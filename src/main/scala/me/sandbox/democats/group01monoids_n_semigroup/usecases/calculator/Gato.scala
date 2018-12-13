package me.sandbox.democats.group01monoids_n_semigroup.usecases.calculator

final case class Gato(name: String, age: Int, color: String)

object Gato {
  import cats.Eq
  import cats.syntax.eq._

  // TODO 01: define equality for Gato
  implicit val catEquality = new Eq[Gato] {
    override def eqv(x: Gato, y: Gato): Boolean = ???
  }

  // This function is here is only to avoid the clash with scalatest!
  // TODO 01: use the function '===' to compare cats
  def isEqual(first: Gato, second: Gato) =
    first === second
}
