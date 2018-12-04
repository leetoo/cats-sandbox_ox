package me.sandbox.cats.traverse

import cats.Traverse
import cats.instances.int._
import cats.instances.list._
import cats.instances.option._
import cats.syntax.foldable._
import cats.syntax.option._
import cats.syntax.traverse._ // Provides the sequence and traverse methods.
object TraverseNotes extends App {
  // Folding!
  // We can use the foldLeft and foldRight methods,
  // to combine all values in a collection.
  val folded1 = Traverse[List[?]].foldLeft(List(1, 2, 3), List.empty[Int]) {
    (acc, num) => num :: acc
  }
  println(s"Traverse[List[?]].foldLeft(List(1, 2, 3), Nil)(num :: acc) = ${folded1}")
  val folded2 = Traverse[List[?]].foldRight(List(1, 2, 3), cats.Eval.Zero) {
    (num, accEval) => accEval.map(acc => num + acc)
  }
  println(s"Traverse[List[?]].foldRight(List(1, 2, 3), 0)(num + acc) = ${folded2.value}")

  // CombineAll!
  // Usually we want to fold all values in a collection,
  // Using a default (or common) empty value and combine operation.
  // The combineAll method is a short hand for folding a collection of Ts
  // using a available Monoid[T].
  val folded3 = List(1, 2, 3).combineAll
  println(s"List(1, 2, 3).combineAll = ${folded3}")

  // FoldMap!
  // It is also very common to perform a transformation (map)
  // to the collection before combining the values.
  // e.g. `C[A].map(A => B).combineAll`
  // In those cases is better to use the foldMap method,
  // which will save us one iteration over the collection.
  val folded4 = List(1, 2, 3).foldMap(n => n * 2)
  println(s"List(1, 2, 3).foldMap(n => n * 2) = ${folded4}")

  // Sequence!
  // If you have a collection C of effects F of values T,
  // and you want to turn it into an effect of a collection of values F[C[T]].
  // You can use the Traverse sequence method - if there is an Applicative for F.
  val sequenced = List(1.some, 3.some, 5.some).sequence
  println(s"List(Some(1), Some(3), Some(5)).sequence = ${sequenced}")

  // Traverse!
  // Similar to foldMap, if you need to perform some transformation to the values
  // before sequencing the effect with the collection, you can use the traverse method.
  val traversed = List(1, 3, 5).traverse(n => if (n % 2 == 0) none[Int] else n.some)
  println(s"List(1, 3, 5).traverse(n => if (n % 2 == 0) None else Some(n)) = ${traversed}")
}