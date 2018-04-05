// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

import anduin.component.icon.Iconv2
import anduin.scalajs.slate.Slate.{Change, Value}
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class AlignButtonBar(
  value: Value,
  onChange: Change => Callback
) {
  def apply(): VdomElement = AlignButtonBar.component(this)
}

private[editor] object AlignButtonBar {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[AlignButtonBar, _]) {

    private def hasAlign(value: Value) = {
      value.blocks.some(block => block.nodeType == TextAlignNode.nodeType)
    }

    private def getAlign(value: Value) = {
      value.blocks
        .filter(block => block.nodeType == TextAlignNode.nodeType)
        .first()
        .toOption
        .map { block =>
          DataUtil.value(block.data, "textAlign")
        }
        .getOrElse("")
    }

    def render(props: AlignButtonBar): VdomElement = {
      <.div(
        Style.flexbox.flex,
        List(
          ("left", Iconv2.leftAlign(), "Align Left"),
          ("center", Iconv2.centerAlign(), "Align Center"),
          ("right", Iconv2.rightAlign(), "Align Right")
        ).toVdomArray {
          case (align, icon, tip) =>
            ToolbarButton(
              key = align,
              tip = tip,
              active = hasAlign(props.value) && getAlign(props.value) == align,
              onClick = {
                val originalType: String = props.value.blocks
                  .first()
                  .toOption
                  .map(_.nodeType)
                  .getOrElse(ParagraphNode.nodeType)

                val change = props.value
                  .change()
                  .setBlock(
                    js.Dynamic.literal(
                      `type` = TextAlignNode.nodeType,
                      data = js.Dynamic.literal(
                        textAlign = align,
                        originalType = originalType
                      )
                    )
                  )
                props.onChange(change)
              }
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
