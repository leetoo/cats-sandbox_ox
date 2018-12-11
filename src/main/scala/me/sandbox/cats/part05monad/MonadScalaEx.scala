package me.sandbox.cats.part05monad

import cats.Monad


object MonadScalaEx extends App {
  import cats.implicits._


  private val ints: List[Int] = Monad[List].flatMap(List(1, 2, 3))(x ⇒ List(x, x))
  println(s"ints = ${ints}")
  private val MonadIfM01: Option[String] = Monad[Option].ifM(Option(true))(Option("truthy"), Option("falsy"))
  println(s"MonadIfM01 = $MonadIfM01")
  private val MonadIfM02: List[Int] = Monad[List].ifM(List(true, false, true))(List(1, 2), List(3, 4))
  println(s"MonadIfM02 = $MonadIfM02")

  case class OptionT[F[_], A](value: F[Option[A]])

  implicit def optionTMonad[F[_]](implicit F: Monad[F]) = {
    new Monad[OptionT[F, ?]] {
      def pure[A](a: A): OptionT[F, A] = OptionT(F.pure(Some(a)))
      def flatMap[A, B](fa: OptionT[F, A])(f: A => OptionT[F, B]): OptionT[F, B] =
        OptionT {
          F.flatMap(fa.value) {
            case None => F.pure(None)
            case Some(a) => f(  a).value
          }
        }
      def tailRecM[A, B](a: A)(f: A => OptionT[F, Either[A, B]]): OptionT[F, B] = tailRecM(a)(f)

    }
  }

  private val valueOptTMonad: OptionT[List, Int] = optionTMonad[List].pure(42)
  println(s"valueOptTMonad = $valueOptTMonad")



}
