package MyTopology

import backtype.storm.drpc.LinearDRPCTopologyBuilder
import backtype.storm.topology.TopologyBuilder
import backtype.storm.{Config, LocalDRPC, LocalCluster}
import storm.trident.TridentTopology

/**
 * Created by juzhou on 5/26/2015.
 */
object MyLocalDRPC {
  def main(args: Array[String]) {
    // the whole linear drpc was deprecated.
    val builder = new LinearDRPCTopologyBuilder("")
    // builder.addBolt(new ExclaimBolt(), 3)
    /*val topology: TridentTopology = new TridentTopology
    val cluster = new LocalCluster()
    val drpcOne = new LocalDRPC()

    topology.newDRPCStream("words", drpcOne)

    val conf: Config = new Config
    conf.setDebug(true)
    conf.setMaxTaskParallelism(1)

    cluster.submitTopology("drpc-demo", conf, builder.createLocalTopology(drpcOne))

    System.out.println("Results for 'hello':" + drpcOne.execute("exclamation", "hello"))

    cluster.shutdown()
    drpcOne.shutdown()*/
  }
}
