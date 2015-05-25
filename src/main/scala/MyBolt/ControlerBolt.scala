package MyBolt

import backtype.storm.task.{OutputCollector, TopologyContext, IOutputCollector}
import backtype.storm.topology.{OutputFieldsDeclarer, IRichBolt}
import backtype.storm.tuple.{Fields, Tuple}

class ControlerBolt extends IRichBolt with IOutputCollector {
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

  def emit(streamId: String, anchors: java.util.Collection[Tuple],
           tuple: java.util.List[AnyRef]): java.util.List[Integer] = {
    null
  }

  def emitDirect(taskId: Int, streamId: String,
                 anchors: java.util.Collection[Tuple], tuple: java.util.List[Object]) {}
  def ack(input: Tuple) {}
  def fail(input: Tuple) {}

  def reportError(error: Throwable) {}
}
