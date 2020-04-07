import org.scalatest.FeatureSpec

import options.{Capitals, IntUtils}

class OptionsSpec extends FeatureSpec {


  feature("Introduction to Option/Some/None") {
    scenario("When existing key provided Map returns Some") {
      val expected: Option[String] = Some("Warsaw")
      val actual: Option[String] = Capitals.capitals.get("Poland")

      assert(expected == actual)
      assert(actual.get == "Warsaw")
    }

    scenario("When not existing key provided Map returns None") {
      val expected: Option[String] = None
      val actual: Option[String] = Capitals.capitals.get("France")

      assert(expected == actual)
      assert(actual.isEmpty)
    }
  }

  feature("Working with Option using a match expression") {
    scenario("Some case") {
      val expected: String = "Warsaw"
      val actual: String = Capitals.getCapital(Capitals.capitals.get("Poland"))

      assert(expected == actual)
    }

    scenario("None case") {
      val expected: String = "?"
      val actual: String = Capitals.getCapital(Capitals.capitals.get("Wakanda"))

      assert(expected == actual)
    }
  }

  feature("Working with Option using for/yield") {
    scenario("Some case") {
      val expected: Option[Int] = Option(3)
      val actual: Option[Int] = IntUtils.add("1", "2")

      assert(expected == actual)
      assert(expected.get == actual.get)
    }

    scenario("None case") {
      val expected: Option[Int] = None
      val actual: Option[Int] = IntUtils.add("1", "notInt")

      assert(expected == actual)
    }
  }

  feature("Option as a container") {
    scenario("Some case") {
      val expected: Option[Int] = Some(4)
      val actual: Option[Int] = IntUtils.toInt("2").map((number: Int) => number * number)

      assert(expected == actual)
    }

    scenario("None case") {
      val expected: Option[Int] = None
      val actual: Option[Int] = IntUtils.toInt("notInt").map((number: Int) => number * number)

      assert(expected == actual)
    }
  }

  feature("Option vs Some") {
    scenario("Option(null) vs Some(null") {
      assert(null == Some(null).get)
      assert(Some(null).isDefined)
      assert(Option(null).isEmpty)
    }
  }
}
