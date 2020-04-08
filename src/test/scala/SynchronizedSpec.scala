import org.scalatest.FeatureSpec
import synchronized.Counter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class SynchronizedSpec extends FeatureSpec {
  feature("Synchronized") {
    scenario("Incrementing value using Futures without synchronized") {
      val counter = new Counter()

      for (_ <- 1 to 1000000) {
        Future {
          counter.increment()
        }
      }
      Thread.sleep(1000)
      println(counter.result)
    }

    scenario("Incrementing value using Futures with synchronized") {
      val counter = new Counter()

      for (_ <- 1 to 1000000) {
        Future {
          counter.incrementSynchronized()
        }
      }

      Thread.sleep(1000)

      val expected: Int = 1000000
      val actual: Int = counter.result

      assert(actual == expected)
    }
  }
}
