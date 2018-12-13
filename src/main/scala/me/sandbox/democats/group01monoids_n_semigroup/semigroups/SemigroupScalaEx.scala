package me.sandbox.democats.group01monoids_n_semigroup.semigroups

import cats.kernel.Semigroup

object SemigroupScalaEx extends App {


  import cats.implicits._

  private val i: Int = Semigroup[Int].combine(1, 2)
  println(s"i = ${i}")

  private val ints: List[Int] = Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6))
  println(s"ints = ${ints}")
  private val maybeInt: Option[Int] = Semigroup[Option[Int]].combine(Option(1), Option(2))
  println(s"maybeInt = ${maybeInt}")
  private val maybeInt2: Option[Int] = Semigroup[Option[Int]].combine(Option(1), None)
  println(s"maybeInt2 = ${maybeInt2}")

  private val i2 = Semigroup[Int => Int].combine(_ + 1, _ * 10).apply(6)
  println(s"i2 = ${i2}")


}
