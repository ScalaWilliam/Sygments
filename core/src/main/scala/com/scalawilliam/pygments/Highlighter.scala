package com.scalawilliam.pygments

import org.python.util.PythonInterpreter
import scala.collection.JavaConverters._


class Highlighter() {

  case class Language(id: String, fullName: String) {
    def highlight(code: String): String =
      interpreter.synchronized {
        interpreter.set("language", id)
        interpreter.set("code", code)
        interpreter.eval( """highlight(code, get_lexer_by_name(language), HtmlFormatter())""").asString
      }
  }

  private val interpreter = new PythonInterpreter()

  interpreter.exec(
    """from pygments import highlight
      |from pygments import lexers
      |from pygments.lexers import get_lexer_by_name
      |from pygments.lexers import PythonLexer
      |from pygments.formatters import HtmlFormatter
      |from pygments.styles import get_all_styles
    """.stripMargin)

  case class Style(id: String, css: String)

  val styles: Map[String, Style] = {

    for {
      styleItem <- interpreter.eval(
        """[(style, HtmlFormatter(style = style).get_style_defs()) for style in get_all_styles()]""").asIterable().asScala
      id :: css :: Nil = styleItem.asIterable().asScala.toList.map(_.asString)
    } yield id -> Style(id = id, css = css)
  }.toMap

  val languages: Map[String, Language] = {
    for {
      lexerItem <- interpreter.eval( """lexers.get_all_lexers()""").asIterable().asScala.toList
      itemBits = lexerItem.asIterable.asScala.toList
      fullName <- itemBits.headOption.map(_.asString)
      id <- itemBits.lift(1).flatMap(_.asIterable.asScala.headOption).map(_.asString)
    } yield id -> Language(
      id = id,
      fullName = if (id == "fsharp") "F#" else fullName
    )
  }.toMap

}
