import either.DoubleUtils
import org.scalatest.FeatureSpec

class EitherSpec extends FeatureSpec {

  feature("Either") {
    scenario("Left") {
      val expected: String = "eitherStringOrDouble"
      val actual: Either[String, Double] = DoubleUtils.eitherStringOrDouble("eitherStringOrDouble")

      assert(actual.isLeft)
      assert(expected == actual.left.getOrElse(""))
    }

    scenario("Right") {
      val expected: Double = 1.0
      val actual: Either[String, Double] = DoubleUtils.eitherStringOrDouble("0.5").map(_ * 2)

      assert(actual.isRight)
      assert(expected == actual.getOrElse(0.0))
    }
  }
}
