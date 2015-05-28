
import org.slf4j.LoggerFactory

/**
 * Created by juzhou on 5/22/2015.
 */
object MyLogger4j {
  def main (args: Array[String]) {
    val log = LoggerFactory.getLogger(MyLogger4j.getClass)
    log.error("stackoverflow", " happened.")
  }
}
