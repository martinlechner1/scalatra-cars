package com.github.martinlechner1.model

sealed trait Fuel {def name: String}

case object Gasoline extends Fuel { val name = "gasoline" }
case object Diesel extends Fuel { val name = "diesel" }