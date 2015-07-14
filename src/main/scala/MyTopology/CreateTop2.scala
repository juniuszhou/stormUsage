package MyTopology
package MyTopology

import MyBolt.{TickBolt, ScalaNewFieldBolt, ExclamationBolt}
import MySpout.ScalaRandomSpout
import backtype.storm.topology.TopologyBuilder
import backtype.storm.{Config, LocalCluster}

/**
 * Created by juzhou on 5/21/2015.
 */
object CreateTop2 {
  def main (args: Array[String]) {
    val tb = new TopologyBuilder
    // tb.setSpout(new ScalaRandomSpout, 1)

    // set component id as spout. and parallelism as 1
    val spout = new ScalaRandomSpout
    tb.setSpout("spout", spout, 1)

    //set just one executor but 2 tasks.
    tb.setBolt("split", new TickBolt(), 1).setNumTasks(2)
      .shuffleGrouping("spout", "defaultOne")



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
