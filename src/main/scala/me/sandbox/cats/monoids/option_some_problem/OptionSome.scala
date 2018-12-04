package me.sandbox.cats.monoids.option_some_problem

// for |+|

import cats.Monoid
import cats.syntax.semigroup._ // for |+|


object Monoids  extends App {

  def add[A](items: List[A])(implicit monoid: Monoid[A]): A =
    items.foldLeft(monoid.empty)(_ |+| _) // for Monoid




  // add(List(Some(1), None, Some(2), None, Some(3)))

  // Error:(15, 6) could not find implicit value for parameter monoid: cats.Monoid[Option[Int]]
  // add(List(Some(1), None, Some(2), None, Some(3)))

}
