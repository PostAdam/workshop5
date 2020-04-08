package exceptions

class CustomException(private val message: String = "",
                                 private val cause: Throwable = None.orNull)
  extends Exception(message, cause)


class CustomInheritingException(private val message: String = "",
                                private val cause: Throwable = None.orNull)
  extends CustomException(message, cause)

