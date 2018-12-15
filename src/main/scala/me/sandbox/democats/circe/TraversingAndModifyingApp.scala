package me.sandbox.democats.circe

object TraversingAndModifyingApp extends App {
  import io.circe._
  import io.circe.parser._

  val json: String = """
 {
 "id": "c730433b-082c-4984-9d66-855c243266f0",
 "name": "Foo",
 "counts": [1, 2, 3],
 "values": {
 "bar": true,
 "baz": 100.001,
 "qux": ["a", "b"]
 }
 } """

  val doc: Json = parse(json).getOrElse(Json.Null)

  val cursor: HCursor = doc.hcursor

  val baz: Decoder.Result[Double] = cursor.downField("values").downField("baz").as[Double]
  println(s"baz = $baz")


  val baz_get: Decoder.Result[Double] = cursor.downField("values").get[Double]("baz")
  println(s"baz_get = $baz_get")

  val secondQux: Decoder.Result[String] =
    cursor.downField("values").downField("qux").downArray.right.as[String]
  println(s"secondQux = $secondQux")

  val reversedNameCursor: ACursor =
    cursor.downField("name").withFocus(_.mapString(_.reverse))
  println(s"reversedNameCursor = ${reversedNameCursor}")

  val reversedName: Option[Json] = reversedNameCursor.top
  println(s"reversedName = ${reversedName}")

  val nameResult  =
    cursor.downField("name").withFocus(_.mapString(_.reverse)).as[String]
  println(s"nameResult = ${nameResult}")





}
