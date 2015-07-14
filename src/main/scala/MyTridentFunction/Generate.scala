package MyTridentFunction

import backtype.storm.tuple.Values
import storm.trident.operation.{TridentCollector, BaseFunction}
import storm.trident.tuple.TridentTuple

import scala.util.Random

class Generate extends BaseFunction {
  def execute(tuple: TridentTuple, collector: TridentCollector) = {
    (0 until 3).foreach(i => {
      val rand = new Random()
      val hotelId = rand.nextInt(1000).toString
      val checkInDate = if (i > 1) "" else new java.util.Date().toString
      val price = rand.nextInt(2000).toString

      val occupancy = "2" // 1 adults
      val los = "2"   // length of stay three days
      val roomCount = "1" // one room

      val roomType = "roomtype"
      val ratePlan = "ratePlan"
      val startDate = "20150603"
      collector.emit(new Values(hotelId,
        checkInDate, price, occupancy, los, roomCount, roomType, ratePlan, startDate))
    })
  }
}