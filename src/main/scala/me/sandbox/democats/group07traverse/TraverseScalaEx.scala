package me.sandbox.democats.group07traverse

import cats.Applicative

object TraverseScalaEx extends App {

  import cats.data.{Validated, ValidatedNel}
  import cats.implicits._

  trait Traverse[F[_]] {
    def traverse[G[_]: Applicative, A, B](fa: F[A])(f: A => G[B]): G[F[B]]
  }

  def parseIntEither(s: String): Either[NumberFormatException, Int] =
    Either.catchOnly[NumberFormatException](s.toInt)

  def parseIntValidated(s: String): ValidatedNel[NumberFormatException, Int] =
    Validated.catchOnly[NumberFormatException](s.toInt).toValidatedNel

 /* private val value: Any = List("1", "2", "3").traverseU(parseIntEither)

  List("1", "abc", "3").traverseU(parseIntEither).isLeft*/
// SEQUENCING
  //Sometimes you may find yourself with a collection of data, each of which is already in an effect,
  // for instance a List[Option[A]]. To make this easier to work with, you want a Option[List[A]].
  // Given Option has an Applicative instance, we can traverse over the list with the identity function.

  import cats.implicits._



//  List(Option(1), Option(2), Option(3)).traverse(identity)
//  List(Option(1), None, Option(3)).traverse(identity)

  // TRAVERSING FOR EFFECT
  //Sometimes our effectful functions return a Unit value in cases where there is no interesting
  // value to return (e.g. writing to some sort of store).

  private val sequence01: Option[Unit] = List(Option(1), Option(2), Option(3)).sequence_
  println(s"sequence01 = ${sequence01}")
  private val sequence02: Option[Unit] = List(Option(1), None, Option(3)).sequence_
  println(s"sequence02 = ${sequence02}")

}
