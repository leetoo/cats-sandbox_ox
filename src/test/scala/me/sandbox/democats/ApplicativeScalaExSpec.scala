package me.sandbox.democats

import me.sandbox.democats.fixtures.OrderFixture
import org.scalatest.{MustMatchers, WordSpec}

class ApplicativeScalaExSpec extends WordSpec with MustMatchers with OrderFixture {
  "ApplicativeScalaExSpec" must {
    "Applicative simple operations " in {
      import cats._
      import cats.implicits._

      Applicative[Option].pure(1) must be(
        Some(1)
      )
      Applicative[List].pure(1) must be(
        List(1)
      )

      (Applicative[List] compose Applicative[Option]).pure(1) must be(
        List(Some(1))
      )

// APPLICATIVE FUNCTORS & MONADS
      //Applicative is a generalization of Monad, allowing expression of effectful computations in a pure functional way.
      //
      //Applicative is generally preferred to Monad when the structure of a computation is fixed a priori. That makes it possible to perform certain kinds of static analysis on applicative values.

      Monad[Option].pure(1) must be(
        Some(1)
      )
      Applicative[Option].pure(1) must be(
        Some(1)
      )

    }
  } // end of test pack
} // end of class
