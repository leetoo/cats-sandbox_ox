package me.sandbox.democats.adt.group04applicative // Provides the validated smart constructors.
object ApplicativeNotes2 extends App {
  ApplicativeInAction.mapNInAction()
  import cats._
  import cats.implicits._

  /**
    *  --------------
    */
  private val applicativeBasedEx1: Option[Int] = Applicative[Option].pure(1)

  private val applicativeBasedEx2: List[Int] = Applicative[List].pure(1)
  private val applicativeBasedEx3: List[Option[Int]] = (Applicative[List] compose Applicative[Option]).pure(1)
  println(s"applicativeBasedEx1 = $applicativeBasedEx1")
  println(s"applicativeBasedEx2 = $applicativeBasedEx2")
  println(s"applicativeBasedEx3 = $applicativeBasedEx3")
  /**
    * APPLICATIVE FUNCTORS & MONADS
    */
  println("\n\nAPPLICATIVE FUNCTORS & MONADS\n")
  private val applicativeBasedEx4: Option[Int] = Monad[Option].pure(1)
  private val applicativeBasedEx5: Option[Int] = Applicative[Option].pure(1)
  println(s"applicativeBasedEx4 = $applicativeBasedEx4")
  println(s"applicativeBasedEx5 = $applicativeBasedEx5")

  ApplicativeInAction.mapNInAction()
}
object ApplicativeInAction {
  import cats.instances.option._
  import cats.syntax.apply._

  def mapNInAction() = {
    // can use tupled for up to 22 parameters
    // mapN uses Semigroupal to extract the values
    // from option and then the Functor to apply
    // the values to the function
    val optionCat: Option[Cat] = (
      Option("Garfield"),
      Option(1978),
      Option("Orange & Black")
    ).mapN(Cat.apply)

/*

val guinness = Dog("Guinness", 2004, List("apples"))
 val rover = Dog("Rover", 1997, List("junk food"))

*/
    println(s"optionCat = $optionCat")



 //    val dogs = guinness |+| rover

}

case class Cat(name: String, born: Int, color: String)

case class Dog(name: String, yearOfBirth: Int, favoriteFoods: List[String])

val tupleToDog: (String, Int, List[String]) => Dog = Dog.apply _

val dogToTuple: Dog => (String, Int, List[String]) =
 dog => (dog.name, dog.yearOfBirth, dog.favoriteFoods)

//  implicit val dogMonoid: Monoid[Dog] = (
//    Monoid[String],
//    Monoid[Int],
//    Monoid[List[String]]
//  ).imapN(tupleToDog)(dogToTuple)

}
