package com.github.martinlechner1.app

import com.github.martinlechner1.model.{Diesel, Fuel, Gasoline}
import org.json4s.CustomSerializer
import org.json4s.JsonAST.JString

/**
  * The API should support the custom Fuel Type
  */
case object FuelSerializer extends CustomSerializer[Fuel](_ => ( {
  case JString(s) => s match {
    case Gasoline.name => Gasoline
    case Diesel.name => Diesel
  }
}, {
  case fuel: Fuel => JString(fuel.name)
}))
