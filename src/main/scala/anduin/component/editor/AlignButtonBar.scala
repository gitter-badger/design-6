// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js

import anduin.component.icon.Icon
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

  private class Backend() {

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

    def render(props: AlignButtonBar): VdomElement = {
      <.div(
        Style.flexbox.flex,
        List(
          ("left", Icon(name = Icon.NameAlignLeft)(), "Align Left"),
          ("center", Icon(name = Icon.NameAlignCenter)(), "Align Center"),
          ("right", Icon(name = Icon.NameAlignRight)(), "Align Right")
        ).toVdomArray {
          case (align, icon, tip) =>
            ToolbarButton(
              key = align,
              tip = tip,
              active = hasAlign(props.value) && getAlign(props.value) == align,
              onClick = {
                val originalType: String = props.value.blocks
                  .headOption
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
