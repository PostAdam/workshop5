package options

object IntUtils {
  def toInt(s: String): Option[Int] =
    try {
      Some(Integer.parseInt(s.trim))
    } catch {
      case _: Exception => None
    }

  def add(number1: String, number2: String): Option[Int] =
    for {
      a <- toInt(number1)
      b <- toInt(number2)
    } yield a + b

}
