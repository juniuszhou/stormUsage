package MyTopology

import backtype.storm.Config
import backtype.storm.LocalCluster
import backtype.storm.LocalDRPC
import backtype.storm.generated.StormTopology
import backtype.storm.tuple.Fields
import backtype.storm.tuple.Values
import storm.trident.TridentTopology
import storm.trident.operation.BaseFunction
import storm.trident.operation.TridentCollector
import storm.trident.operation.builtin.Count
import storm.trident.operation.builtin.FilterNull
import storm.trident.operation.builtin.MapGet
import storm.trident.operation.builtin.Sum
import storm.trident.state.{State, QueryFunction}
import storm.trident.testing.FixedBatchSpout
import storm.trident.testing.MemoryMapState
import storm.trident.tuple.TridentTuple

// https://github.com/velvia/ScalaStorm

class Split extends BaseFunction {
  override def execute(tuple: TridentTuple, collector: TridentCollector) = {
    val sentence = tuple.getString(0)
    sentence.split(" ").foreach(str => collector.emit(new Values(str)))
  }
}

object TridentWordCount {
  def main(args: Array[String]) {
    val conf = new Config()
    conf.setMaxSpoutPending(20)


    val cluster = new LocalCluster()
    val tri = new TridentWordCount()
    val drpc = new LocalDRPC()

    cluster.submitTopology("wordCounter", conf, tri.buildTopology(drpc))

    (0 until 100).foreach(i => {
      println("DRPC RESULT: " + drpc.execute("words", "can you eat jumped"))
      Thread.sleep(1000)
    })


    //conf.setNumWorkers(3)
    //StormSubmitter.submitTopoogyWithProgressBar(args[0], conf, buildTopology(null))

  }
}

class TridentWordCount {

  def buildTopology(drpc: LocalDRPC): StormTopology = {
    val spout = new FixedBatchSpout(new Fields("sentence"), 3, new Values("the cow jumped over the moon"),
      new Values("the man went to the store and bought some candy"), new Values("four score and seven years ago"),
      new Values("how many apples can you eat"), new Values("to be or not to be the person"))
    spout.setCycle(true)

    val topology = new TridentTopology()
    // it is a stream to deal with data then put it into state.
    val wordCounts = topology.newStream("spout1", spout).parallelismHint(16)
      .each(new Fields("sentence"),new Split(), new Fields("word"))
      .groupBy(new Fields("word"))
      .persistentAggregate(new MemoryMapState.Factory(),new Count(), new Fields("count"))
      .parallelismHint(16)

    // use drpc to get data from word counts, it is a state of persistence.
    //
    topology.newDRPCStream("words", drpc)
      .each(new Fields("args"), new Split(), new Fields("word"))
      .groupBy(new Fields("word"))
      .stateQuery(wordCounts, new Fields("word"),
        (new MapGet).asInstanceOf[QueryFunction[_ <: State, _]], new Fields("count"))
      .each(new Fields("count"), new FilterNull())
      .aggregate(new Fields("count"), new Sum(), new Fields("sum"))
    topology.build()
  }


}