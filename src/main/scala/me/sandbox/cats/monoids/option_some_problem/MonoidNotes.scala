package me.sandbox.cats.monoids.option_some_problem

import cats.Monoid
import cats.instances.int._
import cats.syntax.eq.catsSyntaxEq
import cats.syntax.semigroup.catsSyntaxSemigroup // Provides the |+| operator for combining two Monoids.
object MonoidNotes extends App {
  // Combine two ints using the |+| operator.
  println(s"1 |+| 2 = ${1 |+| 2}")

  // Probe the previous operation is equivalent to calling the combine method explicitly.
  println(s"(1 |+| 2) === Monoid[Int].combine(1, 2) is ${(1 |+| 2) === Monoid[Int].combine(1, 2)}")

  // Ensure the cat's Monoid for ints uses the integer addition as the combine operation
  // and the integer's zero as the empty element.
  println(s"(1 |+| Monoid[Int].empty) === (1 + 0) is ${(1 |+| Monoid[Int].empty) === (1 + 0)}")

  // Create a generic function for adding together all elements in a List using a Monoid.
  def addAll[T](elements: List[T])(implicit TMonoid: Monoid[T]): T =
    elements.foldLeft(TMonoid.empty) { case (a, b) => TMonoid.combine(a, b) }
  println(s"addAll(List(1, 2, 3)) = ${addAll(List(1, 2, 3))}")

  // Let's define our own Monoid for Options.
  implicit def OptionMonoid[T](implicit TMonoid: Monoid[T]): Monoid[Option[T]] =
    new Monoid[Option[T]] {
      override val empty: Option[T] = None
      override def combine(e1: Option[T], e2: Option[T]): Option[T] = (e1, e2) match {
        case (Some(a), Some(b)) => Some(TMonoid.combine(a, b))
        case (Some(a), None)    => Some(a)
        case (None, Some(b))    => Some(b)
        case (None, None)       => None
      }
    }
  println(s"addAll(List(Some(1), None, Some(2), Some(3), None)) = ${addAll(List(Some(1), None, Some(2), Some(3), None))}")
}