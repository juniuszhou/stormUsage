package MyTridentTopology

import MyTopology.Split
import backtype.storm.tuple.Fields
import backtype.storm.{LocalDRPC, LocalCluster, Config}
import storm.trident.TridentTopology

/**
 * Created by junius on 6/30/15.
 */
object TridentDrpc {
def main (args: Array[String]) {
  val conf = new Config()

  val drpc = new LocalDRPC()
  val cluster = new LocalCluster()
  val topology = new TridentTopology
  topology.newDRPCStream("words", drpc)
  .each(new Fields("args"), new Split(), new Fields("word"))
  cluster.submitTopology("tester", conf, topology.build())
  (0 until 10).foreach(i => {
    // the name must be the same with drpc stream.
    println(drpc.execute("words", "dat i wojda"))
    Thread.sleep(1000)
  })

  }
}
