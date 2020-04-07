import org.scalatest.FeatureSpec

import stringinterpolation.StringInterpolators.UppercaseInterpolator

class StringInterpolationSpec extends FeatureSpec {
  feature("The s String Interpolator") {
    scenario("Using variables directly in the string") {
      val name: String = "Adam"

      val expected: String = "Hello Adam"
      val actual: String = s"Hello $name"
      assert(expected == actual)
    }

    scenario("Using arbitrary expressions in processed string") {
      val expected: String = "2 + 2 = 4"
      val actual: String = s"2 + 2 = ${2 + 2}"
      assert(expected == actual)
    }

    scenario("Using special characters in processed string") {
      val expected: String = "Price: $399"
      val actual: String = s"Price: $$${399}"
      assert(expected == actual)
    }

    scenario("Using double quotes in processed string") {
      val expected: String = "{\"name\":\"James\"}"
      val actual: String = """{"name":"James"}"""
      assert(expected == actual)
    }
  }

  feature("The f Interpolator") {
    scenario("Formatting variables in processed string") {
      val name: String = "Paul"
      val height: Double = 1.9d

      val expected: String = "Paul is 1,90 meters tall"
      val actual: String = f"$name%s is $height%.2f meters tall"
      assert(expected == actual)
    }

    scenario("Passing wrong variable type to f interpolator issues an error") {
      val height: Double = 1.9d
      //      val invalidFormattedString = f"$height%4d"
    }
  }

  feature("The raw Interpolator") {
    scenario("Raw interpolator does not escape literals within string") {
      val expected: String = "\\n"
      val actual: String = raw"\n"
      assert(expected == actual)
    }
  }

  feature("Custom Interpolator") {
    scenario("Using custom uppercase interpolator makes string uppercase") {
      val expected: String = "THIS STRING IS UPPERCASE"
      val actual: String = uppercase"This ${"string"} is ${"uppercase"}"
      assert(expected == actual)
    }
  }
}
