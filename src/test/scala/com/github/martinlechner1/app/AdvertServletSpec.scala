package com.github.martinlechner1.app

import org.scalatra.test.specs2._
import org.specs2.matcher.JsonMatchers

class AdvertServletSpec extends MutableScalatraSpec with JsonMatchers {

  implicit val formats = org.json4s.DefaultFormats
  implicit val swagger = new AdvertSwagger
  val servletPath = "/advert"

  addServlet(new AdvertServlet, servletPath + "/*")

  "GET / on AdvertServlet" should {
    "return a list of CarAdverts" in {
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
    "fail with 500 on missing title" in {
      val requestBody = """{"id": 1}"""
      post(servletPath, headers = jsonHeaders, body = requestBody) {
        status must_== 500
      }
    }
    "fail with 400 on empty title" in {
      val requestBody = """{"id": 1, "title": ""}"""
      post(servletPath, headers = jsonHeaders, body = requestBody) {
        status must_== 400
      }
    }
  }
}
