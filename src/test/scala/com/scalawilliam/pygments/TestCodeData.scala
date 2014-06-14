package com.scalawilliam.pygments

import com.scalawilliam.pygments.Highlighter.{Code, LanguageAlias}

trait TestCodeData {

  val languageAlias = LanguageAlias("Scala")

  val code = Code("""package com.scalawilliam
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
                    |}""".stripMargin)

}

object TestCodeData extends TestCodeData