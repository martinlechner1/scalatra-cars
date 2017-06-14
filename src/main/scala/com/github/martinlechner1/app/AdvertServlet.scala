package com.github.martinlechner1.app

import com.github.martinlechner1.database.AdvertDAO
import com.github.martinlechner1.model.Advert
import com.wix.accord.{Failure, Result, Success}
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._
import org.scalatra.swagger._

class AdvertServlet(implicit val swagger: Swagger, implicit val advertDAO: AdvertDAO) extends ScalatraServlet
  with JacksonJsonSupport with SwaggerSupport with FutureSupport {

  protected val applicationDescription = "The car advert api."

  protected implicit def executor = scala.concurrent.ExecutionContext.Implicits.global

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  val getAdverts: SwaggerOperation =
    (apiOperation[List[Advert]]("getAdverts")
      summary "Show all car adverts"
      // notes "Shows all the car adverts. You can search it too."
      // parameter queryParam[Option[String]]("name").description("A name to search for")
      )

  get("/", operation(getAdverts)) {
    advertDAO.getAll()
  }

  val postAdvert: SwaggerOperation =
    (apiOperation[Advert]("postAdvert")
      summary "Post advert"
      // notes "Shows all the car adverts. You can search it too."
      parameter bodyParam[Advert].description("A new CarAdvert")
      )

  post("/", operation(postAdvert)) {
    val carAdvert = parsedBody.extractOpt[Advert]
    carAdvert match {
      case Some(value) =>
        val validation: Result = com.wix.accord.validate(value)
        validation match {
          case Success =>
            // TODO: Error handling
            advertDAO.create(value)
          case Failure(e) => BadRequest(e)
        }
      case None => BadRequest("Malformed JSON")
    }
  }

  val deleteAdvert: SwaggerOperation =
    (apiOperation[Advert]("deleteAdvert")
      summary "Delete advert"
      parameters pathParam[Int]("id").description("Id of the car advert to delete")
      )

  delete("/:id", operation(deleteAdvert)) {
    val id = params("id").toInt
    advertDAO.delete(id)
  }

  options("/*") {
    response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"))
  }
}
