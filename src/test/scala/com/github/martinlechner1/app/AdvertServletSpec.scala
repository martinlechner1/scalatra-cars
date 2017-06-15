package com.github.martinlechner1.app

import com.github.martinlechner1.database.{AdvertDAO, Id}
import com.github.martinlechner1.model.{Advert, Diesel}
import org.mockito.Mockito.reset
import org.scalatra.test.specs2._
import org.specs2.matcher.JsonMatchers
import org.specs2.mock.Mockito
import org.specs2.specification.process.RandomSequentialExecution
import org.specs2.specification.{BeforeAll, BeforeEach}


class AdvertServletSpec extends MutableScalatraSpec with JsonMatchers with Mockito with BeforeAll with BeforeEach with RandomSequentialExecution {

  implicit val formats = org.json4s.DefaultFormats
  implicit val swagger = new AdvertSwagger
  implicit val advertDAO = mock[AdvertDAO]
  val servletPath = "/advert"
  val servletPathId = (id: Int) => servletPath + "/" + id

  addServlet(new AdvertServlet, servletPath + "/*")

  def before = {
    reset(advertDAO)
  }


  private val NEW_AUDI = Advert(1, "Audi A4", Diesel, 10, true, Option.empty, Option.empty)

  "GET / on AdvertServlet" should {
    "return a list of CarAdverts" in {
      advertDAO.getAll(Id).returns(List(NEW_AUDI))
      get(servletPath) {
        response.body must /#(0) / ("id" -> 1)
        response.body must /#(0) / ("title" -> "Audi A4")
      }
    }
  }

  "GET /:id on AdvertServlet" should {
    "return a CarAdvert" in {
      advertDAO.get(===(1)).returns(Option(NEW_AUDI))
      get(servletPathId(1)) {
        response.body must /("id" -> 1)
        response.body must /("title" -> "Audi A4")
      }
    }
    "return 404 if no matching car advert is found" in {
      advertDAO.get(===(1)).returns(Option.empty[Advert])
      get(servletPathId(1)) {
        status must_== 404
      }
    }
  }

  "POST / on AdvertServlet" should {
    val jsonHeaders = Map("Accept" -> "application/json", "Content-Type" -> "application/json")

    "accept correct data" in {
      val requestBody = """{"id": 1, "title": "Audi A4", "fuel": "diesel", "price": 10, "new": true}"""
      advertDAO.create(any[Advert]).returns(1)
      post(servletPath, headers = jsonHeaders, body = requestBody) {
        status must_== 200
        there was one(advertDAO).create(NEW_AUDI)
      }
    }
    "fail with 400 on missing title" in {
      val requestBody = """{"id": 1}"""
      post(servletPath, headers = jsonHeaders, body = requestBody) {
        status must_== 400
        there was no(advertDAO).create(any[Advert])

      }
    }
    "fail with 400 on empty title" in {
      val requestBody = """{"id": 1, "title": ""}"""
      post(servletPath, headers = jsonHeaders, body = requestBody) {
        status must_== 400
        there was no(advertDAO).create(any[Advert])
      }
    }
  }

  "PUT /:id on AdvertServlet" should {
    val jsonHeaders = Map("Accept" -> "application/json", "Content-Type" -> "application/json")

    "accept correct data" in {
      val requestBody = """{"id": 1, "title": "Audi A4", "fuel": "diesel", "price": 10, "new": true}"""
      advertDAO.update(any[Advert]).returns(1)
      put(servletPathId(1), headers = jsonHeaders, body = requestBody) {
        status must_== 200
        there was one(advertDAO).update(NEW_AUDI)
      }
    }
    "fail with 400 on not matching id" in {
      val requestBody = """{"id": 1, "title": "Audi A4"}"""
      put(servletPathId(2), headers = jsonHeaders, body = requestBody) {
        status must_== 400
        there was no(advertDAO).update(any[Advert])
      }
    }
    "fail with 400 on missing title" in {
      val requestBody = """{"id": 1}"""
      put(servletPathId(1), headers = jsonHeaders, body = requestBody) {
        status must_== 400
        there was no(advertDAO).update(any[Advert])

      }
    }
    "fail with 400 on empty title" in {
      val requestBody = """{"id": 1, "title": ""}"""
      put(servletPathId(1), headers = jsonHeaders, body = requestBody) {
        status must_== 400
        there was no(advertDAO).update(any[Advert])
      }
    }
  }

  "DELETE / on AdvertServlet" should {
    "return call delete on dao" in {
      val id = 1
      delete(servletPathId(id)) {
        status must_== 200
        there was one(advertDAO).delete(id)
      }
    }
  }
}
