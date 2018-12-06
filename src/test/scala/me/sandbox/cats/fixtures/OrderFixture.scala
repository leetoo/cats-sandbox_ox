package me.sandbox.cats.fixtures

import me.sandbox.cats.monoids.usecases.Order

trait OrderFixture {

  val orderCosting70 = Order(totalCost = 70, quantity = 15)

  val orderCosting30 = Order(totalCost = 30, quantity = 15)

}
