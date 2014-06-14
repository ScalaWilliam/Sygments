package com.scalawilliam.pygments

import org.python.util.PythonInterpreter
import scala.collection.JavaConverters._
import scala.util.Try

object Highlighter {
  trait StringVal {
    self: Product =>
    def value: String
    override def toString = value
  }

  case class Code(value: String) extends StringVal

  case class LanguageAlias(value: String) extends AnyVal

  case class HighlightedCode(value: String) extends AnyVal

  case class Failure(value: String) extends AnyVal

  case class Lexer(id: String, fullName: String, aliases: Vector[String])

  type Highlighter = LanguageAlias => Code => Either[Failure, HighlightedCode]

  def createHighlighter: (Vector[Lexer], Highlighter) = {

    val interpreter = new PythonInterpreter()

    interpreter.exec(
      """from pygments import highlight
        |from pygments import lexers
        |from pygments.lexers import get_lexer_by_name
        |from pygments.lexers import PythonLexer
        |from pygments.formatters import HtmlFormatter
      """.stripMargin)

    val lexers = interpreter.eval(
      """lexers.get_all_lexers()"""
    ).asIterable().asScala.map(_.asIterable.asScala.toVector).flatMap {
      obj =>
        val fullName = obj(0).asString()
        val fsharp = if ( fullName == "FSharp" ) Vector("F#") else Vector.empty
        val aliases = obj(1).asIterable.asScala.map(_.asString).toVector ++ fsharp
        aliases.headOption.toSeq.map(Lexer(_, fullName, aliases))
    }.toVector

    val nameToLexer = (for {
      lexer @ Lexer(_, fullName, aliases) <- lexers
      item <- Vector(fullName) ++ aliases
    } yield LanguageAlias(item) -> lexer).toMap

    val aliasToLexer =
      nameToLexer.mapValues(Right.apply).withDefault (language => Left(Failure(s"Lexer for $language not found")))

    val highlighter: Highlighter =
      languageAlias => code => for {
        lexer <- aliasToLexer(languageAlias).right
        result <- interpreter.synchronized {
          interpreter.set("code", code.value)
          interpreter.set("language", lexer.id)
          Try {
            val result = interpreter.eval(
              """highlight(code, get_lexer_by_name(language), HtmlFormatter())"""
            ).asString
            Right(HighlightedCode(result))
          } match {
            case scala.util.Success(data) => data
            case scala.util.Failure(e) => Left(Failure(s"$e"))
          }
        }.right
      } yield result

    lexers -> highlighter

  }
}