package MyTridentFunction

import backtype.storm.tuple.Values
import storm.trident.operation.{TridentCollector, BaseAggregator}
import storm.trident.tuple.TridentTuple


class MySplit extends BaseAggregator[String] {
  var minPrice: String = _
  var wordList = List[String]()
  def init(obj: Object, collector: TridentCollector): String = {
    ""
  }

  def aggregate(f: String, tuple: TridentTuple, collector: TridentCollector): Unit = {
    val sentence = tuple getString 0
    wordList = sentence :: wordList

  }

  def complete(f: String, collector: TridentCollector) = {
    val result = wordList.map(str => (str, 1)).groupBy(_._1).toList.map(kandl => (kandl._1, kandl._2.size))
    result.foreach(nandint =>  collector.emit(new Values(nandint._1.toString, nandint._2.toString)))
  }
}