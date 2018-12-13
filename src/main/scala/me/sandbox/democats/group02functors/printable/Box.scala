package me.sandbox.democats.group02functors.printable

final case class Box[A](value: A){
  implicit def printableBox[B](implicit printer: Printable[B]) : Printable[Box[B]] = printer.contramap[Box[B]](_.value)
}

// Printable with contramap
trait Printable[A] {
  def format(a: A) : String

  // contramap: for F[A] and a mapping from B => A, produce a F[B]
  def contramap[B](func: B => A): Printable[B] = {
    val _this = this
    new Printable[B] {
      def format(b: B) = _this.format(func(b))
    }
  }

}

object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    def format()(implicit printer: Printable[A]): String = printer.format(value)
    def contramap[B](func: B => A)(implicit printer: Printable[A]) : Printable[B] = printer.contramap(func)
  }
}

object PrintableInstances {
  implicit object PrintableString extends Printable[String] {
    def format(s: String) : String = "\"" + s + "\""
  }

  implicit val printableBool = new Printable[Boolean] {
    def format(value: Boolean) = if (value) "yes" else "no"
  }



  implicit def boxStringContramap[A]
  (implicit printable: Printable[A]): Printable[Box[A]] =
    printable.contramap[Box[A]](_.value)


}