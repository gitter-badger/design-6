// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

import anduin.component.icon.Icon
import anduin.scalajs.slate.Slate.{Editor, Value}
import anduin.scalajs.slate.SlateReact
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class AlignButtonBar(
  editorRef: () => SlateReact.EditorComponentRef,
  value: Value,
  onChange: Editor => Callback
) {
  def apply(): VdomElement = AlignButtonBar.component(this)
}

private[editor] object AlignButtonBar {

  private val ComponentName = this.getClass.getSimpleName

  private class Backend(scope: BackendScope[AlignButtonBar, Unit]) {

    private def hasAlign(value: Value) = {
      value.blocks.exists(block => block.nodeType == TextAlignNode.nodeType)
    }

    private def getAlign(value: Value) = {
      value.blocks
        .find(block => block.nodeType == TextAlignNode.nodeType)
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
        _ = editor.setBlocks(
          js.Dynamic.literal(
            `type` = TextAlignNode.nodeType,
            data = js.Dynamic.literal(
              textAlign = align,
              originalType = originalType
            )
          )
        )
        _ <- props.onChange(editor)
      } yield ()
    }

    def render(props: AlignButtonBar): VdomElement = {
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
    .builder[AlignButtonBar](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
