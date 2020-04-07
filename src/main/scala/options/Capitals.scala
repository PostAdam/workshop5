package options

object Capitals {
  val capitals: Map[String, String] = Map(
    "Poland" -> "Warsaw",
    "Germany" -> "Berlin"
  )

  def getCapital(capital: Option[String]): String =
    capital match {
      case Some(s) => s
      case None => "?"
    }
}
