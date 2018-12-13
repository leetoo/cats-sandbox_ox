package me.sandbox.democats

import cats.Apply
import me.sandbox.democats.fixtures.OrderFixture
import org.scalatest.{MustMatchers, WordSpec}

class ApplyScalaExSpec extends WordSpec with MustMatchers with OrderFixture {
  "Apply" must {
    "Apply simple operations " in {
      // MAP
      //Since Apply extends Functor, we can use the map method from Functor:
      import cats.implicits._
      val intToString: Int ⇒ String = _.toString
      val double: Int ⇒ Int = _ * 2
      val addTwo: Int ⇒ Int = _ + 2
      Apply[Option].map(Some(1))(intToString) must be(
        Some("1")
      )
      Apply[Option].map(Some(1))(double) must be(
        Some(2)
      )
      Apply[Option].map(None)(addTwo) must be(
        None
      )

// COMPOSE
      //And like functors, Apply instances also compose:

      val listOpt = Apply[List] compose Apply[Option]
      val plusOne = (x: Int) ⇒ x + 1
      listOpt.ap(List(Some(plusOne)))(List(Some(1), None, Some(3))) must be(
        List(Some(2), None, Some(4))
      )

      // AP
      //The ap method is a method that Functor does not have:

      Apply[Option].ap(Some(intToString))(Some(1)) must be(
        Some("1")
      )
      Apply[Option].ap(Some(double))(Some(1)) must be(
        Some(2)
      )
      Apply[Option].ap(Some(double))(None) must be(
        None
      )
      Apply[Option].ap(None)(Some(1)) must be(
        None
      )
      Apply[Option].ap(None)(None) must be(
        None
      )

      // AP2, AP3, ETC
      //Apply also offers variants of ap. The functions apN (for N between 2 and 22) accept N arguments where ap accepts 1.
      //
      //Note that if any of the arguments of this example is None, the final result is None as well. The effects of the context we are operating on are carried through the entire computation:


      val addArity2 = (a: Int, b: Int) ⇒ a + b
      Apply[Option].ap2(Some(addArity2))(Some(1), Some(2)) must be(
        Some(3)
      )
      Apply[Option].ap2(Some(addArity2))(Some(1), None) must be(
        None
      )

      val addArity3 = (a: Int, b: Int, c: Int) ⇒ a + b + c
      Apply[Option].ap3(Some(addArity3))(Some(1), Some(2), Some(3)) must be(
        Some(6)
      )

      // MAP2, MAP3, ETC
      //Similarly, mapN functions are available:


      Apply[Option].map2(Some(1), Some(2))(addArity2) must be(
        Some(3)
      )

      Apply[Option].map3(Some(1), Some(2), Some(3))(addArity3) must be(
        Some(6)
      )

      // TUPLE2, TUPLE3, ETC
      //Similarly, tupleN functions are available:

      Apply[Option].tuple2(Some(1), Some(2)) must be(
        Some((1,2))
      )
      Apply[Option].tuple3(Some(1), Some(2), Some(3)) must be(
        Some((1,2,3))
      )

      // APPLY BUILDER SYNTAX
      //The |@| operator offers an alternative syntax for the higher-arity Apply functions (apN, mapN and tupleN). In order to use it, first import cats.implicits._.
      //
      //All instances created by |@| have map, ap, and tupled methods of the appropriate arity:



      /** DEPRECATED but correct
        *
        * val option2 = Option(1) |@| Option(2)
        * val option3 = option2 |@| Option.empty[Int]
        *
        * option2 map addArity2 must be(
        * Some(3)
        * )
        * option3 map addArity3 must be(
        * None
        * )
        *
        * option2 apWith Some(addArity2) must be(
        * Some(3)
        * )
        * option3 apWith Some(addArity3) must be(
        * None
        * )
        *
        * option2.tupled must be(
        * Some((1,2))
        * )
        * option3.tupled must be(
        * None
        * )
        *
        */

    }
  } // end of test pack
} // end of class
