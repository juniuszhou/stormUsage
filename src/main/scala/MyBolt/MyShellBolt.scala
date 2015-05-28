package MyBolt

import backtype.storm.task.ShellBolt
import backtype.storm.topology.{OutputFieldsDeclarer, IRichBolt}
import backtype.storm.tuple.Fields

// not tested yet.
class MyShellBolt (command: String, args: String) extends ShellBolt with IRichBolt {
  //def this(command: String, args: String) = {super(command, args)}

  def declareOutputFields (declarer: OutputFieldsDeclarer) {
    declarer.declare (new Fields ("word") )
  }

  def getComponentConfiguration: java.util.Map[String, AnyRef] = {
    null
  }
}