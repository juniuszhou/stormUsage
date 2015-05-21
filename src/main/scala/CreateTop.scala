import MyBolt.SplitSentence
import MySpout.{ScalaRandomSpout, RandomSentenceSpout}
import backtype.storm.{LocalCluster, Config}
import backtype.storm.topology.TopologyBuilder

/**
 * Created by juzhou on 5/21/2015.
 */
object CreateTop {
  def main (args: Array[String]) {
    val tb = new TopologyBuilder
    tb.setSpout("spout", new ScalaRandomSpout, 1)
    tb.setBolt("split", new SplitSentence, 1).shuffleGrouping("spout")

    val conf: Config = new Config
    conf.setDebug(true)
    conf.setMaxTaskParallelism(1)

    val cluster = new LocalCluster()
    cluster.submitTopology("word-count", conf, tb.createTopology)

    Thread.sleep(10000)

    cluster.shutdown
  }
}
