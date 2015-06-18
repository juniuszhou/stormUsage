package MySpout

import backtype.storm.spout.SpoutOutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.base.BaseRichSpout
import backtype.storm.tuple.Fields
import backtype.storm.tuple.Values
import backtype.storm.utils.Utils
import java.util.Random

/*
this class is copied from storm-start, which is included in storm source code.
as a part of storm example to show how to use storm.
 */

class MyRandomSentenceSpout extends BaseRichSpout {
  var _collector: SpoutOutputCollector = null
  var _rand: Random = null

  def open(conf: java.util.Map[_, _], context: TopologyContext, collector: SpoutOutputCollector) {
    _collector = collector
    _rand = new Random
  }

  def nextTuple() {
    Utils.sleep(100)
    val sentences: Array[String] = Array[String]("the cow jumped over the moon", "an apple a day keeps the doctor away", "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature")
    val sentence: String = sentences(_rand.nextInt(sentences.length))
    _collector.emit(new Values(sentence))
  }

  override def ack(id: AnyRef) {
  }

  override def fail(id: AnyRef) {
  }

  def declareOutputFields(declarer: OutputFieldsDeclarer) {
    declarer.declare(new Fields("word"))
  }
}
