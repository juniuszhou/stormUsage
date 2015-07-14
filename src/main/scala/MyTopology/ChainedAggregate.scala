package MyTopology

import MySpout.ScalaRandomSpout
import backtype.storm.tuple.Values
import storm.trident.TridentTopology
import storm.trident.operation.{BaseAggregator, TridentCollector, BaseFunction}
import storm.trident.tuple.TridentTuple

import scala.util.Random





object ChainedAggregate {
  val top = new TridentTopology
  top.newStream("line", new ScalaRandomSpout)
  //.each()
}
