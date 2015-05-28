package MyTopology

import MyBolt.{ScalaNewFieldBolt, ExclamationBolt, SplitSentence}
import MySpout.ScalaRandomSpout
import backtype.storm.topology.TopologyBuilder
import backtype.storm.{Config, LocalCluster}

/**
 * Created by juzhou on 5/21/2015.
 */
object CreateTop {
  def main (args: Array[String]) {
    val tb = new TopologyBuilder
    // tb.setSpout(new ScalaRandomSpout, 1)
    tb.setSpout("spout", new ScalaRandomSpout, 1)
    tb.setBolt("split", new ScalaNewFieldBolt(), 1).shuffleGrouping("spout")

    val conf: Config = new Config
    conf.setDebug(true)
    // don't set it too small.
    // conf.setMaxTaskParallelism(1)

    val cluster = new LocalCluster()
    cluster.submitTopology("word-count", conf, tb.createTopology)

    Thread.sleep(100000)

    // cluster.shutdown()
  }
}
