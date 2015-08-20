package com.scalawilliam.pygments

import org.scalatest.{Matchers, Inspectors, FunSuite}

class InputDataTest extends FunSuite with Matchers with Inspectors {

  val highlighter = new Highlighter()

  val scalaLanguage = highlighter.languages("scala")

  test("0 input does get highlighted") {
    scalaLanguage.highlight(code = "")
  }

  // Check if we don't get crashed with random characters
  test("Random input data does get highlighted") {
    val r = new scala.util.Random()
    forAll(Iterator.continually(r.nextString(r.nextInt(50))).take(150).toStream) {
      string => scalaLanguage.highlight(code = string)
    }
  }

}
