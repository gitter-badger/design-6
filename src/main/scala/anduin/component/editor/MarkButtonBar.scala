// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor

import anduin.component.icon.Icon
import anduin.scalajs.slate.Slate.{Editor, Value}
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class MarkButtonBar(
  value: Value,
  onChange: Editor => Callback
) {
  def apply(): VdomElement = MarkButtonBar.component(this)
}

private[editor] object MarkButtonBar {

  private val ComponentName = this.getClass.getSimpleName

  private class Backend(scope: BackendScope[MarkButtonBar, _]) {

    private def toggleMark(markNode: MarkNode) = {
      for {
        props <- scope.props
        value = props.value
        change = value.change()
        newChange = if (value.isFocused) {
          change.toggleMark(markNode.nodeType)
        } else {
          change.focus().toggleMark(markNode.nodeType)
        }
        _ <- props.onChange(newChange)
      } yield ()
    }

    def render(props: MarkButtonBar): VdomElement = {
      <.div(
        Style.flexbox.flex,
        List(
          (BoldNode, Icon(name = Icon.Glyph.Bold)(), "Bold"),
          (ItalicNode, Icon(name = Icon.Glyph.Italic)(), "Italic"),
          (UnderlineNode, Icon(name = Icon.Glyph.Underline)(), "Underline"),
          (StrikeThroughNode, Icon(name = Icon.Glyph.StrikeThrough)(), "Strikethrough")
        ).toVdomArray {
          case (markNode, icon, tip) =>
            ToolbarButton(
              key = markNode.nodeType,
              tip = tip,
              active = props.value.activeMarks.exists(item => item.markType == markNode.nodeType),
              onClick = toggleMark(markNode)
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
