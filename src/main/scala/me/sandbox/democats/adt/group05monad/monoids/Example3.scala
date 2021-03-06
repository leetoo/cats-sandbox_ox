package me.sandbox.democats.adt.group05monad.monoids

import cats.Monoid
import cats.instances.int._
import cats.instances.option._
import cats.syntax.semigroup._

object Example3 extends App {

  def addWithoutMonoid(list: List[Option[Int]]): Option[Int] = {
    list.foldLeft(Option(0)){
      case (acc, value) => acc.map(i => value.fold(i)( _ + i ))
    }
  }

  def addWithMonoid(list: List[Option[Int]]): Option[Int] = {
    list.foldLeft(Monoid.empty[Option[Int]])(_ |+| _)
  }

  val result1 = addWithoutMonoid(List(Some(1), Some(8), Some(7), Some(5)))
  val result2 = addWithMonoid(List(Some(1), Some(8), Some(7), Some(5)))

  println(s" Reuslt is: ${result1}")
  assert(result1 == result2)
}
