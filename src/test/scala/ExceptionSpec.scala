import exceptions.{CustomException, CustomInheritingException}
import org.scalatest.FeatureSpec

import scala.util.control.Exception.{Catch, catching}

class ExceptionSpec extends FeatureSpec {
  feature("Exception") {
    scenario("Throwing exception") {
      assertThrows[CustomException](throw new CustomException())
    }

    scenario("Handling exception using try-catch block") {
      var message: String = ""

      try {
        throw new CustomException("message")
      } catch {
        case ex: CustomException =>
          message = ex.getMessage
      }

      assert(message == "message")
    }

    scenario("try-catch block is an expression") {
      val result: Int = try {
        throw new CustomException()
        1
      } catch {
        case _: CustomException =>
          0
      }

      assert(result == 0)
    }

    scenario("try-catch block with multiple exception types") {
      val result: Int = try {
        6 / 0
      } catch {
        case _: CustomException => 0
        case _: ArithmeticException => -1
      }

      assert(result == -1)
    }

    scenario("throw inheriting exception in try-catch block with multiple exception types") {
      val result: Int = try {
        throw new CustomInheritingException()
      } catch {
        case _: CustomException => 1
        case _: CustomInheritingException => -1
      }

      assert(result == 1)
    }

    scenario("try-catch block with finally block") {
      var result: Int = 0

      try {
        6 / 0
      } catch {
        case _: ArithmeticException =>
      } finally {
        result = 3
      }

      assert(result == 3)
    }

    scenario("Handling exception using Catch - success") {
      val catchCustom: Catch[Int] = catching(classOf[CustomException]).withApply(_ => -1)

      val result = catchCustom(throw new CustomException())
      assert(result == -1)
    }

    scenario("Handling exception using Catch - failure") {
      val catchCustom: Catch[Int] = catching(classOf[CustomException]).withApply(_ => -1)

      val result = catchCustom(2)
      assert(result == 2)
    }
  }

}
