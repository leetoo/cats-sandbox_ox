package me.sandbox.democats.circe

object CirceOpticsApp extends App {
  import io.circe._
  import io.circe.parser._

  val json: Json = parse(
    """
{
"order": {
 "customer": {
   "name": "Custy McCustomer",
   "contactDetails": {
      "address": "1 Fake Street, London, England",
      "phone": "0123-456-789"
  }
},
"items": [{
  "id": 123,
    "description": "banana",
   "quantity": 1
}, {
   "id": 456,
    "description": "apple",
   "quantity": 2
}],
"total": 123.45
}
}
""").getOrElse(Json.Null)

  val phoneNum: Option[String] = json.hcursor.downField("order").
    downField("customer").
    downField("contactDetails").
    get[String]("phone").
    toOption
  println(s"phoneNum = ${phoneNum}")

  import io.circe.optics.JsonPath._
  // import io.circe.optics.JsonPath._

  val _address = root.order.customer.contactDetails.address.string

  val address: Option[String] = _address.getOption(json)
  println(s"address = ${address}")

  val items: Vector[Json] = json.hcursor.
    downField("order").
    downField("items").
    focus.
    flatMap(_.asArray).
    getOrElse(Vector.empty)
  println(s"items = ${items}")

  val descriptions: Vector[String] =
    items.flatMap(_.hcursor.get[String]("description").toOption)
  // descriptions: Vector[String] = Vector("banana", "apple")
  println(s"descriptions = ${descriptions}")

  val itemsOptincts: List[String] = root.order.items.each.description.string.getAll(json)
  println(s"itemsOptincts = ${itemsOptincts}")
  /**
    * Modifying JSON
    */

  val doubleQuantities: Json => Json = root.order.items.each.quantity.int.modify(_ * 2)
  println(s"doubleQuantities = ${doubleQuantities}")

  val modifiedJson = doubleQuantities(json)
  println(s"modifiedJson = ${modifiedJson}")

  val modifiedQuantities: List[Int] = root.order.items.each.quantity.int.getAll(modifiedJson)
  println(s"modifiedQuantities = ${modifiedQuantities}")


}


