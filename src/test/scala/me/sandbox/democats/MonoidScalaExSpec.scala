package me.sandbox.democats

import cats.kernel.Monoid
import me.sandbox.democats.fixtures.OrderFixture
import org.scalatest.{MustMatchers, WordSpec}

class MonoidScalaExSpec extends WordSpec with MustMatchers with OrderFixture {
  "Monoid" must {
    import cats.implicits._
    "Monoid simple operations " in {
      Monoid[String].empty must be(
        ""
      )
      Monoid[String].combineAll(List("a", "b", "c")) must be(
        "abc"
      )
      Monoid[String].combineAll(List()) must be(
        ""
      )
      Monoid[Map[String, Int]].combineAll(List(Map("a" → 1, "b" → 2), Map("a" → 3))) must be(
        Map("a" -> 4, "b" -> 2)
      )
      Monoid[Map[String, Int]].combineAll(List()) must be(
        Map()
      )
    }
    "Monoid simple operations  2" in {
      import cats.implicits._

      val list = List(1, 2, 3, 4, 5)
      list.foldMap(i ⇒ (i, i.toString)) must be(
        (15, "12345")
      )
    }
  }
}
