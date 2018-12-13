package me.sandbox.democats.fixtures

import me.sandbox.democats.group01monoids_n_semigroup.usecases.Order

trait OrderFixture {

  val orderCosting70 = Order(totalCost = 70, quantity = 15)

  val orderCosting30 = Order(totalCost = 30, quantity = 15)

}
