package either

import scala.util.Try

object DoubleUtils {

  def tryToDouble(s: String): Try[Double] = {
    Try(s.toDouble)
  }

  def eitherStringOrDouble(s: String): Either[String, Double] = {
    try {
      Right(s.toDouble)
    } catch {
      case _: Exception => Left(s)
    }
  }
}
