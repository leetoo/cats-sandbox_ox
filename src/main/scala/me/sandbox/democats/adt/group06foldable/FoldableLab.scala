package me.sandbox.democats.adt.group06foldable

// for |+|
import cats.Foldable
import cats.instances.option._

object FoldableLab extends App {
  val maybeInt = Option(123)
  val result = Foldable[Option].foldLeft(maybeInt, 10)(_ * _)
  println(s"result = $result")
  val maybeIntSimple = Option(123)
  val resultSimple = maybeIntSimple.foldLeft(10)(_ * _)
  println(s"resultSimple = $resultSimple")



}
