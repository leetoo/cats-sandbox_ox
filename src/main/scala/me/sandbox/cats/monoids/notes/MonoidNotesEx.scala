package me.sandbox.cats.monoids.notes

import cats.Monoid
object MonoidNotesEx extends App {


  import cats.implicits._


  val monoidCombineMap = Monoid[Map[String, Int]].combineAll(List(Map("a" → 1, "b" → 2), Map("a" → 3)))
  println(monoidCombineMap)

}
