package me.sandbox.cats.part05monad.transformers

import cats.Id
import cats.data.{OptionT, ReaderT, StateT, WriterT}
import cats.instances.either._
import cats.syntax.applicative._
import scala.concurrent.Future
import scala.language.higherKinds // Enable the use of higher-kinded types, like F[_].
object MonadNotes2 extends App {
  Code.applyMonads()


}
object Code {
  def applyMonads() = {
    val a = 10.pure[ErrorOrOption]
    val b = 32.pure[ErrorOrOption]
    val c: OptionT[ErrorOr, Int] =
      a.flatMap(x => b.map(y => x + y))
    println(s"a = $a")
    println(s"b = $b")
    /* create using apply: */
    val errorStack1 = OptionT[ErrorOr, Int](Right(Some(10)))
    /* create using pure: */
    val errorStack2 = 32.pure[ErrorOrOption]

    println(s"errorStack1.value => ${errorStack1.value}")
    println(s" errorStack2.value.map(_.getOrElse(-1)) => ${errorStack2.value.map(_.getOrElse(-1))}")
    println(s"print c => $c")
    /**
      * pure abstracts over constructors, providing a way to create a new monadic context from a plain value.
      */
    //val adressLists = List(List("address1"), List("address2"), List("address3"), List()).pure[ErrorOrOption]



    /*adressLists.forall( x  =>  { // todo finish list two list with error and valid addresses

    })*/


    //
    //    val fullyWrapped: FutureEitherOption[Int] = futureEitherOption
    //
    //    val intermediate: FutureEither[Option[Int]] = fullyWrapped.value
    //
    //    val stack: Future[Either[String, Option[Int]]] = intermediate.stack
    //
    //    val result: Either[String, Option[Int]] =
    //      Await.result(stack, Duration(1, scala.concurrent.duration.SECONDS))
  }
  // Either takes two type params, but monad only takes one
  // Alias Either to a type constructor with one parameter
  type ErrorOr[A] = Either[String, A]
  type ErrorOrOption[A] = OptionT[ErrorOr, A]
  /**
    * Can't define a Future[Either[A,Option[B ] ] ] because
    * we don't have too many type parameters.
    *
    * @param stack
    * @tparam F is the outer monad in the stack (Either is the inner)
    * @tparam E is the error type for the Either
    * @tparam A is the result type for the Either
    */
  case class EitherT[F[_], E, A](stack: F[Either[E, A]])
  // Have to build it up from various type classes
  type FutureEither[A] = EitherT[Future, String, A]
  type FutureEitherOption[A] = OptionT[FutureEither, A]
  //  val futureEitherOption: FutureEitherOption[Int] =
  //    for {
  //      a <- 10.pure[FutureEitherOption]
  //      b <- 10.pure[FutureEitherOption]
  //    } yield a + b
  /**
    * Various monads are actually defined using the corresponding
    * transformer and Id monad. Reader, Writer, and State
    * are all defined this way.
    *
    * When the transformers are defined separately
    * to their corresponding monads they tend to
    * mirror the methods on the monad. ex: EitherT
    * defines fold, bimap, and swap.
    */
  type Reader[E, A] = ReaderT[Id, E, A]
  type Writer[W, A] = WriterT[Id, W, A]
  type State[S, A] = StateT[Id, S, A]
}
