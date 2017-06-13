package com.github.martinlechner1.app

import com.github.martinlechner1.model.CarAdvert
import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._


class AdvertServlet extends ScalatraServlet with CorsSupport with JacksonJsonSupport {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/advert") {
    List(CarAdvert(1, "Demo"))
  }

  options("/*") {
    response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
  }
}
