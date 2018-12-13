package me.sandbox.democats

import me.sandbox.democats.fixtures.OrderFixture
import org.scalatest.{MustMatchers, WordSpec}

class EitherScalaExSpec extends WordSpec with MustMatchers with OrderFixture {
/*


  import cats.implicits._

  import cats.Monad

  implicit def eitherMonad[Err]: Monad[Either[Err, ?]] =
    new Monad[Either[Err, ?]] {
      def flatMap[A, B](fa: Either[Err, A])(f: A => Either[Err, B]): Either[Err, B] =
        fa.flatMap(f)

      def pure[A](x: A): Either[Err, A] = Either.right(x)
    }

  val right: Either[String, Int] = Either.right(5)
  right.flatMap(x ⇒ Either.right(x + 1)) must be(
    Right(6)
  )

  val left: Either[String, Int] = Either.left("Something went wrong")
  left.flatMap(x ⇒ Either.right(x + 1)) must be(
    Left("Something went wrong")
  )


  "EitherS" must {
    "EitherS simple operations " in {

     // EITHER VS VALIDATED
      //In general, Validated is used to accumulate errors, while Either is used to short-circuit a computation upon the first error. For more information, see the Validated vs Either section of the Validated documentation.
      //
      //More often than not we want to just bias towards one side and call it a day - by convention, the right side is most often chosen.
      import cats.implicits._
      val right: Either[String, Int] = Either.right(5)
      right.map(_ + 1) must be(
        Right(6)
      )

      val left: Either[String, Int] = Either.left("Something went wrong")
      left.map(_ + 1) must be(
        Left("Something went wrong")
      )



      // USING EITHER INSTEAD OF EXCEPTIONS
      //As a running example, we will have a series of functions that will parse a string into an integer, take the reciprocal, and then turn the reciprocal into a string.
      //
      //In exception-throwing code, we would have something like this:

      EitherStyle.parse("Not a number").isRight must be(
        false
      )
      EitherStyle.parse("2").isRight must be(
        true
      )

      import EitherStyle._

      magic("0").isRight must be(
        false
      )
      magic("1").isRight must be(
        true
      )
      magic("Not a number").isRight must be(
        false
      )

      import EitherStyle._

      val result = magic("2") match {
        case Left(_: NumberFormatException) ⇒ "Not a number!"
        case Left(_: IllegalArgumentException) ⇒ "Can't take reciprocal of 0!"
        case Left(_) ⇒ "Unknown error"
        case Right(result) ⇒ s"Got reciprocal: ${result}"
      }
      result must be(
        "Got reciprocal: 0.5"
      )

      import EitherStyleWithAdts._

      val result = magic2("2") match {
        case Left(NotANumber(_)) ⇒ "Not a number!"
        case Left(NoZeroReciprocal) ⇒ "Can't take reciprocal of 0!"
        case Right(result) ⇒ s"Got reciprocal: ${result}"
      }
      result must be(
        "Got reciprocal: 0.5"
      )

    // EITHER IN THE SMALL, EITHER IN THE LARGE
      //Once you start using Either for all your error-handling, you may quickly run into an issue where you need to call into two separate modules which give back separate kinds of errors.

      // Solution 1: Application-wide errors

      // Solution 2: ADTs all the way down
      //Instead of lumping all our errors into one big ADT, we can instead keep them local to each module, and have an application-wide error ADT that wraps each error ADT we need.

      val right: Either[String, Int] = Right(41)
      right.map(_ + 1) must be(
        Right(42)
      )

      val left: Either[String, Int] = Left("Hello")
      left.map(_ + 1) must be(
        Left("Hello")
      )
      left.leftMap(_.reverse) must be(
        Left("olleH")
      )

      Either.catchOnly[NumberFormatException]("abc".toInt).isRight must be(
        false
      )

      Either.catchNonFatal(1 / 0).isLeft must be(
        true
      )

      import cats.implicits._

      val right: Either[String, Int] = 42.asRight[String]
      right must be(
        Right(42)
      )


    }
  } // end of test pack
  object ExceptionStyle {
    def parse(s: String): Int =
      if (s.matches("-?[0-9]+")) s.toInt
      else throw new NumberFormatException(s"${s} is not a valid integer.")

    def reciprocal(i: Int): Double =
      if (i == 0) throw new IllegalArgumentException("Cannot take reciprocal of 0.")
      else 1.0 / i

    def stringify(d: Double): String = d.toString

  }


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

    def magic2(s: String): Either[Error, String] =
      parse(s).flatMap(reciprocal).map(stringify)
  }

  */
} // end of class


