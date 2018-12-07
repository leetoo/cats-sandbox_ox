package me.sandbox.cats.part02functors

import cats.Functor
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import cats.implicits._

object FunctorScalaEx extends App {
  val lab1 = Functor[Option].map(None: Option[String])(_.length)
  println(lab1)
  val source = List("Cats", "is", "awesome")
  val product = Functor[List].fproduct(source)(_.length).toMap
  val pr1 = product.get("Cats").getOrElse(0)
    println(s"product.get(`Cats`).getOrElse(0) = $pr1")
  val pr2 = product.get("is").getOrElse(0)
  println(s"product.get(`is`).getOrElse(0) = $pr2")
  val pr3 = product.get("awesome").getOrElse(0)
  println(s"product.get(`awesome`).getOrElse(0) = $pr3")
  println(s"product == $product" )

  val listOpt = Functor[List] compose Functor[Option]
  val exp4 = listOpt.map(List(Some(1), None, Some(3)))(_ + 1)
  println(s"exp4 = $exp4")


}
object FunctorNotes2 extends App {
  def apply() = {
    val future: Future[String] =
      Future(123)
        .map(n => n + 1)
        .map(n => n * 2)
        .map(n => n + "!")
    println(Await.result(future, 1.second))

    // import cats.syntax.functor._
    // import cats.instances.map._ todo
    //val mapComp = (func1 map func2)(1)
    val andThenComp = (func1 andThen func2) (1)
    val writtenOutComp = func2(func1(1))
    println(andThenComp)
    println(writtenOutComp)
  }
  //mapping over func1 is function composition
  val func1: Int => Double =
    (x: Int) => x.toDouble
  val func2: Double => Double =
    (y: Double) => y * 2
  import cats.instances.int._
  import cats.instances.option._
  import cats.syntax.semigroup._ // for |+|
  Option(1) |+| Option(2)
}


