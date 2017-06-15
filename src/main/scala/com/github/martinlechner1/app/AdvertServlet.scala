package com.github.martinlechner1.app

import com.github.martinlechner1.database._
import com.github.martinlechner1.model.Advert
import com.wix.accord.{Failure, Success, validate}
import org.json4s.{DefaultFormats, Formats, JValue}
import org.scalatra._
import org.scalatra.json._
import org.scalatra.swagger._

class AdvertServlet(implicit val swagger: Swagger, implicit val advertDAO: AdvertDAO) extends ScalatraServlet
  with JacksonJsonSupport with ApiDescription {

  private val ID = "id"
  private val PATH = "/"
  private val PATH_WITH_ID = PATH + ":" + ID

  protected val applicationDescription = "The car advert api."

  protected implicit lazy val jsonFormats: Formats = DefaultFormats + InstantSerializer + FuelSerializer

  before() {
    contentType = formats("json")
  }

  private def handleAdvertBody(body: JValue, action: (Advert => Any)) = {
    body.extractOpt[Advert]
      .map(value => {
        validate(value) match {
          case Success =>
            val result = action(value)
            result match {
              case 1 => Ok()
              case _ => InternalServerError("Database operation error")
            }
          case Failure(e) => BadRequest(e)
        }
      })
      .getOrElse(BadRequest("Malformed JSON"))
  }

  get(PATH, operation(getAllAdverts)) {
    val sort = params.get("sort")
      .map({
        case Id.name => Id
        case Title.name => Title
        case New.name => New
        case Mileage.name => Mileage
        case Fuel.name => Fuel
        case FirstRegistration.name => FirstRegistration
        case Price.name => Price
        case _ => Id
      })
      .getOrElse(Id)
    advertDAO.getAll(sort)
  }

  get(PATH_WITH_ID, operation(getAdvert)) {
    val id = params(ID).toInt

    advertDAO
      .get(id)
      .map(value => Ok(value))
      .getOrElse(NotFound("No car advert for given " + ID + ": " + id))
  }

  post(PATH, operation(createAdvert)) {
    handleAdvertBody(parsedBody, advertDAO.create)
  }

  delete(PATH_WITH_ID, operation(deleteAdvert)) {
    val id = params(ID).toInt
    advertDAO.delete(id)
  }

  put(PATH_WITH_ID, operation(updateAdvert)) {
    val id = params(ID).toInt

    val updateFn = (value: Advert) =>
      if (value.id == id) {
        advertDAO.update(value)
      } else {
        BadRequest("Ids do not match")
      }

    handleAdvertBody(parsedBody, updateFn)
  }

  options("/*") {
    response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"))
  }
}
