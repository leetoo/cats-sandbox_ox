package me.sandbox.democats.group03apply

import cats.Apply

object ApplyLab extends App {
  import cats.implicits._

  val intToString: Int ⇒ String = _.toString
  val double: Int ⇒ Int = _ * 2
  val addTwo: Int ⇒ Int = _ + 2
  def addTwo2: Int ⇒ Int = _ + 2
  private val ex1: Option[String] = Apply[Option].map(Some(1))(intToString)
  val ex11 = ex1 + "a"
  println(s"ex1 = $ex1 and ex11 =  $ex11")
  private val ex2: Option[Int] = Apply[Option].map(Some(1))(double)
  println(s"ex2 = $ex2")
  val ex22 = Option(1)
  val ex23 = ex22 match {
    case Some(x) => Option(x * 2)
    case _ => throw new Exception("something wrong ")
  }


  /**
    * MAP
    */
  println(s"ex23 = $ex23")
  private val ex3: Option[Int] = Apply[Option].map(None)(addTwo)
  println(s"ex3 = $ex3")
  /**
    * COMPOSE
    */
  val listOpt = Apply[List] compose Apply[Option]
  val plusOne = (x: Int) ⇒ x + 1
  private val list: List[Option[Int]] = listOpt.ap(List(Some(plusOne)))(List(Some(1), None, Some(3)))
  println(s"list = $list")
  /**
    * AP
    */
  private val ex6: Option[String] = Apply[Option].ap(Some(intToString))(Some(1))
  println(s"ex6 = $ex6 ")
  private val ex7: Option[Int] = Apply[Option].ap(Some(double))(Some(1))
  val ex77 = ex7 + "a"
  println(s"ex7 = $ex7 ex77 = $ex77")
  private val ex8: Option[Int] = Apply[Option].ap(Some(double))(None)
  private val ex9: Option[Nothing] = Apply[Option].ap(None)(Some(1))
  private val ex10: Option[Nothing] = Apply[Option].ap(None)(None)
  println(s"ex8,ex9,ex10 = ${ex8},${ex9},${ex10}")
  /**
    * AP2, AP3, ETC
    */
  val addArity2 = (a: Int, b: Int) ⇒ a + b
  private val expression11: Option[Int] = Apply[Option].ap2(Some(addArity2))(Some(1), Some(2))
  println(s"ex11 = $expression11")
  private val ex12: Option[Int] = Apply[Option].ap2(Some(addArity2))(Some(1), None)
  println(s"ex12 = $ex12")
  val addArity3 = (a: Int, b: Int, c: Int) ⇒ a + b + c
  private val ex14: Option[Int] = Apply[Option].ap3(Some(addArity3))(Some(1), Some(2), Some(3))
  println(s"ex14 = $ex14")
  /**
    * MAP2, MAP3, ETC
    */
  println(s"\n\nMAP2, MAP3, ETC\n")
  private val map2BasedEx: Option[Int] = Apply[Option].map2(Some(1), Some(2))(addArity2)
  println(s"map2BasedEx = $map2BasedEx")
  private val map2BasedEx2: Option[Int] = Apply[Option].map3(Some(1), Some(2), Some(3))(addArity3)
  println(s"map2BasedEx2 = $map2BasedEx2")
  /**
    * TUPLE2, TUPLE3, ETC
    */
  println(s"\n\n TUPLE2, TUPLE3, ETC")
  private val tuple2BasedEx: Option[(Int, Int)] = Apply[Option].tuple2(Some(1), Some(2))
  println(s"tuple2BasedEx = $tuple2BasedEx")

  private val tuple2BasedEx2: Option[(Int, Int, Int)] = Apply[Option].tuple3(Some(1), Some(2), Some(3))
  println(s"tuple2BasedEx2 = $tuple2BasedEx2")
  /**
    * APPLY BUILDER SYNTAX
    */
  println(s"\n\n APPLY BUILDER SYNTAX\n")
/*

  import cats.implicits._
   val option2 = Option(1) |@| Option(2)
  val option3 = option2 |@| Option.empty[Int]

  private val apBuildSyntax1: Option[Int] = option2 map addArity2
  private val apBuildSyntax2: Option[Int] = option3 map addArity3

  private val apBuildSyntax3: Option[Int] = option2 apWith Some(addArity2)
  private val apBuildSyntax4: Option[Int] = option3 apWith Some(addArity3)

  private val apBuildSyntax5: Option[(Int, Int)] = option2.tupled
  private val apBuildSyntax6: Option[(Int, Int, Int)] = option3.tupled
  println(s"apBuildSyntax1 = $apBuildSyntax1")
  println(s"apBuildSyntax2 = $apBuildSyntax2")
  println(s"apBuildSyntax3 = $apBuildSyntax3")
  println(s"apBuildSyntax4 = $apBuildSyntax4")
  println(s"apBuildSyntax5 = $apBuildSyntax5")
  println(s"apBuildSyntax6 = $apBuildSyntax6")

  */

}

