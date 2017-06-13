import com.github.martinlechner1.app._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new AdvertSwagger

  override def init(context: ServletContext) {
    context.mount(new AdvertServlet, "/advert", "advert")
    context.mount (new ResourcesApp, "/api-docs")
  }
}
