package com.github.martinlechner1.database

import com.github.martinlechner1.model.Advert
import io.getquill.{MysqlJdbcContext, SnakeCase}


class AdvertDAO(implicit val ctx: MysqlJdbcContext[SnakeCase]) {
  import ctx._

  def create(advert: Advert) {
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

  def update(advert: Advert) {
    ctx.run(
      quote {
        query[Advert].filter(_.id == lift(advert.id)).update(lift(advert))
      }
    )
  }

  def getAll(): List[Advert] = {
    ctx.run(
      quote {
        query[Advert]
      }
    )
  }

  def get(id: Int): Option[Advert] = {
    ctx.run(
      quote {
        query[Advert].filter(_.id == lift(id)).take(1)
      }
    ).headOption
  }
}
