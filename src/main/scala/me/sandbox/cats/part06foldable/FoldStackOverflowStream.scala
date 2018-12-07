package me.sandbox.cats.part06foldable

import cats.{Eval, Foldable}

object FoldStackOverflowStream extends App {



  def bigData = (1 to 100000).toStream


  //bigData.foldRight(0L)(_ + _)

  // ^ should give stack overflow exception

  import cats.instances.stream._

  val eval: Eval[Long] =
    Foldable[Stream].
      foldRight(bigData, Eval.now(0L)) { (num, eval) =>
        eval.map(_ + num)
      }

  println(s"eval.value = ${eval.value}")
  /**
    * Explicits over Implicits
    * Remember that Scala will only use an instance of Foldable if the method isn’t
    * explicitly available on the receiver. For example, the following code will use
    * the version of foldLeft defined on List:
    *
    * point : 7.1.4.3
    *
    */

  List(1, 2, 3).foldLeft(0)(_ + _)
  // res18: Int = 6

  import cats.syntax.foldable._

  import scala.language.higherKinds



  def sum[F[_]: Foldable](values: F[Int]): Int =  values.foldLeft(0)(_ + _)




}
