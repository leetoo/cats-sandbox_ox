package me.sandbox.democats.adt.group06foldable

import cats.Foldable

object FoldableScalaEx extends App {
  import cats.implicits._

  private val maybeString01: Option[String] = Foldable[List].foldK(List(None, Option("two"), Option("three")))
  private val maybeString02: Option[String] = Foldable[List].foldK(List(Option("One"), Option("two"), Option("three")))
  //  private val maybeString03  = Foldable[List].foldK(List("One", "two", "three")) // compile error
  /* Error:(47, 52) could not find implicit value for parameter G: cats.MonoidK[Comparable]
   private val maybeString03  = Foldable[List].foldK(List("One", "two", "three"))
   Error:(47, 52) not enough arguments for method foldK: (implicit G: cats.MonoidK[Comparable])Comparable[String].
     Unspecified value parameter G.
   private val maybeString03  = Foldable[List].foldK(List("One", "two", "three"))*/

  println(s"maybeString = $maybeString01 -  $maybeString02  ")

  // TOLIST
  //Convert F[A] to List[A].

  private val intsList: List[Int] = Foldable[List].toList(List(1, 2, 3))
  println(s"$intsList")
  def parseInt(s: String): Option[Int] =

  // TRAVERSE_
    Either.catchOnly[NumberFormatException](s.toInt).toOption

  private val foldableList01: Option[Unit] = Foldable[List].traverse_(List("1", "2", "3"))(parseInt)
  println(s"foldableList01 = $foldableList01")

  private val foldableList02: Option[Unit] = Foldable[List].traverse_(List("a", "b", "c"))(parseInt)
  println(s"foldableList02 = $foldableList02")


  //"COMPOSE"
    // We can compose Foldable[F[_]] and Foldable[G[_]] instances to obtain Foldable[F[G]].
    val FoldableListOption = Foldable[List].compose[Option]
    private val i: Int = FoldableListOption.fold(List(Option(1), Option(2), Option(3), Option(4)))
  println(s"i = ${i}")
    private val str: String = FoldableListOption.fold(List(Option("1"), Option("2"), None, Option("3")))
  println(s"str = ${str}")

  // MORE FOLDABLE METHODS

  private val bool: Boolean = Foldable[List].isEmpty(List(1, 2, 3))
  println(s"bool = ${bool}")
  private val ints01: List[Int] = Foldable[List].dropWhile_(List(1, 2, 3))(_ < 2)
  println(s"ints01 = ${ints01}")
  private val ints02: List[Int] = Foldable[List].takeWhile_(List(1, 2, 3))(_ < 2)
  println(s"ints02 = ${ints02}")


}
