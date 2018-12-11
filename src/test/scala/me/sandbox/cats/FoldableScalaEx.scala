package me.sandbox.cats

import cats.Foldable
import me.sandbox.cats.fixtures.OrderFixture
import org.scalatest.{MustMatchers, WordSpec}

class FoldableScalaEx extends WordSpec with MustMatchers with OrderFixture {
  import cats.implicits._

  "Foldable" must {
    "FOLDMAP " in {
      // FOLDMAP
      //foldMap is similar to fold but maps every A value into B and then combines them using the given
      // Monoid[B] instance.
      Foldable[List].foldMap(List("a", "b", "c"))(_.length) must be(
        3
      )
      Foldable[List].foldMap(List(1, 2, 3))(_.toString) must be(
        "123"
      )
    }
    "FOLDK" in {
      // foldK is similar to fold but combines every value in the foldable using the given
      // MonoidK[G] instance instead of Monoid[G].
      Foldable[List].foldK(List(List(1, 2), List(3, 4, 5))) must be(List(1, 2, 3, 4, 5)
      )
      Foldable[List].foldK(List(None, Option("two"), Option("three"))) must be(Some("two")
      )
    }
    "TOLIST" in {
      // Convert F[A] to List[A].
      Foldable[List].toList(List(1, 2, 3)) must be(
        List(1, 2, 3)
      )
      Foldable[Option].toList(Option(42)) must be(
        List(42)
      )
      Foldable[Option].toList(None) must be(
        List()
      )
    }
    "FILTER_" in {
      // Convert F[A] to List[A] only including the elements that match a predicate.
      Foldable[List].filter_(List(1, 2, 3))(_ < 3) must be(
        List(1, 2)
      )
      Foldable[Option].filter_(Option(42))(_ != 42) must be(
        List()
      )
    }
    "TRAVERSE_" in {
      // Convert F[A] to List[A] only including the elements that match a predicate.
      import cats.implicits._
      def parseInt(s: String): Option[Int] =
        Either.catchOnly[NumberFormatException](s.toInt).toOption
      Foldable[List].traverse_(List("1", "2", "3"))(parseInt) must be(Some(())
      )
      Foldable[List].traverse_(List("a", "b", "c"))(parseInt) must be(None
      )
    }
    "COMPOSE" in {
      // We can compose Foldable[F[_]] and Foldable[G[_]] instances to obtain Foldable[F[G]].
      val FoldableListOption = Foldable[List].compose[Option]
      FoldableListOption.fold(List(Option(1), Option(2), Option(3), Option(4))) must be(10
      )
      FoldableListOption.fold(List(Option("1"), Option("2"), None, Option("3"))) must be("123"
      )
    }
    "MORE FOLDABLE METHODS" in {
      Foldable[List].isEmpty(List(1, 2, 3)) must be(false)
      Foldable[List].dropWhile_(List(1, 2, 3))(_ < 2) must be(List(2, 3))
      Foldable[List].takeWhile_(List(1, 2, 3))(_ < 2) must be(List(1))
    }
  } // end of "Foldable" must {
} // end of the class

