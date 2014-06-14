package com.scalawilliam.pygments

import com.scalawilliam.pygments.Highlighter._
import org.scalatest.{FunSuite, Matchers}

class HighlighterTest extends FunSuite with Matchers {

  val (lexers, highlighter) = Highlighter.createHighlighter

  test("Highlighter executes for Scala") {
    val result = highlighter apply LanguageAlias("Scala") apply Code("import stuff._")
    withClue(result) {
      result.isRight shouldBe true
      result.right.get.value should include ("import")
    }
  }

  test("Highlighter fails an invalid name") {
    val result = highlighter apply LanguageAlias("Fake fake") apply Code("import stuff._")
    withClue(result) {
      result.isLeft shouldBe true
      result.left.get.value should include ("not found")
    }
  }

}