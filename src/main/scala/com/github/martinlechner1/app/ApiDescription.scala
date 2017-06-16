package com.github.martinlechner1.app

import com.github.martinlechner1.model.Advert
import org.scalatra.swagger.{SwaggerOperation, SwaggerSupport}

trait ApiDescription extends SwaggerSupport {

  val pathParamId = "id"

  val getAllAdverts: SwaggerOperation =
    (apiOperation[List[Advert]]("getAllAdverts")
      summary "Show all car adverts."
      parameter queryParam[Option[String]]("sort").description("(Optional) Specify the field you want to sort the result with.")
      )

  val getAdvert: SwaggerOperation =
    (apiOperation[Advert]("getAdvert")
      summary "Show car advert with given id."
      parameter pathParam[Int](pathParamId).description("Id of the car advert to get.")
      )

  val createAdvert: SwaggerOperation =
    (apiOperation[Advert]("createAdvert")
      summary "Create new car advert."
      parameter bodyParam[Advert].description("A new car advert.")
      )

  val updateAdvert: SwaggerOperation =
    (apiOperation[Advert]("updateAdvert")
      summary "Update an existing car advert."
      parameter pathParam[Int](pathParamId).description("Id of the car advert to update.")
      parameter bodyParam[Advert].description("The updated car advert."))

  val deleteAdvert: SwaggerOperation =
    (apiOperation[Advert]("deleteAdvert")
      summary "Delete an existing car advert."
      parameter pathParam[Int](pathParamId).description("Id of the car advert to delete.")
      )
}
