import org.scalatest.FeatureSpec

class NullsSpec extends FeatureSpec {


  feature("Null") {
    scenario("Assign null") {
//      val int: Int = null
      val list: List[String] = null
    }

    scenario("NullPointerException") {
      val list: List[String] = null
      assertThrows[NullPointerException] {
        list.appended("string")
      }
    }
  }

  feature("Nil") {
    scenario("Nil is List()") {
      assert(Nil == List())
      assert(Nil eq List())
      assert(Nil equals List())
    }
  }

}
