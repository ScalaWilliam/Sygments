package com.scalawilliam.pygments

import com.scalawilliam.scalatest.TimeMeasurement
import org.scalatest.{FunSuite, Matchers}

class PerformanceTest extends FunSuite with Matchers with TimeMeasurement with TestCodeData {

  test("Performance with size") {
    val highlighter = new Highlighter().languages(languageAlias)
    val result = for {
      codeSize <- 1 to 7
      newCode = s"$code\n" * codeSize
    } yield codeSize -> {
        def execute = highlighter.highlight(code = newCode)
        run(execute)(100.times)
      }
    info(result.map{case(inputSize, testResult) => s"Code size: $inputSize => $testResult"}.mkString("\n"))
  }
}
