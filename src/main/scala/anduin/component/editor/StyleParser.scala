// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

import org.scalajs.dom.Element

import anduin.scalajs.slate.Slate.Data

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] object StyleParser {

  private val TextAlignProperty = "text-align"
  private val styleKey = "style"

  private def getStyleDict(style: String) = {
    for {
      entry <- style.split(";").toList
      (rawKey, rawValue) = entry.span(_ != ':')
    } yield rawKey.trim -> rawValue.drop(1).trim
  }

  /**
    * Parse the text alignment from given element
    */
  def textAlign(ele: Element): String = {
    Option(ele.getAttribute(styleKey))
      .flatMap { style =>
        getStyleDict(style).toMap.get(TextAlignProperty)
      }
      .getOrElse("")
  }

  def getStyleTagMod(data: Data): TagMod = {
    ^.style := js.Dictionary(getStyleDict(DataUtil.value(data, styleKey)): _*)
  }

  def parseStyle(ele: Element): js.UndefOr[String] = {
    Option(ele.getAttribute(styleKey): js.UndefOr[String]).getOrElse(js.undefined)
  }
}
