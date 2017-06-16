package com.github.martinlechner1.database

import java.time.Instant
import java.util.Date

import com.github.martinlechner1.model._
import io.getquill.{MysqlJdbcContext, SnakeCase}


class AdvertDAO(implicit val ctx: MysqlJdbcContext[SnakeCase]) {
  import ctx._  // scalastyle:ignore

  // Tell quill how to handle Instants
  implicit val encodeInstant = MappedEncoding[Instant, Date](time => Date.from(time))
  implicit val decodeInstant = MappedEncoding[Date, Instant](time => time.toInstant)

  // Tell quill how to handle Fuel
  implicit val encodeFuel = MappedEncoding[Fuel, String](_.name)
  implicit val decodeFuel = MappedEncoding[String, Fuel] {
    case Gasoline.name => Gasoline
    case Diesel.name => Diesel
  }

  def create(advert: Advert): Long = {
    ctx.run(
      quote {
        query[Advert].insert(lift(advert))
      }
    )
  }

  def delete(id: Int) {
    val quotedQuery = quote {
      query[Advert].filter(_.id == lift(id)).delete
    }
    ctx.run(quotedQuery)
  }

  def update(advert: Advert): Long = {
    ctx.run(
      quote {
        query[Advert].filter(_.id == lift(advert.id)).update(lift(advert))
      }
    )
  }

  def getAll(sort: SortBy): List[Advert] = {

    val q = sort match {
      case Id => quote {
        query[Advert].sortBy(_.id)
      }
      case Title => quote {
        query[Advert].sortBy(_.title)
      }
      case Fuel => quote {
        query[Advert].sortBy(_.fuel)
      }
      case FirstRegistration => quote {
        query[Advert].sortBy(_.firstRegistration)
      }
      case Mileage => quote {
        query[Advert].sortBy(_.mileage)
      }
      case New => quote {
        query[Advert].sortBy(_.`new`)
      }
      case Price => quote {
        query[Advert].sortBy(_.price)
      }
    }

    ctx.run(q)
  }

  def get(id: Int): Option[Advert] = {
    ctx.run(
      quote {
        query[Advert].filter(_.id == lift(id)).take(1)
      }
    ).headOption
  }
}
