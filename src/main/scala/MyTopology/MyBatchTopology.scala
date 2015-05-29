package MyTopology

import backtype.storm.tuple._
import backtype.storm.{LocalCluster, Config}
import storm.trident.TridentTopology
import storm.trident.testing.FixedBatchSpout

object MyBatchTopology {
  def main(args: Array[String]) {
    val conf: Config = new Config
    conf.setMaxSpoutPending(20)

    val spout = new FixedBatchSpout(new Fields("sentence"), 3,
      new Values("the cow jumped over the moon"),
      new Values("the man went to the store and bought some candy"),
      new Values("four score and seven years ago"),
      new Values("how many apples can you eat"),
      new Values("to be or not to be the person"))
    val topology = new TridentTopology
    topology.newStream("spout1", spout)
    val stormTop = topology.build()

    val cluster = new LocalCluster()
    cluster.submitTopology("wordCounter", conf, stormTop)
  }
}




