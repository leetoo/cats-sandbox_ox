package me.sandbox.democats.adt.group08either
import cats.implicits._
object EitherStyle {
  def parse(s: String): Either[NumberFormatException, Int] =
    if (s.matches("-?[0-9]+")) Either.right(s.toInt)
    else Either.left(new NumberFormatException(s"${s} is not a valid integer."))

  def reciprocal(i: Int): Either[IllegalArgumentException, Double] =
    if (i == 0) Either.left(new IllegalArgumentException("Cannot take reciprocal of 0."))
    else Either.right(1.0 / i)

  def stringify(d: Double): String = d.toString

  def magic(s: String): Either[Exception, String] =
    parse(s).flatMap(reciprocal).map(stringify)
}

object EitherStyleWithAdts {
  sealed abstract class Error
  final case class NotANumber(string: String) extends Error
  final case object NoZeroReciprocal extends Error

  def parse(s: String): Either[Error, Int] =
    if (s.matches("-?[0-9]+")) Either.right(s.toInt)
    else Either.left(NotANumber(s))

  def reciprocal(i: Int): Either[Error, Double] =
    if (i == 0) Either.left(NoZeroReciprocal)
    else Either.right(1.0 / i)

  def stringify(d: Double): String = d.toString

  def magic(s: String): Either[Error, String] =
    parse(s).flatMap(reciprocal).map(stringify)
}
object EitherScalaEx extends App {

  import cats.implicits._

// EITHER VS VALIDATED
  val right1: Either[String, Int] = Either.right(5)
  private val stringOrInt01: Either[String, Int] = right1.map(_ + 1)
  println(s"stringOrInt = $stringOrInt01")


  val left1: Either[String, Int] = Either.left("Something went wrong")
  private val stringOrInt02: Either[String, Int] = left1.map(_ + 1)
  println(s"stringOrInt02 = $stringOrInt02")

  val right2: Either[String, Int] = Either.right(5)
  private val stringOrInt03: Either[String, Int] = right2.flatMap(x ⇒ Either.right(x + 1))
  println(s"stringOrInt03 = $stringOrInt03")

  val left2: Either[String, Int] = Either.left("Something went wrong")
  private val stringOrInt04: Either[String, Int] = left2.flatMap(x ⇒ Either.right(x + 1))
  println(s"stringOrInt04 = $stringOrInt04")

  // USING EITHER INSTEAD OF EXCEPTIONS
  //As a running example, we will have a series of functions that will parse a string into an integer, take the reciprocal, and then turn the reciprocal into a string.
  println(s"USING EITHER INSTEAD OF EXCEPTIONS")

  private val eitherStyleParse: Any = EitherStyle.parse("Not a number").isRight
  println(s"eitherStyleParse = ${eitherStyleParse}")
  private val eitherParse02: Any = EitherStyle.parse("2").isRight
  println(s"eitherParse02 = ${eitherParse02}")


import EitherStyle._
  private val magic0: Any = magic("0").isRight
  println(s"magic0 = ${magic0}")
  private val magic1: Any = magic("1").isRight
  println(s"magic1 = ${magic1}")
  private val magicNoN: Any = magic("Not a number").isRight
  println(s"magicNoN = ${magicNoN}")

  import EitherStyle._

  val result01 = magic("2") match {
    case Left(_: NumberFormatException) ⇒ "Not a number!"
    case Left(_: IllegalArgumentException) ⇒ "Can't take reciprocal of 0!"
    case Left(_) ⇒ "Unknown error"
    case Right(result01) ⇒ s"Got reciprocal: ${result01}"
  }
  println(s"result01 = $result01")



  import EitherStyleWithAdts._
  val result02 = EitherStyleWithAdts.magic("2") match {
    case Left(NotANumber(_)) ⇒ "Not a number!"
    case Left(NoZeroReciprocal) ⇒ "Can't take reciprocal of 0!"
    case Right(result02) ⇒ s"Got reciprocal: ${result02}"
  }
  println(s"result02 = $result02")
 // EITHER IN THE SMALL, EITHER IN THE LARGE
  // Once you start using Either for all your error-handling, you may quickly run into an issue where you need to call into two separate modules which give back separate kinds of errors.
  println("\n\n\tSolution 2: ADTs all the way down\n")
  // Solution 2: ADTs all the way down

  val right: Either[String, Int] = Right(41)
  private val stringOrInt: Either[String, Int] = right.map(_ + 1)
  println(s"stringOrInt = ${stringOrInt}")

  val left: Either[String, Int] = Left("Hello")

  private val stringOrInt1: Either[String, Int] = left.map(_ + 1)
  println(s"stringOrInt1 = ${stringOrInt1}")
  private val value02: Either[String, Int] = left.leftMap(_.reverse)
  println(s"value02 = ${value02}")
  // ADDITIONAL SYNTAX
  println("\n\n\tADDITIONAL SYNTAX\n")

  val right11: Either[String, Int] = 42.asRight[String]
  println(s"right11 = ${right11}")

}
