package me.sandbox.democats.group01monoids_n_semigroup.usecases.calculator

import me.sandbox.democats.group01monoids_n_semigroup.usecases.Order

object Calculator {

  import cats.Monoid

  /*
   * TODO 03: implement the generic 'add' on any monoid
   * Note:
   * - accept a List of T
   * - accept an implicit monoid of T
   * - fold over the list
   * - combine the different T using |+|
   */
  implicit val monoid: Monoid[Order] = new Monoid[Order] {
    def combine(o1: Order, o2: Order) =
      Order(
        o1.totalCost + o2.totalCost,
        o1.quantity + o2.quantity
      )

    def empty = Order(0, 0)
  }
  def add[T](items: List[T])(implicit m: Monoid[T]): T = {
    //???

    import cats.Monoid
    import cats.syntax.semigroup._ // for |+|

    // items.foldLeft(0)(_ + _) // for |+|
    items.foldLeft(Monoid[T].empty)(_ |+| _)
  }
}
