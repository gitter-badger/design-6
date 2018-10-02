// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input

import anduin.component.icon.Icon
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class TextBox(
  value: String,
  onFocus: Callback = Callback.empty,
  onChange: String => Callback = _ => Callback.empty,
  placeholder: String = "",
  context: VdomNode = EmptyVdom,
  // ===
  tpe: TextBox.Tpe = TextBox.TpeSingle,
  status: TextBox.Status = TextBox.StatusNone,
  size: TextBox.Size = TextBox.Size32,
  // ===
  isDisabled: Boolean = false,
  isRequired: Boolean = false,
  isReadOnly: Boolean = false,
  isAutoFocus: Boolean = false
) {
  def apply(): VdomElement = TextBox.component(this)
}

object TextBox {

  private type Props = TextBox

  sealed abstract class Tpe(val styles: TagMod)
  case object TpeSingle extends Tpe(Style.lineHeight.px16)
  case object TpeArea extends Tpe(Style.lineHeight.ratio1p5.padding.ver8)

  sealed abstract class Size(val height: TagMod, val font: TagMod)
  case object Size32 extends Size(Style.height.px32, Style.fontSize.px13)
  case object Size40 extends Size(Style.height.px40, Style.fontSize.px16)

  // ===

  trait Status {
    val styles: Style = Style.borderColor.gray4
    val icon: Option[VdomNode] = None
  }
  private def renderIcon(name: Icon.Name, color: TagMod) = {
    val styles = TagMod(
      Style.position.absolute.coordinate.top0.coordinate.bottom0,
      Style.backgroundColor.white.margin.verAuto,
      TagMod(^.right := "8px", ^.height := "16px")
    )
    <.div(styles, color, Icon(name = name)())
  }
  case object StatusNone extends Status
  case object StatusBusy extends Status
  case object StatusValid extends Status {
    override val styles: Style = Style.borderColor.success4
    override val icon: Option[VdomNode] = Option(renderIcon(Icon.NameCheck, Style.color.success4))
  }
  case object StatusInvalid extends Status {
    override val styles: Style = Style.borderColor.danger4
  }

  // === Predefined styles

  private[component] object Styles {
    private val border = Style.border.all.borderWidth.px1.borderRadius.px2

    val input = TagMod(
      Style.display.block.width.pc100.padding.hor12,
      Style.focus.spread.focus.border.transition.allWithShadow,
      border
    )

    val inputEnabled = Style.backgroundColor.white.color.gray8
    val inputDisabled = Style.backgroundColor.gray2.color.gray6

    val context = TagMod(
      Style.padding.hor12.backgroundColor.gray2.color.gray6,
      Style.borderWidth.right0.borderRadius.left,
      Style.flexbox.flex.flexbox.itemsCenter,
      StatusNone.styles,
      border
    )
  }

  // ===

  private def onChange(props: Props)(e: ReactEventFromInput): Callback = {
    props.onChange(e.target.value)
  }

  private def render(props: Props): VdomElement = {
    val height = TagMod.when(props.tpe != TpeArea) { props.size.height }
    val commonStyles = TagMod(props.size.font, props.tpe.styles, height)
    val contextIsDefined = props.context != EmptyVdom
    val tag: VdomTag = if (props.tpe == TpeArea) <.textarea else <.input

    val input = tag(
      // styles
      TagMod(commonStyles, Styles.input, props.status.styles),
      TagMod.when(contextIsDefined) { Style.borderRadius.right },
      TagMod.when(props.isReadOnly) { Style.backgroundColor.gray1 },
      if (props.isDisabled) Styles.inputDisabled else Styles.inputEnabled,
      // behaviours
      TagMod.when(props.tpe != TpeArea) { ^.tpe := "text" },
      ^.disabled := props.isDisabled,
      ^.readOnly := props.isReadOnly,
      ^.onChange ==> onChange(props),
      ^.onFocus --> props.onFocus,
      ^.value := props.value,
      ^.placeholder := props.placeholder,
      ^.autoFocus := props.isAutoFocus
    )
    val context = TagMod.when(contextIsDefined) {
      <.div(commonStyles, Styles.context, props.context)
    }
    val icon = props.status.icon.whenDefined

    <.div(Style.position.relative.flexbox.flex, context, input, icon)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
