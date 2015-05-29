package MyBolt

/**
 * Created by junius on 5/26/15.
 */
import backtype.storm.topology.base.BaseRichBolt
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.tuple.{Values, Fields, Tuple}
import backtype.storm.task.{OutputCollector, TopologyContext}
import java.util.{Map => JMap}  // good way to use java map in scala.

class ExclamationBolt extends BaseRichBolt {

  var collector: OutputCollector = _

  override def prepare(config: JMap[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    this.collector = collector
  }

  override def execute(tuple: Tuple): Unit = {
    this.collector.emit(tuple, new Values("JJJ !!!"))
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields("word"))
  }
}