package me.sandbox.democats.group01monoids_n_semigroup.notes

import cats.Monoid
import cats.implicits._

object MonoidNotesEx extends App {
  val monoidCombineMap = Monoid[Map[String, Int]].combineAll(List(Map("a" → 1, "b" → 2), Map("a" → 3)))
  println(monoidCombineMap)
  val monoidCombineMapEmpty = Monoid[Map[String, Int]].combineAll(List())
  println(monoidCombineMapEmpty)
}
object MonoidNotesMoreExamples extends App {
  val l = List(1, 2, 3, 4, 5)
  val t3 = l.foldMap(identity)
  println(t3)
  val t4 = l.foldMap(i ⇒ i.toString)
  println(t4)
  val t5 = l.foldMap(i ⇒ (i, i.toString))
  println(t5)

}
