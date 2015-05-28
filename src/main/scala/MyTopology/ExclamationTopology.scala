package MyTopology

import MySpout.ScalaRandomSpout
import backtype.storm.testing.TestWordSpout
import backtype.storm.topology.TopologyBuilder
import MyBolt.ExclamationBolt
import backtype.storm.{LocalCluster, Config}
import backtype.storm.utils.Utils

// copy from https://github.com/boonhero/my-storm-starter

object ExclamationTopology {
  def main(args: Array[String]) {
    val builder = new TopologyBuilder

    builder.setSpout("word", new ScalaRandomSpout(), 10)
    builder.setBolt("exclaim", new ExclamationBolt(), 3).shuffleGrouping("word")

    val config = new Config()
    config.setDebug(true)

    val cluster = new LocalCluster()

    cluster.submitTopology("ExclamationTopology", config, builder.createTopology())
    Utils.sleep(5000)
    cluster.killTopology("ExclamationTopology")
    cluster.shutdown()

  }
}