// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.toolbar

import scala.scalajs.js

import anduin.component.editor.utils.DataUtil
import anduin.component.editor.{ParagraphNode, TextAlignNode}
import anduin.component.icon.Icon
import anduin.scalajs.slate.Slate.Value
import anduin.scalajs.slate.SlateReact
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[toolbar] final case class AlignButtonBar(
  value: Value,
  editorRef: () => SlateReact.EditorComponentRef
) {
  def apply(): VdomElement = AlignButtonBar.component(this)
}

private[toolbar] object AlignButtonBar {

  private type Props = AlignButtonBar

  private class Backend(scope: BackendScope[Props, _]) {

    private def hasAlign(value: Value) = {
      value.blocks.exists(_.nodeType == TextAlignNode.nodeType)
    }

    private def getAlign(value: Value) = {
      value.blocks
        .find(_.nodeType == TextAlignNode.nodeType)
        .map { block =>
          DataUtil.value(block.data, "textAlign")
        }
        .getOrElse("")
    }

    private def onClick(align: String) = {
      for {
        props <- scope.props
        editorInstance <- props.editorRef().get
        editor = editorInstance.raw
        originalType = props.value.blocks.headOption
          .map(_.nodeType)
          .getOrElse(ParagraphNode.nodeType)
        _ <- Callback {
          editor
            .focus()
            .setBlocks(
              js.Dynamic.literal(
                `type` = TextAlignNode.nodeType,
                data = js.Dynamic.literal(
                  textAlign = align,
                  originalType = originalType
                )
              )
            )
        }
      } yield ()
    }

    def render(props: Props): VdomElement = {
      <.div(
        Style.flexbox.flex,
        List(
          ("left", Icon(name = Icon.Glyph.AlignLeft)(), "Align Left"),
          ("center", Icon(name = Icon.Glyph.AlignCenter)(), "Align Center"),
          ("right", Icon(name = Icon.Glyph.AlignRight)(), "Align Right")
        ).toVdomArray {
          case (align, icon, tip) =>
            ToolbarButton(
              key = align,
              tip = tip,
              active = hasAlign(props.value) && getAlign(props.value) == align,
              onClick = onClick(align)
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
