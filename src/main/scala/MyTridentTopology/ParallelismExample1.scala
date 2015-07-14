package MyTridentTopology

import MySpout.IntTupleSpout
import storm.trident.TridentTopology
import storm.trident.operation.BaseFilter
import storm.trident.operation.TridentOperationContext
import storm.trident.tuple.TridentTuple
import backtype.storm.Config
import backtype.storm.LocalCluster
import backtype.storm.LocalDRPC
import backtype.storm.generated.StormTopology
import backtype.storm.tuple.Fields

object ParallelismExample1 {
  def main (args: Array[String]) {
    val conf = new Config()

    //val drpc = new LocalDRPC()
    val cluster = new LocalCluster()
    val pe =  new ParallelismExample1
    cluster.submitTopology("hackaton", conf, pe.buildTopology()) //drpc))
  }
}


class ParallelismExample1 extends BaseFilter {
    var partitionIndex: Int = _
    var actor: String = _

    override def prepare(conf: java.util.Map[_, _], context: TridentOperationContext) = {
      partitionIndex = context.getPartitionIndex
    }

    def isKeep(tuple: TridentTuple): Boolean = {
      val filter = tuple.getString(0).equals(actor)
      if(filter) {
        println("I am partition [" + partitionIndex + "] by: " + actor)
      }
      filter
    }

  def buildTopology(/*drpc: LocalDRPC*/): StormTopology = {
    val topology = new TridentTopology()
    topology.newStream("spout", new IntTupleSpout)
      .parallelismHint(2)
      .partitionBy(new Fields("a"))

    topology.build()
  }

}