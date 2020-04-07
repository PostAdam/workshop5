package stringinterpolation

object StringInterpolators {

  implicit class UppercaseInterpolator(sc: StringContext) {
    def uppercase(args: Any*): String = {
      val strings = sc.parts.iterator
      val expressions = args.iterator

      val buffer = new StringBuffer(strings.next)
      while(strings.hasNext) {
        buffer append expressions.next
        buffer append strings.next
      }

      buffer.toString.toUpperCase
    }
  }

}
