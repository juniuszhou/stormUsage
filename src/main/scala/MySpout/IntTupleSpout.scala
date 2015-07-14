package MySpout

import java.util.Random

import backtype.storm.spout.SpoutOutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.base.BaseRichSpout
import backtype.storm.tuple.{Fields, Values}

class IntTupleSpout extends BaseRichSpout {
  private[MySpout] var _collector: SpoutOutputCollector = null
  private[MySpout] var _rand: Random = null

  //this variable set as true is critical important otherwise some workers may die.
  // private[MySpout] var _isDistributed: Boolean = true

  def nextTuple() {
    val one = _rand.nextInt(4)
    val two = _rand.nextInt(3)
    val three = _rand.nextInt(4)
    Thread.sleep(1000)
    _collector.emit(new Values(new Integer(one), new Integer(two), new Integer(three)))
  }

  override def ack(id: AnyRef) {
  }

  override def fail(id: AnyRef) {
  }

  def declareOutputFields(declarer: OutputFieldsDeclarer) {
    /*
    val streamId1 = "defaultOne"

    val streamId2 = "defaultTwo"
    declarer.declareStream(streamId1, new Fields("word"))
    declarer.declareStream(streamId2, new Fields("word"))
    */

    declarer.declare(new Fields("a", "b", "c"))
  }

  //java.util.Map must be declared otherwaise its default map from scala.
  def open(conf: java.util.Map[_, _], context: TopologyContext, collector: SpoutOutputCollector) {

    _collector = collector
    _rand = new Random
  }

}