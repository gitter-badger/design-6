// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import anduin.component.icon.Iconv2
import anduin.scalajs.slate.Slate.{Change, Value}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class MarkButtonBar(
    value: Value,
    onChange: Change => Callback
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = MarkButtonBar.component(this)
}

private[editor] object MarkButtonBar {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[MarkButtonBar, _]) {

    def render(props: MarkButtonBar): VdomElement = {
      <.div(
        List(
          (BoldNode, Iconv2.bold(), "Bold"),
          (ItalicNode, Iconv2.italic(), "Italic"),
          (UnderlineNode, Iconv2.underline(), "Underline"),
          (StrikeThroughNode, Iconv2.strikethrough(), "Strikethrough")
        ).toVdomArray {
          case (markNode, icon, tip) =>
            ToolbarButton(
              key = markNode.nodeType,
              tip = tip,
              active = props.value.activeMarks.some(item => item.markType == markNode.nodeType),
              onClick = {
                val change = props.value.change().toggleMark(markNode.nodeType)
                props.onChange(change)
              }
            )(icon)
        }
      )
    }
  }

  private val component = ScalaComponent
    .builder[MarkButtonBar](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
