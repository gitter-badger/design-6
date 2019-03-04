// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor

import anduin.component.icon.Icon
import anduin.scalajs.slate.Slate.Value
import anduin.scalajs.slate.SlateReact
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class MarkButtonBar(
  value: Value,
  editorRef: () => SlateReact.EditorComponentRef
) {
  def apply(): VdomElement = MarkButtonBar.component(this)
}

private[editor] object MarkButtonBar {

  private type Props = MarkButtonBar

  private class Backend(scope: BackendScope[Props, _]) {

    private def toggleMark(markNode: MarkNode) = {
      for {
        props <- scope.props
        editorInstance <- props.editorRef().get
        editor = editorInstance.raw
        _ <- Callback {
          editor.toggleMark(markNode.nodeType)
        }
      } yield ()
    }

    def render(props: Props): VdomElement = {
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
              active = props.value.activeMarks.exists(_.markType == markNode.nodeType),
              onClick = toggleMark(markNode)
            )(icon)
        }
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build
}
