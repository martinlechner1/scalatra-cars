package com.github.martinlechner1.model

import com.wix.accord.dsl._
import java.time.Instant

case class Advert(id: Int, title: String, fuel: Fuel, price: Int, `new`: Boolean, mileage: Option[Int], firstRegistration: Option[Instant])

object Advert {

  implicit val carAdvertValidator = validator[Advert] { p =>
    p.title is notEmpty
    p.price should be >= 0
    (
      (p.`new` is false)
        and (p.mileage is notEmpty)
        and (p.mileage.each should be > 0)
        and (p.firstRegistration is notEmpty)
        and (p.firstRegistration.map(_.isBefore(Instant.now())).each is true)
      ) or (
      (p.`new` is true)
        and (p.mileage is empty)
        and (p.firstRegistration is empty)
    )
  }
}
