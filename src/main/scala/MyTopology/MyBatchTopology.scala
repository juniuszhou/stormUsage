package MyTopology

import MySpout.MyBatchSpout
import backtype.storm.{LocalCluster, Config}
import storm.trident.TridentTopology

object MyBatchTopology {
  def main(args: Array[String]) {
    val conf: Config = new Config
    conf.setMaxSpoutPending(20)

    val spout = new MyBatchSpout
    val topology = new TridentTopology
    topology.newStream("spout1", spout)
    val stormTop = topology.build()

    val cluster = new LocalCluster()
    cluster.submitTopology("wordCounter", conf, stormTop)
  }
}




