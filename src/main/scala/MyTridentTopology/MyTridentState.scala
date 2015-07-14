package MyTridentTopology

import MySpout.ScalaRandomSpout
import MyTopology.Split
import MyTridentFunction.MySplit
import storm.trident.TridentState
import storm.trident.TridentTopology
import storm.trident.operation.{TridentCollector, BaseFunction}
import storm.trident.operation.builtin.{Sum, Count, FilterNull, MapGet}
import storm.trident.state.QueryFunction
import storm.trident.testing.{FixedBatchSpout, MemoryMapState}
import backtype.storm.Config
import backtype.storm.LocalCluster
import backtype.storm.LocalDRPC
import backtype.storm.StormSubmitter
import backtype.storm.generated.AlreadyAliveException
import backtype.storm.generated.InvalidTopologyException
import backtype.storm.generated.StormTopology
import backtype.storm.tuple.{Values, Fields}
import backtype.storm.utils.DRPCClient
import storm.trident.tuple.TridentTuple


class InternalSplit extends BaseFunction {
  override def execute(tuple: TridentTuple, collector: TridentCollector) = {
    val sentence = tuple.getString(0)
    sentence.split(" ").foreach(str => collector.emit(new Values(str, "1")))
  }
}

object MyTridentState {
  def main(args: Array[String]) {
    val conf = new Config()
    conf.setMaxSpoutPending(20)
    val drpc = new LocalDRPC()
    val cluster = new LocalCluster()
    val topology = buildTopology(drpc)
    cluster.submitTopology("wordCounter", conf, buildTopology(drpc))
    (0 until 5).foreach(i => {
      println("__________________________________________________________________")
      println(" XXXXXXXXXXXXXXXX Result is " + drpc.execute("words", "cat the dog jumped"))
      Thread.sleep(1000)
    })

  }

  def buildTopology(drpc: LocalDRPC) = {
    val topology = new TridentTopology()
    val spout = new FixedBatchSpout(new Fields("sentence"), 3, new Values(
      "the cow jumped over the moon"), new Values(
      "the man went to the store and bought some candy"), new Values(
      "four score and seven years ago"),
      new Values("how many apples can you eat"), new Values(
        "to be or not to be the person"))
    spout.setCycle(true)

    val wordCounts = topology.newStream("spout1", spout)
      // memory map just a multi hash map in memory to store all words appear times
      // then input fields, aggregator function and output fields.
      .parallelismHint(16)
      .each(new Fields("sentence"), new Split(), new Fields("word"))
      .groupBy(new Fields("word"))
      .persistentAggregate(new MemoryMapState.Factory(), new Count(),
        new Fields("count"))

    Thread.sleep(2000)


    topology.newDRPCStream("words", drpc)
      // the args is fixed field for drpc to get parameters. you can't change it.
      .each(new Fields("args"), new Split(), new Fields("word"))
      .groupBy(new Fields("word"))
      // here we associate the drpc stream and state stream.
      .stateQuery(wordCounts, new Fields("word"),
      // MapGet extends BaseQueryFunction extends BaseOperation implements QueryFunction
      // new MapGet return its class object, need transmit to its interface class.
        new MapGet().asInstanceOf[QueryFunction[storm.trident.state.State, _]], new Fields("count"))
      .each(new Fields("count"), new FilterNull())
      .aggregate(new Fields("count"), new Sum(), new Fields("sum"))

    topology.build()

  }
}

