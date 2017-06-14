import com.github.martinlechner1.app._
import org.scalatra._
import javax.servlet.ServletContext

import com.github.martinlechner1.database.AdvertDAO
import io.getquill.{MysqlJdbcContext, SnakeCase}

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new AdvertSwagger
  implicit lazy val ctx = new MysqlJdbcContext[SnakeCase]("ctx")
  implicit val advertDao = new AdvertDAO

  override def init(context: ServletContext) {

    /*
     * The following line is a quick fix to make swagger-ui work, see:
     * https://github.com/scalatra/scalatra-website-examples/issues/40 and
     * https://github.com/scalatra/scalatra/issues/676
     */
    context.initParameters("org.scalatra.cors.allowCredentials") = "false"
    context.mount(new AdvertServlet, "/advert", "advert")
    context.mount (new ResourcesApp, "/api-docs")
  }
}
