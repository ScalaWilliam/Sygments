package com.scalawilliam.pygments

import com.scalawilliam.pygments.Highlighter.Code
import com.scalawilliam.scalatest.TimeMeasurement
import org.scalatest.{FunSuite, Matchers}

class PerformanceTest extends FunSuite with Matchers with TimeMeasurement with TestCodeData {

  test("Performance with size") {
    val (_, highlighter) = Highlighter.createHighlighter
    val result = for {
      codeSize <- 1 to 7
      newCode = Code((code.value + "\n") * codeSize)
    } yield codeSize -> {
        def execute = highlighter(languageAlias)(newCode)
        run(execute)(100.times)
      }
    info(result.map{case(inputSize, testResult) => s"Code size: $inputSize => $testResult"}.mkString("\n"))
  }
}
