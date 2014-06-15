package com.scalawilliam.sygments

import com.scalawilliam.pygments.Highlighter._
import com.scalawilliam.pygments.{Highlighter, StyleProvider}
import org.scalatra.ScalatraServlet
import scala.xml.PCData
class SygmentsServlet(lexers: Vector[Lexer], highlighter: Highlighter) extends ScalatraServlet {
  def this() = this(Highlighter.createHighlighter._1, Highlighter.createHighlighter._2)
  post("/highlight/:languageAlias") {
    val code = Code(request.body)
    val languageAlias = LanguageAlias(params("languageAlias"))
    <highlight-response>
      <input>{PCData(code.value)}</input>
      <language-alias>{PCData(languageAlias.value)}</language-alias>
      {
        highlighter(languageAlias)(code) match {
          case Left(Failure(reason)) => <failure>{reason}</failure>
          case Right(HighlightedCode(value)) => <highlighted>{PCData(value)}</highlighted>
        }
      }
    </highlight-response>
  }

  get("/lexers") {
    <lexers>
      {for {Lexer(id, fullName, aliases) <- lexers} yield
      <lexer id={id} name={fullName}>
        {for {alias <- aliases} yield <alias>{alias}</alias>}
      </lexer>}
    </lexers>
  }

  get("/styles/internal") {
    <styles>{
      for {style <- StyleProvider.availableStyles}
      yield <style>{style}</style>
      }</styles>
  }

  get("/styles/:style.css") {
    contentType = "text/css"
    StyleProvider.css get params("style") match {
      case Some(data) => data
      case None => halt(status = 404, body = "Stylesheet not found")
    }
  }

}
