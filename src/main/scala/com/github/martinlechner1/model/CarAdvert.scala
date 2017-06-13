package com.github.martinlechner1.model

import com.wix.accord.dsl._

case class CarAdvert(id: Int, title: String)

object CarAdvert {
  implicit val carAdvertValidator = validator[CarAdvert] { p =>
    p.title is notEmpty
  }
}