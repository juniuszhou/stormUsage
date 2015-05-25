package MyBolt

import backtype.storm.task.{OutputCollector, TopologyContext}
import backtype.storm.topology.IRichBolt
import backtype.storm.tuple.Tuple

/**
 * Created by juzhou on 5/22/2015.
 */
class ScalaNewFieldBolt extends IRichBolt {
  override def cleanup() {}

  //this bolt just get all values from tuple also check is global info such as ids.
  override def execute(input: Tuple): Unit = {
    val msgId = input.getMessageId
    val streamId = input.getSourceStreamId
    val component = input.getSourceComponent

    val size = input.size
    val vs = input.getValues
    (0 until size).foreach(i => println(i + " value is " + vs.get(i).toString))

  }

  override def prepare(stormConf: java.util.Map[_, _], context: TopologyContext, collector: OutputCollector) {}


}
