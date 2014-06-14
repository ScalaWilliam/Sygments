import javax.servlet.ServletContext

import com.scalawilliam.sygments.SygmentsServlet
import org.scalatra._
import scala.util.Properties._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {

  s"""
     |Welcome to Sygments, $userName!
     |You are running Sygments on $osName, Scala $versionNumberString, JDK $javaVersion.
     |
     |*********************************
     |*** https://scala.contractors ***
     |***    www.scalawilliam.com   ***
     |*********************************
     |
     |""".stripMargin split "\n" map (" " + _) foreach println

    context.mount(classOf[SygmentsServlet], "/*")

  }

}


