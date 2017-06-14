package com.github.martinlechner1.app

import com.github.martinlechner1.database.AdvertDAO
import com.github.martinlechner1.model.Advert
import org.scalatra.test.specs2._
import org.specs2.matcher.JsonMatchers
import org.specs2.mock.Mockito

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class AdvertServletSpec extends MutableScalatraSpec with JsonMatchers with Mockito {

  implicit val formats = org.json4s.DefaultFormats
  implicit val swagger = new AdvertSwagger
  implicit val advertDAO = mock[AdvertDAO]
  val servletPath = "/advert"

  addServlet(new AdvertServlet, servletPath + "/*")

  "GET / on AdvertServlet" should {
    "return a list of CarAdverts" in {
      advertDAO.getAll().returns(Future.successful(List(Advert(1, "Demo"))))
      get(servletPath) {
        response.body must /#(0) / ("id" -> 1) / ("title" -> "Demo")
      }
    }
  }

  "POST / on AdvertServlet" should {
    val jsonHeaders = Map("Accept" -> "application/json", "Content-Type" -> "application/json")

    "accept correct data" in {
      val requestBody = """{"id": 1, "title": "Audi A4"}"""
      post(servletPath, headers = jsonHeaders, body = requestBody) {
        status must_== 200
      }
    }
    "fail with 400 on missing title" in {
      val requestBody = """{"id": 1}"""
      post(servletPath, headers = jsonHeaders, body = requestBody) {
        status must_== 400
      }
    }
    "fail with 400 on empty title" in {
      val requestBody = """{"id": 1, "title": ""}"""
      post(servletPath, headers = jsonHeaders, body = requestBody) {
        status must_== 400
      }
    }
  }
  /*
  FIXME: Asynchronity breaks the tests -.-

  "DELETE / on AdvertServlet" should {
    "return true on successful operation" in {
      advertDAO.delete(0).returns(Future.successful(true))
      delete(servletPath + "/1") {
        status must_== 200
        response.body must_== "true"
      }
    }
  }
  */
}
