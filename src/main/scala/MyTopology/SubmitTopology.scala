package MyTopology
import MySpout.RandomSentenceSpout
import backtype.storm.Config
import backtype.storm.LocalCluster
import backtype.storm.StormSubmitter
import backtype.storm.task.ShellBolt
import backtype.storm.topology.BasicOutputCollector
import backtype.storm.topology.IRichBolt
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.TopologyBuilder
import backtype.storm.topology.base.BaseBasicBolt
import backtype.storm.tuple.Fields
import backtype.storm.tuple.Tuple
import backtype.storm.tuple.Values
import java.util.HashMap
import java.util.Map

import MySpout.RandomSentenceSpout

/**
 * org.apache.thrift7.transport.TTransportException: java.net.ConnectException: Connection refused
 */
object SubmitTopology {

  class SplitSentence extends ShellBolt with IRichBolt {
    def declareOutputFields(declarer: OutputFieldsDeclarer) {
      declarer.declare(new Fields("word"))
    }

    def getComponentConfiguration: java.util.Map[String, AnyRef] = {
      null
    }
  }

  class WordCount extends BaseBasicBolt {
    var counts: java.util.Map[String, Integer] = new java.util.HashMap[String, Integer]

    def execute(tuple: Tuple, collector: BasicOutputCollector) {
      val word: String = tuple.getString(0)
      var count: Integer = counts.get(word)
      if (count == null) count = 0
      count += 1
      counts.put(word, count)
      collector.emit(new Values(word, count))
    }

    def declareOutputFields(declarer: OutputFieldsDeclarer) {
      declarer.declare(new Fields("word", "count"))
    }
  }

  def main(args: Array[String]) {
    val builder: TopologyBuilder = new TopologyBuilder
    builder.setSpout("spout", new RandomSentenceSpout, 5)
    builder.setBolt("split", new SplitSentence, 8).shuffleGrouping("spout")
    builder.setBolt("count", new WordCount, 12).fieldsGrouping("split", new Fields("word"))
    val conf: Config = new Config
    conf.setDebug(true)

    conf.setNumWorkers(3)
    val appName = "wordCount"
    StormSubmitter.submitTopologyWithProgressBar(appName, conf, builder.createTopology)
    Thread.sleep(10000)
  }
}

