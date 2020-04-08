package synchronized

class Counter {
  var result: Int = 0

  def increment(): Unit =
    result += 1

  def incrementSynchronized(): Unit =
    this.synchronized {
      result += 1
    }
}
