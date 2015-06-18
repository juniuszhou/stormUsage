package MyTopology

import MyBolt.ExclamationBolt
import MySpout.ScalaRandomSpout
import backtype.storm.topology.TopologyBuilder

/**
 * Created by junius on 6/15/15.
 */
object MyNimbusTop {
  def main (args: Array[String]) {
    val tb = new TopologyBuilder
    tb.setSpout("word", new ScalaRandomSpout(), 10)
    tb.setBolt("exclaim", new ExclamationBolt(), 3).shuffleGrouping("word")
    tb.createTopology()
  }
}
