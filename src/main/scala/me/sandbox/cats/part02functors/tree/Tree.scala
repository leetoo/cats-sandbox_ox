package me.sandbox.cats.part02functors.tree

import cats.Functor
import me.sandbox.cats.part02functors.FunctorNotesSwC.sumOne

sealed trait Tree[+A]
final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
final case class Leaf[A](value: A) extends Tree[A]
object Tree {
  // TODO 04: implement Functor[Tree]
  /*
  // implicit val treeFunctor = ???
  // Functor for Trees.
  // Let's create a Functor for a binary tree.
  // sealed trait Tree[+A]
  //  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  //  final case class Leaf[A](value: A) extends Tree[A]
  */
  implicit val TreeFunctor: Functor[Tree] = new Functor[Tree] {
     override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
      case Branch(left, right) => Branch(map(left)(f), map(right)(f))
      case Leaf(a)             => Leaf(f(a))
    }

  }
}
object Demo extends App {
  val tree: Tree[Int] = Branch(left = Leaf(10),
    right = Branch(left = Leaf(3), right = Leaf(5)))
  val summedTree = sumOne(tree)
  println(s"Given sumOne[F[_]: Functor](fa: F[Int]): F[Int] " +
    s"= fa.map(x => x + 1)\t->\tsumOne(Branch(Leaf(10),Branch(Leaf(3),Leaf(5)))) = $summedTree")

  val treeHelloWorld = Branch(Leaf("hello"), Leaf("WoRld"))
  Functor[Tree].map(treeHelloWorld)(_.toUpperCase)
  println(s"treeHelloWorld = $treeHelloWorld")

  val tree2 = Branch(Leaf(1), Branch(Leaf(2), Branch(Leaf(3), Leaf(4))))
  Functor[Tree].map(tree2)(_ + 1)
  println(s"tree2 = $tree2")
}


