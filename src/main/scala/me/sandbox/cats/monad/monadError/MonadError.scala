        package me.sandbox.cats.monad.monadError // Provides the |+| operator for combining two Monoids.
object MonoidNotesEx extends App {


            import cats.MonadError
          import cats.instances.either._ // for MonadError

          type ErrorOr[A] = Either[String, A]

          val monadError = MonadError[ErrorOr, String]




}
