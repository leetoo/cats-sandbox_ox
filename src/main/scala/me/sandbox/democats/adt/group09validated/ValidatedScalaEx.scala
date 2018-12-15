package me.sandbox.democats.adt.group09validated

import cats.data.Validated.{Invalid, Valid}
import cats.data.{NonEmptyList, Validated}
import cats.{Semigroup, SemigroupK}




trait Read[A] {
  def read(s: String): Option[A]
}



sealed abstract class ConfigError
final case class MissingConfig(field: String) extends ConfigError
final case class ParseError(field: String) extends ConfigError



case class Config(map: Map[String, String]) {
  def parse[A: Read](key: String): Validated[ConfigError, A] =
    map.get(key) match {
      case None => Invalid(MissingConfig(key))
      case Some(value) =>
        Read[A].read(value) match {
          case None => Invalid(ParseError(key))
          case Some(a) => Valid(a)
        }
    }
}
  case class ConnectionParams(url: String, port: Int)

object Read {
  def apply[A](implicit A: Read[A]): Read[A] = A

  implicit val stringRead: Read[String] =
    new Read[String] { def read(s: String): Option[String] = Some(s) }

  implicit val intRead: Read[Int] =
    new Read[Int] {
      def read(s: String): Option[Int] =
        if (s.matches("-?[0-9]+")) Some(s.toInt)
        else None
    }
}

  object ValidatedScalaEx extends App {

    import cats.data.Validated
    implicit val nelSemigroup: Semigroup[NonEmptyList[ConfigError]] =
      SemigroupK[NonEmptyList].algebra[ConfigError]

    implicit val readString: Read[String] = Read.stringRead
    implicit val readInt: Read[Int] = Read.intRead
     val config = Config(Map(("url", "127.0.0.1"), ("port", "1337")))

    val valid = parallelValidate(
      config.parse[String]("url").toValidatedNel,
      config.parse[Int]("port").toValidatedNel
    )(ConnectionParams.apply)
    println(s"valid = ${valid}")

    private val params: ConnectionParams = valid.getOrElse(ConnectionParams("", 0))
    println(s"params = ${params}")
    private val valid1: Boolean = valid.isValid
    println(s"valid1 = ${valid1}")


    def parallelValidate[E, A, B, C](v1: Validated[E, A], v2: Validated[E, B])(f: (A, B) => C): Validated[E, C] =
    (v1, v2) match {
      case (Valid(a), Valid(b)) => Valid(f(a, b))
      case (Valid(_), i@Invalid(_)) => i
      case (i@Invalid(_), Valid(_)) => i
      case (Invalid(_), Invalid(_)) => throw new Exception()
    }





}



