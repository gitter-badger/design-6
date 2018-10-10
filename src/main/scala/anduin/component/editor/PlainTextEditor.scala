// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import anduin.component.truncate.TruncateMarkup

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PlainTextEditor(html: String) {
  def apply(): VdomElement = PlainTextEditor.component(this)
}

object PlainTextEditor {

  private type Props = PlainTextEditor

  private def render(props: Props) = {
    val text = Serializer.deserialize(props.html).document.text
    TruncateMarkup(
      renderTarget = text
    )()
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
