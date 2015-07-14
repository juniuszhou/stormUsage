package MyTridentFunction

import backtype.storm.tuple.Values
import storm.trident.operation.{TridentCollector, BaseAggregator}
import storm.trident.tuple.TridentTuple

class MyAggMin extends BaseAggregator[Float] {
  var minPrice: Float = _
  def init(obj: Object, collector: TridentCollector): Float = {
    0.0F
  }

  def aggregate(f: Float, tuple: TridentTuple, collector: TridentCollector): Unit = {
  }

  def complete(f: Float, collector: TridentCollector) = {
    collector.emit(new Values(new java.lang.Float(minPrice)))
  }
}