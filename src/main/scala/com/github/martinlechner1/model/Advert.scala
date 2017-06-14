package com.github.martinlechner1.model

import com.wix.accord.dsl._

case class Advert(id: Int, title: String)

object Advert {
  implicit val carAdvertValidator = validator[Advert] { p =>
    p.title is notEmpty
  }
}