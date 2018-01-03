// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.slate

import org.scalajs.dom.Element

private[slate] object StyleParser {

  private final val TextAlignProperty = "text-align:"

  /**
    * Parse the text alignment from given element
    */
  def textAlign(ele: Element): String = {
    Option(ele.getAttribute("style")).flatMap(
      _.split(";")
        .find(_.trim.startsWith(TextAlignProperty))
        .map(_.stripPrefix(TextAlignProperty).trim)
    ).getOrElse("")
  }
}
