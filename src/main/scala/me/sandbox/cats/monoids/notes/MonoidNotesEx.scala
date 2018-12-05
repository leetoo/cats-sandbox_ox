package me.sandbox.cats.monoids.notes

import cats.Monoid // Provides the |+| operator for combining two Monoids.
object MonoidNotesEx extends App {





  val monoidCombineMap = Monoid[Map[String, Int]].combineAll(List(Map("a" → 1, "b" → 2), Map("a" → 3)))
  println(monoidCombineMap)

}
