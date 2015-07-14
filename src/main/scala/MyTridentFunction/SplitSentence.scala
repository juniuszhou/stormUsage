package MyTridentFunction

import backtype.storm.tuple.Values
import storm.trident.operation.{TridentCollector, BaseFunction}
import storm.trident.tuple.TridentTuple

import scala.util.Random

class SplitSentence extends BaseFunction {
  def execute(tuple: TridentTuple, collector: TridentCollector) = {
      /*val sen = tuple getString 0
      val (word, count) =
        sen.split(" ").map(str => (str, 1)).groupBy(_).map(kArr => (kArr._1, kArr._2.size))
      collector.emit(new Values(word, count))*/

  }
}
