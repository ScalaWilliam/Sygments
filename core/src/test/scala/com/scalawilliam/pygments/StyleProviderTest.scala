package com.scalawilliam.pygments

import org.scalatest.{FunSuite, Matchers}

class StyleProviderTest extends FunSuite with Matchers {
  val highlighter = new Highlighter()
  test("Style provider lists the borland style") {
    highlighter.styles.keySet should contain ("borland")
  }
  test("Style provider returns borland css") {
    highlighter.styles("borland").css should include ("e3d2d2")
  }
}
