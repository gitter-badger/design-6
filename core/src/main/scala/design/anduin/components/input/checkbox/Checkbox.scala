// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.checkbox

import anduin.component.icon.Icon
import anduin.component.icon.Icon.Glyph.{Blank, CheckBold, MinusBold}
import anduin.component.input.labelwrapper.LabelWrapper
import anduin.style.{Style => SStyle}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Checkbox(
  isChecked: Boolean,
  onChange: Boolean => Callback = _ => Callback.empty,
  isDisabled: Boolean = false,
  isIndeterminate: Boolean = false,
  style: Checkbox.Style = Checkbox.Style.Minimal
) {
  def apply(children: VdomNode*): VdomElement = {
    Checkbox.component(this)(children: _*)
  }
}

object Checkbox {

  private type Props = Checkbox

  sealed trait Style {
    private[checkbox] def get: Props => LabelWrapper.Style
  }
  object Style {
    object Minimal extends Style {
      private[checkbox] def get = _ => LabelWrapper.Style.Minimal
    }
    object Full extends Style {
      private[checkbox] def get = props => LabelWrapper.Style.Full(props.isChecked)
    }
  }

  private def inputOnChange(props: Props)(e: ReactEventFromInput) =
    props.onChange(e.target.checked)

  private val boxStaticStyles = TagMod(
    SStyle.display.block.width.px16.height.px16.borderRadius.px2.border.all,
    SStyle.outline.focusLight.transition.allWithOutline
  )

  private def boxGetStyles(props: Props): TagMod = {
    val specific: TagMod = if (props.isDisabled) {
      SStyle.background.gray1.borderColor.gray3
    } else if (props.isChecked || props.isIndeterminate) {
      TagMod(
        SStyle.background.primary4.background.hoverPrimary3.background.activePrimary5,
        SStyle.borderColor.primary5.shadow.px1Dark
      )
    } else {
      TagMod(
        SStyle.background.gray1.background.hoverGray0.background.activeGray2,
        SStyle.borderColor.gray4.shadow.px1Light
      )
    }
    TagMod(boxStaticStyles, specific)
  }

  // This is the actual <input>
  private def renderBox(props: Props): VdomElement = {
    <.input(
      boxGetStyles(props),
      ^.tpe := "checkbox",
      ^.checked := props.isChecked,
      ^.onChange ==> inputOnChange(props),
      ^.disabled := props.isDisabled
    )
  }

  private def renderGlyph(props: Props): VdomElement = {
    <.div(
      // Should not consume events
      SStyle.pointerEvents.none,
      if (props.isDisabled) SStyle.color.gray4 else SStyle.color.gray0,
      Icon(if (props.isIndeterminate) MinusBold else if (props.isChecked) CheckBold else Blank)()
    )
  }

  private def render(props: Props, children: PropsChildren): VdomElement = {
    LabelWrapper(
      inputChildren = List((16, renderBox(props)), (16, renderGlyph(props))),
      isDisabled = props.isDisabled,
      isInteractive = true,
      style = props.style.get(props)
    )(children)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
