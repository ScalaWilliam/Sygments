package com.scalawilliam.pygments

import org.scalatest.{Inspectors, FunSuite, Matchers}

class HighlighterTest extends FunSuite with Matchers with Inspectors {

  val highlighter = new Highlighter()

  test("Highlighter executes for Scala") {
    highlighter.languages("scala").highlight("import stuff._") should include ("import")
  }

}