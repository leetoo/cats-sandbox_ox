package me.sandbox.democats

import cats.kernel.Semigroup
import me.sandbox.democats.fixtures.OrderFixture
import org.scalatest.{MustMatchers, WordSpec}

class SemigroupScalaExSpec extends WordSpec with MustMatchers with OrderFixture {

  "simple operations" must {
    "work on Int" in {
      import cats.implicits._
      Semigroup[Int].combine(1, 2) must be(3)
      Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6)) must be(List(1, 2, 3, 4, 5, 6))
      Semigroup[Option[Int]].combine(Option(1), Option(2)) must be(Some(3))
      Semigroup[Option[Int]].combine(Option(1), None) must be(Some(1))
      Semigroup[Int => Int].combine(_ + 1, _ * 10).apply(6) must be(67)
      val aMap = Map("foo" → Map("bar" → 5))
      val anotherMap = Map("foo" → Map("bar" → 6))
      val combinedMap = Semigroup[Map[String, Map[String, Int]]].combine(aMap, anotherMap)
      combinedMap.get("foo") must be(Some(
        Map("bar" -> 11)
      ))
      import cats.implicits._
      val one: Option[Int] = Option(1)
      val two: Option[Int] = Option(2)
      val n: Option[Int] = None
      one |+| two must be(Some(3))
      n |+| two must be(Some(2))
      n |+| n must be(None)
      two |+| n must be(Some(2))
    }
  }
}
