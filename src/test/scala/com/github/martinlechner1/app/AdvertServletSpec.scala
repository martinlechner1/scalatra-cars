package com.github.martinlechner1.app

import org.scalatra.test.specs2._
import org.specs2.matcher.JsonMatchers

class AdvertServletSpec extends MutableScalatraSpec with JsonMatchers {

  implicit val swagger = new AdvertSwagger
  addServlet(new AdvertServlet, "/advert/*")

  "GET / on AdvertServlet" should {
    "return a list of CarAdverts" in {
      get("/advert") {
        response.body must /#(0)/("id" -> 1)/("title" -> "Demo")
      }
    }
  }
}
