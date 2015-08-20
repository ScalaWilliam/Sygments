package controllers

import javax.inject.Singleton

import com.scalawilliam.pygments.Highlighter
import play.api.libs.json.Json
import play.api.mvc.{BodyParsers, Action, Controller}

@Singleton
class Main() extends Controller {

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