// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

import japgolly.scalajs.react.ScalaFnComponent

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object PlainTextEditor {

  private case class Props(html: String, maxLengthOpt: Option[Int] = None)

  private def render(props: Props) = {
    val text = Serializer.deserialize(props.html).document.text
    val subText = props.maxLengthOpt.map { max => s"${text.substring(0, max)}..." }.getOrElse(text)

    // TODO: @nghuuphuoc Don't need to wrap in `div` when upgrading to React 16
    <.div(subText)
  }

  def apply(html: String, maxLengthOpt: Option[Int] = None): VdomElement = ScalaFnComponent(render)(Props(html, maxLengthOpt))
}
