package MyTopology

import MyBolt.{TickBolt, ScalaNewFieldBolt, ExclamationBolt}
import MySpout.ScalaRandomSpout
import backtype.storm.topology.TopologyBuilder
import backtype.storm.{Config, LocalCluster}
import org.slf4j.LoggerFactory

/**
 * Created by juzhou on 5/21/2015.
 */
object CreateTop {
  def main (args: Array[String]) {
    val log = LoggerFactory.getLogger("CreateTop")
    log.error("start to call storm topology.")
    val tb = new TopologyBuilder
    // tb.setSpout(new ScalaRandomSpout, 1)

    // set component id as spout. and parallelism as 1
    tb.setSpout("spout", new ScalaRandomSpout, 1)

    // set component id as split. and data from spout component.
    //tb.setBolt("split", new ScalaNewFieldBolt(), 1).shuffleGrouping("spout")

    // if you create your own stream not default. then must set the stream name here.
    tb.setBolt("split", new TickBolt(), 1).shuffleGrouping("spout", "defaultOne")



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
