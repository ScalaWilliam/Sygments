package com.scalawilliam.pygments

import com.scalawilliam.pygments.Highlighter.{Code, LanguageAlias}
import org.scalatest.{Matchers, Inspectors, FunSuite}

class InputDataTest extends FunSuite with Matchers with Inspectors {

  val (_, highlighter) = Highlighter.createHighlighter
  val highlightScala = Code andThen highlighter(LanguageAlias("Scala"))

  test("0 input does get highlighted") {
    (highlightScala apply "").isRight shouldBe true
  }

  // Check if we don't get crashed with random characters
  test("Random input data does get highlighted") {
    val r = new scala.util.Random()
    forAll(Iterator.continually(r.nextString(r.nextInt(50))).take(150).toStream) {
      string =>
        val highlighted = highlightScala apply string
        highlighted.isRight shouldBe true
    }
  }

}
