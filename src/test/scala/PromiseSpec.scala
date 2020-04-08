import org.scalatest.FeatureSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success, Try}

class PromiseSpec extends FeatureSpec {
  feature("Completing Promises") {
    scenario("Promise can be completed") {
      val p = Promise[Int]
      Future {
        Thread sleep 1000
        p success {
          print("Promise completed successfully");
          1
        }
      }

      p success {
        print("Completing promise")
        0
      }
    }

    scenario("Converting Promise to Future and completing with onCompleted") {
      val p = Promise[Int]
      Future {
        Thread sleep 1000
        p success {
          println("Completing Promise")
          1
        }
      }

      p.future onComplete {
        case Success(_) => println("Future of Promise completed")
        case Failure(_) => assert(1 == 0)
      }

      Thread.sleep(2000)
    }

    scenario("Completing Promise with success") {
      val p = Promise[String]
      p.future foreach (x => println(s"p succeeded with '$x'"))
      Thread.sleep(1000)
      p success "assigned"
      Thread.sleep(1000)
    }

    scenario("Completing Promise with failure") {
      val q = Promise[String]
      q failure new Exception("not kept")
      q.future.failed foreach (t => println(s"q failed with $t"))
      Thread.sleep(1000)
    }
/*
    scenario("Promise can be completed using success or failure") {
      def donutStock(donut: String): Int =
        if (donut == "vanilla donut") 10
        else throw new IllegalStateException("Out of stock")

      val donutStockPromise = Promise[Int]()

      val donutStockFuture = donutStockPromise.future
      donutStockFuture.onComplete {
        case Success(stock) => println(s"Stock for vanilla donut = $stock")
        case Failure(e) => println(s"Failed to find vanilla donut stock, exception = $e")
      }

      val donut = "vanilla donut"
      if (donut == "vanilla donut") {
        donutStockPromise.success(donutStock(donut))
      } else {
        donutStockPromise.failure(Try(donutStock(donut)).failed.get)
      }
    }

    scenario("Promise can be completed using complete") {
      def donutStock(donut: String): Int = {
        if (donut == "vanilla donut") 10
        else throw new IllegalStateException("Out of stock")
      }

      val donutStockPromise = Promise[Int]()
      val donutStockFuture = donutStockPromise.future
      donutStockFuture.onComplete {
        case Success(stock) => println(s"Stock for vanilla donut = $stock")
        case Failure(e) => println(s"Failed to find vanilla donut stock, exception = $e")
      }
      donutStockPromise.complete(Try(donutStock("unknown donut")))
    }*/
  }
}
