import either.DoubleUtils
import org.scalatest.FeatureSpec

import scala.util.{Failure, Success, Try}

class TrySpec extends FeatureSpec {


  feature("Try") {
    scenario("Success") {
      val expected: Try[Double] = Success(1.0)
      val actual: Try[Double] = DoubleUtils.tryToDouble("0.5").map(_ * 2)

      assert(expected == actual)
    }

    scenario("Failure") {
      val result: Try[Double] = DoubleUtils.tryToDouble("toDouble")

      assert(result.isFailure)
      assert(result.isInstanceOf[Failure[Double]])
    }
  }
}
