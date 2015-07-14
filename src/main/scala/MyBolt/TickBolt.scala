package MyBolt

import backtype.storm.{Constants, Config}
import backtype.storm.topology.base.BaseRichBolt
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.tuple.{Values, Fields, Tuple}
import backtype.storm.task.{OutputCollector, TopologyContext}
import java.util.{Map => JMap}
import org.slf4j.LoggerFactory

// good way to use java map in scala.

class TickBolt extends BaseRichBolt {
  val logger = LoggerFactory.getLogger("TickBolt")
  var collector: OutputCollector = _

  override def prepare(config: JMap[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    this.collector = collector
  }

  override def execute(tuple: Tuple): Unit = {
    if((tuple.getSourceComponent == Constants.SYSTEM_COMPONENT_ID) &&
      (tuple.getSourceStreamId == Constants.SYSTEM_TICK_STREAM_ID)) {
        logger.error("get tick message from system.")
    }
    else {
      logger.error("component id is " + tuple.getSourceComponent)
      logger.error("stream id is " + tuple.getSourceStreamId)
      logger.error("message id is " + tuple.getMessageId)
      this.collector.emit(tuple, new Values("JJJ !!!"))
    }
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields("word"))
  }

  override def getComponentConfiguration: java.util.Map[String, Object] = {
    val conf: Config = new Config
    // configure tick as 60 then got a system tuple each one minute.
    conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, new Integer(60))
    conf
  }
}