package com.scalawilliam.pygments

trait TestCodeData {

  val languageAlias = "Scala"

  val code = """package com.scalawilliam
                    |
                    |import com.scalawilliam.pygments.Highlighter
                    |import com.scalawilliam.pygments.Highlighter.{Code, LanguageAlias}
                    |import org.scalatest.{Matchers, FunSuite}
                    |
                    |class ProcessingPerformanceTest extends FunSuite with Matchers {
                    |  test("Runs") {
                    |    val highlighter = Highlighter.createHighlighter._2
                    |    highlighter(LanguageAlias("Scala"))(Code("import test"))
                    |  }
                    |}""".stripMargin

}

object TestCodeData extends TestCodeData