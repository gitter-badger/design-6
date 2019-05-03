// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.radio

import anduin.component.icon.Icon
import anduin.component.icon.Icon.Glyph.{Blank, Circle}
import anduin.component.input.labelwrapper.LabelWrapper
import anduin.style.{Style => SStyle}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Radio(
  isChecked: Boolean,
  onChange: Callback,
  isDisabled: Boolean = false,
  style: Radio.Style = Radio.Style.Minimal
) {
  def apply(children: VdomNode*): VdomElement =
    Radio.component(this)(children: _*)
}

object Radio {

  private type Props = Radio

  sealed trait Style {
    private[radio] def get: Props => LabelWrapper.Style
  }
  object Style {
    object Minimal extends Style {
      private[radio] def get = _ => LabelWrapper.Style.Minimal
    }
    object Full extends Style {
      private[radio] def get = props => LabelWrapper.Style.Full(props.isChecked)
    }
  }

  private val boxStaticStyles = TagMod(
    SStyle.display.block.width.px16.height.px16.borderRadius.pill.border.all,
    SStyle.outline.focusLight.transition.allWithOutline
  )

  private def boxGetStyles(props: Props): TagMod = {
    val specific: TagMod = if (props.isDisabled) {
      SStyle.background.gray1.borderColor.gray3
    } else if (props.isChecked) {
      SStyle.background.primary4.borderColor.primary5
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
      ^.tpe := "radio",
      ^.checked := props.isChecked,
      ^.onChange --> props.onChange,
      ^.disabled := props.isDisabled
    )
  }

  private def renderGlyph(props: Props): VdomElement = {
    <.div(
      // Should not consume events
      SStyle.pointerEvents.none,
      if (props.isDisabled) SStyle.color.gray4 else SStyle.color.gray0,
      Icon(if (props.isChecked) Circle else Blank, Icon.Size.Custom(12))()
    )
  }

  private def render(props: Props, children: PropsChildren): VdomElement = {
    LabelWrapper(
      inputChildren = List((16, renderBox(props)), (12, renderGlyph(props))),
      isDisabled = props.isDisabled,
      isInteractive = !props.isChecked,
      style = props.style.get(props)
    )(children)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
