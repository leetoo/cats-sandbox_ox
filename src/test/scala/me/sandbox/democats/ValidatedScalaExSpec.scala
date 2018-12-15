package me.sandbox.democats

import me.sandbox.democats.adt.group09validated._
import org.scalatest.{MustMatchers, WordSpec}

sealed abstract class Validated[+E, +A]
class ValidatedScalaExSpec extends WordSpec with MustMatchers {



  "ValidatedScalaExSpec" must {
    import me.sandbox.democats.adt.group09validated.ValidatedScalaEx._

    "ValidatedScalaExSpec simple operations " in {

      implicit val readString: Read[String] = Read.stringRead
      implicit val readInt: Read[Int] = Read.intRead
       val config = Config(Map(("url", "127.0.0.1"), ("port", "1337")))

      val valid = parallelValidate(
        config.parse[String]("url").toValidatedNel,
        config.parse[Int]("port").toValidatedNel
      )(ConnectionParams.apply)
      valid.isValid must be(
        true
      )
      valid.getOrElse(ConnectionParams("", 0)) must be(ConnectionParams(
        "127.0.0.1"
        ,
        1337
      ))



      val invalid = parallelValidate(
        config.parse[String]("url").toValidatedNel,
        config.parse[Int]("port").toValidatedNel
      )(ConnectionParams.apply)

      import cats.data.{NonEmptyList, Validated}

      invalid.isValid must be(
        false
      )
      val errors = NonEmptyList(MissingConfig("url"), List(ParseError("port")))
      invalid == Validated.invalid(errors) must be(
        true
      )



      val houseNumber = config.parse[Int]("house_number").andThen { n ⇒
        if (n >= 0) Validated.valid(n)
        else Validated.invalid(ParseError("house_number"))
      }

      houseNumber.isValid must be(
        false
      )
     /*
     val error = ParseError("house_number")
      houseNumber == Validated.invalid(error) must be(
        true
      )
    */





      houseNumber.isValid must be(
        false
      )
      val error = ParseError("house_number")
      houseNumber == Validated.invalid(error) must be(
        true
      )
    }


  } // end of test pack
} // end of class
