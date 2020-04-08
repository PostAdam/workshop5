import java.util.NoSuchElementException

import org.scalatest.FeatureSpec

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.{Await, Future, TimeoutException}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

class FutureSpec extends FeatureSpec {
  feature("Awaiting for Future result") {
    scenario("Future result is awaited") {
      val future: Future[Int] = Future {
        Thread.sleep(1000)
        1
      }

      val actual: Int = Await.result(future, 2 second)
      val expected: Int = 1

      assert(expected == actual)
    }

    scenario("Future is timed out") {
      val future: Future[Unit] = Future {
        Thread.sleep(5000)
      }

      assertThrows[TimeoutException] {
        Await.result(future, 1 second)
      }
    }

    scenario("Accessing Future value when it is completed") {
      val future: Future[Int] = Future {
        Thread.sleep(1000)
        1
      }

      Thread.sleep(2000)

      assert(future.isCompleted)
      val futureValue: Option[Try[Int]] = future.value
      val expected: Int = 1
      val actual: Int = futureValue.get.get
      assert(expected == actual)
    }

    scenario("Accessing Future value when it is not completed") {
      val future: Future[Int] = Future {
        Thread.sleep(3000)
        1
      }

      assert(future.isCompleted == false)
      val futureValue: Option[Try[Int]] = future.value
      assertThrows[NoSuchElementException](futureValue.get.get)
    }
  }

  feature("Handling Future result using callbacks") {
    scenario("Handle Future using onComplete with pattern matching - Success case") {
      val future = Future[Int] {
        Thread.sleep(1000)
        1
      }

      assert(future.isCompleted == false)

      val _: Unit = future.onComplete {
        case Success(result) =>
          val expected = 1
          val actual = result
          assert(expected == actual)
        case Failure(_) =>
          assert(1 == 0)
      }

      Thread.sleep(2000)
    }

    scenario("Handle Future using onComplete with pattern matching - Failure case") {
      val future = Future[Int] {
        Thread.sleep(1000)
        throw new RuntimeException
        1
      }

      assert(future.isCompleted == false)

      val _: Unit = future.onComplete {
        case Success(_) =>
          assert(1 == 0)
        case Failure(_) =>
          assert(future.isCompleted)
      }

      Thread.sleep(2000)
    }

    scenario("Handle Future using andThen method with pattern matching") {
      val future = Future {
        Thread.sleep(1000)
        1
      }

      assert(future.isCompleted == false)

      val _: Future[Int] = future.andThen {
        case Success(r) =>
          assert(future.isCompleted)

          val expected: Int = 1
          val actual: Int = r
          assert(actual == expected)
        case Failure(e) => throw e
      }

      Thread.sleep(2000)
    }
  }

  feature("Future object methods") {
    scenario("Mapping result of the Future") {
      val future: Future[Int] = Future {
        Thread.sleep(1000)
        2
      }

      future
        .map(x => x * x)
        .onComplete {
          case Success(result) =>
            assert(result == 4)
          case Failure(_) =>
            assert(1 == 0)
        }


      Thread.sleep(2000)
    }

    scenario("Recovering Future when failed") {
      val future: Future[Int] = Future {
        Thread.sleep(1000)
        throw new RuntimeException()
        1
      }

      val recoveredFuture: Future[Int] = future.recover {
        case _: RuntimeException => 0
      }

      val _: Unit = recoveredFuture.onComplete {
        case Success(result) =>
          val expected: Int = 0
          assert(result == expected)
        case Failure(_) =>
          assert(1 == 0)
      }

      Thread.sleep(2000)
    }
  }

  feature("Running Futures in parallel") {
    scenario("Getting result of the first completed Future") {
      val future1: Future[Int] = Future { Thread.sleep(2000); 1 }
      val future2: Future[Int] = Future { Thread.sleep(1500); 2 }
      val future3: Future[Int] = Future { Thread.sleep(1000); 3 }

      val futures: Array[Future[Int]] = Array(future1, future2, future3)
      val firstCompleted: Future[Int] = Future.firstCompletedOf(futures)

      firstCompleted.onComplete {
        case Success(result) =>
          val expected: Int = 3
          assert(result == expected)
        case Failure(_) =>
          assert(1 == 0)
      }
      Thread.sleep(5000)
    }

    scenario("Getting result of all completed Future") {
      val future1: Future[Int] = Future { Thread.sleep(2000); 1 }
      val future2: Future[Int] = Future { Thread.sleep(1500); 2 }
      val future3: Future[Int] = Future { Thread.sleep(1000); 3 }

      val futures: List[Future[Int]] = List(future1, future2, future3)
      val completedFutures: Future[List[Int]] = Future.sequence(futures)

      completedFutures.onComplete {
        case Success(results) =>
          val expected: List[Int] = List(1, 2, 3)
          assert(results == expected)
        case Failure(_) =>
          assert(1 == 0)
      }

      Thread.sleep(5000)
    }

    scenario("Merging results of multiple Futures using for comprehension") {
      val future1: Future[Int] = Future { Thread.sleep(2000); 1 }
      val future2: Future[Int] = Future { Thread.sleep(1500); 2 }
      val future3: Future[Int] = Future { Thread.sleep(1000); 3 }

      val result = for {
        result1 <- future1
        result2 <- future2
        result3 <- future3
      } yield result1 + result2 + result3

      result.onComplete {
        case Success(actual) =>
          val expected: Int = 6
          assert(actual == expected)
        case Failure(_) =>
          assert(1 == 0)
      }

      Thread.sleep(5000)
    }
  }
}
