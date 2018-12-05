package me.sandbox.cats.functors

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
object FunctorNotes2 extends App {
  def apply() = {
    val future: Future[String] =
      Future(123)
        .map(n => n + 1)
        .map(n => n * 2)
        .map(n => n + "!")

    println(Await.result(future, 1.second))

    // import cats.syntax.functor._
    // import cats.implicits._
    // import cats.instances.map._ todo
    //val mapComp = (func1 map func2)(1)

    val andThenComp = (func1 andThen func2)(1)

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
