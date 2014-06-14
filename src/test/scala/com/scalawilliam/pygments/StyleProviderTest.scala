package com.scalawilliam.pygments

import org.scalatest.{FunSuite, Matchers}

class StyleProviderTest extends FunSuite with Matchers {
  test("Style provider lists the borland style") {
    StyleProvider.availableStyles should contain ("borland")
  }
  test("Style provider returns borland css") {
    StyleProvider.css("borland") should include ("e3d2d2")
  }
}
