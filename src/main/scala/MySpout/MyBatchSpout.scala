package MySpout

import java.util
import scala.util.Random

import backtype.storm.Config
import backtype.storm.task.TopologyContext
import backtype.storm.tuple.Fields
import storm.trident.operation.TridentCollector
import storm.trident.spout.IBatchSpout

class MyBatchSpout extends IBatchSpout {
  val fields = new Fields("id", "name")
  //var outputs: Array[java.util.List[AnyRef]] = null
  val maxBatchSize: Int = 10

  val data = new Array[java.util.List[AnyRef]](10)
  val rand = new Random()
  (0 until 10).foreach(i => data(i) = {
    val arr = new java.util.ArrayList[AnyRef]()
    arr.add(rand.nextString(5)) // add id string
    arr.add(rand.nextString(10)) // add name string
    // arr.add(new Object)  // we can add any object but raw type
    arr
  })

  def open(conf: java.util.Map[_, _], context: TopologyContext) {
      }

  def emitBatch(batchId: Long, collector: TridentCollector) {
      collector.emit(data(batchId.toInt))
  }

  def ack(batchId: Long) {
    // we need mark or remove acked batch.
    // this.batches.remove(batchId)
  }

  def close() {
  }

  def getComponentConfiguration: java.util.Map[_, _] = {
    val conf: Config = new Config
    conf.setMaxTaskParallelism(1)
    // conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, new Integer(60))
    conf
  }

  def getOutputFields: Fields = fields
}
