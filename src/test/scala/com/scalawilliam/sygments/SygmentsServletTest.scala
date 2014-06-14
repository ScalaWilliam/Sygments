package com.scalawilliam.sygments

import org.scalatest.{FunSuite, FunSuiteLike, Matchers}
import org.scalatra.test.scalatest.ScalatraSuite

class SygmentsServletTest extends FunSuite with Matchers with ScalatraSuite with FunSuiteLike  {

  addServlet(classOf[SygmentsServlet], "/*")

  test("That Scala code can be highlighted") {
    post("/highlight/scala", "import test._".getBytes) {
      body should include("<highlighted>")
    }
  }

  test("That Scala is one of the returned lexers") {
    get("/lexers") {
      body should include("Scala")
    }
  }


  test("That borland style is listed") {
    get("/styles/internal") {
      body should include ("<style>borland</style>")
    }
  }

  test("That borland style is available") {
    get("/styles/borland.css") {
      body should include ("e3d2d2")
    }
  }

}
