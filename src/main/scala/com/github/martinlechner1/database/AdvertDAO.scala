package com.github.martinlechner1.database

import com.github.martinlechner1.model.Advert
import io.getquill.{MysqlAsyncContext, SnakeCase}

import scala.concurrent.{ExecutionContext, Future}

class AdvertDAO(implicit val ctx: MysqlAsyncContext[SnakeCase]) {
  import ctx._

  def create(advert: Advert)(implicit ectx: ExecutionContext) {
    ctx.run(
      quote {
        query[Advert].insert(lift(advert))
      }
    )
  }

  def delete(id: Int)(implicit ectx: ExecutionContext): Future[Boolean] = {
    val quotedQuery = quote {
      query[Advert].filter(_.id == lift(id)).delete
    }
    ctx.run(quotedQuery).map {
      case 0 => false
      case 1 => true
      case _ => false
    }
  }

  def getAll()(implicit ectx: ExecutionContext): Future[List[Advert]] = {
    ctx.run(
      quote {
        query[Advert]
      }
    )
  }
}
