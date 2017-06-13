package com.github.martinlechner1.app

import com.github.martinlechner1.model.CarAdvert
import com.wix.accord.{Failure, Result, Success}
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._
import org.scalatra.swagger._

class AdvertServlet(implicit val swagger: Swagger) extends ScalatraServlet
  with JacksonJsonSupport with SwaggerSupport {

  protected val applicationDescription = "The car advert api."

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  val getAdverts =
    (apiOperation[List[CarAdvert]]("getAdverts")
      summary "Show all car adverts"
      // notes "Shows all the car adverts. You can search it too."
      // parameter queryParam[Option[String]]("name").description("A name to search for")
      )

  get("/", operation(getAdverts)) {
    List(CarAdvert(1, "Demo"))
  }

  val postAdvert =
    (apiOperation[CarAdvert]("postAdvert")
      summary "Post advert"
      // notes "Shows all the car adverts. You can search it too."
      parameter bodyParam[CarAdvert].description("A new CarAdvert")
      )

  post("/", operation(postAdvert)) {
    val carAdvert = parsedBody.extractOpt[CarAdvert]
    carAdvert match {
      case Some(value) =>
        val validation: Result = com.wix.accord.validate(value)
        validation match {
          case Success => Ok(value)
          case Failure(e) => BadRequest(e)
        }
      case None => BadRequest("Malformed JSON")
    }
  }

  options("/*") {
    response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"))
  }
}
