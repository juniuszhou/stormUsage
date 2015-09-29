package MyTransaction

import backtype.storm.testing.MemoryTransactionalSpout
import backtype.storm.transactional.TransactionalTopologyBuilder
import backtype.storm.tuple.Fields

/**
 * TransactionalTopologyBuilder deprecated and replaced by trident api.
 */
object SimpleTransaction {
  def main (args: Array[String]) {
    val spout = new MemoryTransactionalSpout(null, new Fields("word"), 100)
    val builder = new TransactionalTopologyBuilder("global-count", "spout", spout, 3)

  }
}
