package com.github.martinlechner1.app

import org.scalatra.ScalatraServlet
import org.scalatra.swagger.{ApiInfo, NativeSwaggerBase, Swagger}


class ResourcesApp(implicit val swagger: Swagger) extends ScalatraServlet with NativeSwaggerBase

object AdvertApiInfo extends ApiInfo(
  "The Car Advert API",
  "Docs for the Car Advert API",
  "https://github.com/martinlechner1",
  "martin.lechner.ml@gmail.com",
  "MIT",
  "http://opensource.org/licenses/MIT")

class AdvertSwagger extends Swagger(Swagger.SpecVersion, "1.0.0", AdvertApiInfo)