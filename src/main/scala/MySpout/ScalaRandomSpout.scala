package MySpout

import java.util.Random

import backtype.storm.spout.SpoutOutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.base.BaseRichSpout
import backtype.storm.tuple.{Fields, Values}
import backtype.storm.utils.Utils

/**
 * Created by juzhou on 5/21/2015.
 */
class ScalaRandomSpout extends BaseRichSpout {
  private[MySpout] var _collector: SpoutOutputCollector = null
  private[MySpout] var _rand: Random = null

  //this variable set as true is critical important otherwise some workers may die.
  // private[MySpout] var _isDistributed: Boolean = true

  def nextTuple() {
    val sentences: Array[String] = Array[String]("the cow jumped over the moon",
      "an apple a day keeps the doctor away", "four score and seven years ago",
      "snow white and the seven dwarfs", "i am at two with nature")
    val sentence: String = sentences(_rand.nextInt(sentences.length))
    Thread.sleep(1000)
    _collector.emit("defaultOne", new Values(sentence))
    _collector.emit("defaultTwo", new Values(sentence))
    //_collector.emit(new Values(sentence))
  }

  override def ack(id: AnyRef) {
  }

  override def fail(id: AnyRef) {
  }

  def declareOutputFields(declarer: OutputFieldsDeclarer) {
    val streamId1 = "defaultOne"
    val streamId2 = "defaultTwo"
    declarer.declareStream(streamId1, new Fields("word"))
    declarer.declareStream(streamId2, new Fields("word"))
    //declarer.declare(new Fields("word"))
  }

  //java.util.Map must be declared otherwaise its default map from scala.
  def open(conf: java.util.Map[_, _], context: TopologyContext, collector: SpoutOutputCollector) {

    _collector = collector
    _rand = new Random
  }

}