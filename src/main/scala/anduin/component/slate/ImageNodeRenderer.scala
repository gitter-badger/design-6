// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.slate

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.slate.Slate._
// scalastyle:on underscore.import

private[slate] object ImageNodeRenderer {

  def apply(data: Data): VdomElement = {
    val source = DataUtil.value(data, "source")
    val width = DataUtil.value(data, "width")
    val height = DataUtil.value(data, "height")
    <.img(
      ^.src := source,
      TagMod.when(width.nonEmpty)(^.width := parseSize(width)),
      TagMod.when(height.nonEmpty)(^.height := parseSize(height))
    )
  }

  private def parseSize(size: String) = {
    if (size.endsWith("px")) {
      size
    } else {
      s"${size}px"
    }
  }
}
