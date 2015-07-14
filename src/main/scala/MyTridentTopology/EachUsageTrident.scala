package MyTridentTopology

import MySpout.IntTupleSpout
import backtype.storm.tuple.{Fields, Values}
import backtype.storm.{LocalCluster, Config}
import storm.trident.TridentTopology
import storm.trident.operation.builtin.Count
import storm.trident.operation.{TridentCollector, BaseFunction}
import storm.trident.testing.MemoryMapState.Factory
import storm.trident.tuple.TridentTuple

class AddFunction extends BaseFunction {
  def execute(tuple: TridentTuple, collector: TridentCollector): Unit = {
    println("###### tuple is received. " + tuple.getInteger(0))
    (0 to tuple.getInteger(0)).foreach( i => {
      println("&&&& emit a value")
      collector.emit(new Values(i.toString))
    })
  }
}

class PrintFunction extends BaseFunction {
  def execute(tuple: TridentTuple, collector: TridentCollector): Unit = {

    val fIter = tuple.getFields.iterator()
    while(fIter.hasNext) print("Field: " + fIter.next())
    println()
    println("_________________")
    val vIter = tuple.getValues.iterator()
    while(vIter.hasNext) print(" value: " + vIter.next())
    println()

  }
}

object EachUsageTrident {
  def main (args: Array[String]) {
    val top = new TridentTopology

    top.newStream("spout", new IntTupleSpout)
      // input fields and function and output fields.
    .each(new Fields("b"), new AddFunction, new Fields("d"))
    .each(new Fields("a", "b", "c", "d"), new PrintFunction, new Fields())
    .persistentAggregate(new Factory(), new Count(), new Fields("count") )

    val conf: Config = new Config
    conf.setDebug(true)

    val cluster = new LocalCluster()
    cluster.submitTopology("word-count", conf, top.build)

    Thread.sleep(100000)

  }
}


/*
http://storm.apache.org/documentation/Trident-API-Overview.html
each example.

[1, 2, 3]
[4, 1, 6]
[3, 0, 8]

the first one has two output and second one has one output. last one no ouput
[1, 2, 3, 0]
[1, 2, 3, 1]
[4, 1, 6, 0]

 */