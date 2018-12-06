package me.sandbox.cats.fixtures

import me.sandbox.cats.monoids.usecases.Gato

trait GatoFixture {

  val michin = Gato("michin", 3, "black")

  val cat1 = Gato("Garfield", 38, "orange and black")

  val cat2 = Gato("Heathcliff", 33, "orange and black")

}
