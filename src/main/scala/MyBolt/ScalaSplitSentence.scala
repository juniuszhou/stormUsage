package MyBolt

import backtype.storm.task.{OutputCollector, TopologyContext, IBolt}
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.tuple.Tuple

import backtype.storm.tuple.Fields

class ScalaSplitSentence extends IBolt{
  override def cleanup() {}

  //this bolt just get all values from tuple also check is global info such as ids.
  override def execute(input: Tuple): Unit = {
    val msgId = input.getMessageId
    val streamId = input.getSourceStreamId
    val component = input.getSourceComponent

    val size = input.size
    val vs = input.getValues
    val value = vs.get(0) // get the first value as set as word field

  }

  override def prepare(stormConf: java.util.Map[_, _], context: TopologyContext, collector: OutputCollector) {}

  def  declareOutputFields (declarer: OutputFieldsDeclarer): Unit = {
    val streamId = "streamOne"
    val fields = new Fields("word")

    declarer.declare(fields) // just declare new field
    declarer.declareStream(streamId, fields) // declare both stream and field.
  }


  def getComponentConfiguration: java.util.Map[String, Object] = {
    null
  }
}
