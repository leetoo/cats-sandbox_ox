package me.sandbox.democats

import me.sandbox.democats.fixtures.OrderFixture
import org.scalatest.{MustMatchers, WordSpec}

class MonadScalaExSpec extends WordSpec with MustMatchers with OrderFixture {
  import cats._
  import cats.implicits._

  case class OptionT[F[_], A](value: F[Option[A]])

  implicit def optionTMonad[F[_]](implicit F: Monad[F]) = {
    new Monad[OptionT[F, ?]] {
      def pure[A](a: A): OptionT[F, A] = OptionT(F.pure(Some(a)))
      def flatMap[A, B](fa: OptionT[F, A])(f: A => OptionT[F, B]): OptionT[F, B] =
        OptionT {
          F.flatMap(fa.value) {
            case None => F.pure(None)
            case Some(a) => f(a).value
          }
        }
      def tailRecM[A, B](a: A)(f: A => OptionT[F, Either[A, B]]): OptionT[F, B] = tailRecM(a)(f)
    }
  }
 
  "Monad" must {
    "Monad simple operations " in {

      Option(Option(1)).flatten must be(
        Option(1)
      )
      Option(None).flatten must be(
        None
      )
      List(List(1), List(2, 3)).flatten must be(
        List(1,2,3)
      )
     // MONAD INSTANCES
      //If Applicative is already present and flatten is well-behaved, extending the Applicative to a Monad is trivial. To provide evidence that a type belongs in the Monad type class, cats' implementation requires us to provide an implementation of pure (which can be reused from Applicative) and flatMap.
      //
      //We can use flatten to define flatMap: flatMap is just map followed by flatten. Conversely, flatten is just flatMap using the identity function x => x (i.e. flatMap(_)(x => x)).


      Monad[Option].pure(42) must be(
        Option(42)
      )

// FLATMAP
      //flatMap is often considered to be the core function of Monad, and cats follows this tradition by providing implementations of flatten and map derived from flatMap and pure.

      Monad[List].flatMap(List(1, 2, 3))(x ⇒ List(x, x)) must be(
        List(1,1,2,2,3,3)
      )

      // IFM
      //Monad provides the ability to choose later operations in a sequence based on the results of earlier ones. This is embodied in ifM, which lifts an if statement into the monadic context.
      import cats._
      import cats.implicits._

      Monad[Option].ifM(Option(true))(Option("truthy"), Option("falsy")) must be(
        Some("truthy")
      )
      Monad[List].ifM(List(true, false, true))(List(1, 2), List(3, 4)) must be(
        List(1, 2, 3, 4, 1, 2)
      )

      // COMPOSITION
      //Unlike Functors and Applicatives, you cannot derive a monad instance for a generic M[N[_]] where both M[_] and N[_] have an instance of a monad.
      //
      //However, it is common to want to compose the effects of both M[_] and N[_]. One way of expressing this is to provide instructions on how to compose any outer monad (F in the following example) with a specific inner monad (Option in the following example).

      /*

      optionTMonad[List].pure(42) must be(OptionT(
        List(Some(42))
      ))
     */
    }
  } // end of test pack
} // end of class


