package controllers

import javax.inject.Singleton

import com.scalawilliam.pygments.Highlighter
import play.api.libs.json.Json
import play.api.mvc.{Action, BodyParsers, Controller, RequestHeader}

case class Sample(language: String, credit: String, path: String) {
  def absolutePath(implicit requestHeader: RequestHeader) =
    copy(path = routes.Assets.at(path).absoluteURL())
}

@Singleton
class Main() extends Controller {

  val samples = List(
    Sample(
      language = "scala",
      credit = "https://github.com/ScalaWilliam/xs4s",
      path = "sample.scala"
    )
  )

  implicit val samplesFmt = Json.format[Sample]

  def index = Action { implicit request =>
    Ok(views.html.index.apply(
      samples = {
        for {
          sample <- samples
          language <- highlighter.languages.get(sample.language)
        } yield language -> sample.absolutePath
      }.sortBy(_._1.fullName),
      styles = highlighter.styles.values.toList.sortBy(_.id),
      languages = highlighter.languages.values.toList.sortBy(_.fullName)
    ))
  }

  val highlighter = new Highlighter()

  def highlight(languageId: String) = Action(BodyParsers.parse.text) { request =>
    highlighter.languages.get(languageId) match {
      case Some(language) =>
        Ok(language.highlight(request.body))
      case None =>
        NotFound(s"Could not find language code $languageId")
    }
  }

  def listLexers = Action {
    Ok(Json.toJson(highlighter.languages.mapValues(_.fullName)))
  }

  def listStyles = Action {
    Ok(Json.toJson(highlighter.styles.keys.toList))
  }

  def getStyle(id: String) = Action {
    highlighter.styles.get(id) match {
      case None => NotFound
      case Some(highlighter.Style(_, css)) => Ok(css).withHeaders("Content-Type" -> "text/css")
    }
  }

}