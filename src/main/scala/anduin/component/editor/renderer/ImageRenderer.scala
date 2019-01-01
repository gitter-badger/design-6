// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.renderer

import japgolly.scalajs.react.raw

import anduin.component.editor.DataUtil

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.slate.Slate._
// scalastyle:on underscore.import

private[editor] object ImageRenderer {

  def apply(data: Data): raw.React.Element = {
    val source = DataUtil.value(data, "source")
    val width = DataUtil.value(data, "width")
    val height = DataUtil.value(data, "height")
    <.img(
      ^.src := source,
      TagMod.when(width.nonEmpty)(^.width := parseSize(width)),
      TagMod.when(height.nonEmpty)(^.height := parseSize(height))
    ).rawElement
  }

  private def parseSize(size: String) = {
    if (size.endsWith("px")) {
      size
    } else {
      s"${size}px"
    }
  }
}
