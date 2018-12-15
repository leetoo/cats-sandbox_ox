package me.sandbox.democats.circe

import io.circe.Json

object CirceJsonIntroApp extends App {
  // fromString example
  def jsonString: Json = Json.fromString("scala exercises")
  println(s"jsonString = $jsonString")

  // fromDouble example
  val jsonDouble: Option[Json] = Json.fromDouble(1)



  val jsonBoolean: Json = Json.fromBoolean(true)

  // fromFields example
  val fieldList  = List(
    ("key1", Json.fromString("value1")),
    ("key2", Json.fromInt(1)))
  println(s"fieldList = ${fieldList}")

  val jsonFromFields: Json = Json.fromFields(fieldList)
  println(s"jsonFromFields = $jsonFromFields")
  println(s"jsonFromFields noSpaces= ${jsonFromFields.noSpaces}")
  println(s"jsonFromFields spaces2= ${jsonFromFields.spaces2}")
  println(s"jsonFromFields spaces4= ${jsonFromFields.spaces4}")

  //
  val fieldList1  = List(
    ("key", Json.fromString("value"))
  )



  val jsonTest: Json = Json.fromFields(fieldList1)
  val jsonTest1: Json = Json.fromFields(
    List(("key",Json.fromString("value")) )
  )








  println(s"jsonFromFields1 noSpaces= ${jsonTest.noSpaces}")
  println(s"jsonTest1 noSpaces= ${jsonTest1.noSpaces}")

  val complexJson: Json = Json.fromFields(
    List(
      ("name",Json.fromString("sample json")),
      ("data",Json.fromString("{done, :false}")) )
  )

  val complexJson1: Json = Json.fromFields(
    List(
      ("name",Json.fromString("sample json")),
      ("data",Json.fromFields(
        List((
          "done", Json.fromBoolean(false))
      )
      )
  )
    )
  )

  println(s"complexJson = ${complexJson.noSpaces}")
  println(s"complexJson1 = ${complexJson1.noSpaces}")

  val jsonTest2 = Json.fromFields(
    List(("x",Json.fromInt(1)) )
  )
  println(s"jsonTest2 = ${jsonTest2.noSpaces}")


  val jsonTest3 = Json.fromValues(
    List(Json.fromFields(List(("x",Json.fromInt(1)))) )
  ).noSpaces

  println(s"jsonTest3 = ${jsonTest3}")

  val jsonArray: Json = Json.fromValues(List(
    Json.fromFields(List(("field1", Json.fromInt(1)))),
    Json.fromFields(List(
      ("field1", Json.fromInt(200)),
      ("field2", Json.fromString("Having circe in Scala Exercises is awesome"))
    ))
  ))

  println(s"jsonArray = $jsonArray")

  def transformJson(jsonArray: Json): Json =
    jsonArray mapArray { oneJson: Vector[Json] =>
      oneJson.init
    }

  println(s"jsonArrayTransformed = ${transformJson(jsonArray).noSpaces}")





}
