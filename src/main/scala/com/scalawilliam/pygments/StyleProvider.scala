package com.scalawilliam.pygments

import org.python.util.PythonInterpreter
import scala.collection.JavaConverters._

object StyleProvider {

  val i = new PythonInterpreter()

  i.exec(
    """from pygments.styles import get_all_styles
      |from pygments.formatters import HtmlFormatter
      |def get_css(style):
      |  return HtmlFormatter(style = style).get_style_defs()
    """.stripMargin)

  val availableStyles = i.eval(
    """get_all_styles()"""
  ).asIterable().asScala.map(_.asString).toVector

  def getCss(styleName: String) = {
    i.set("styleName", styleName)
    i.eval(
      """get_css(styleName)"""
    ).asString
  }

  val css = availableStyles.map(getCss).zip(availableStyles).map(_.swap).toMap

}
