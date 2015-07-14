package MyDrpcTopology

import backtype.storm.drpc.LinearDRPCTopologyBuilder
import backtype.storm.{LocalCluster, LocalDRPC}

/**
 * http://stackoverflow.com/questions/18730164/storm-drpc-versus-transactional-versus-trident-when-to-use-what
 * Created by junius on 6/26/15.
 * LocalDRPC drpc = new LocalDRPC();
      LocalCluster cluster = new LocalCluster();
 */
object MyDrpcTop {
def main (args: Array[String]) {
    val drpc = new LocalDRPC
    val cluster = new LocalCluster


  }
}
