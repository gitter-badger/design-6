// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.component.button.ButtonStyle
import anduin.component.icon.Icon
import anduin.scalajs.util.Util
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[dropdown] class DropdownTarget[A] {

  def apply(): OuterProps.type = OuterProps

  case class OuterProps(props: Props) {
    def apply(): VdomElement = component(this)
  }

  private type Props = Dropdown[A]#InnerProps

  // This could be simpler if A => String conforms to A => VdomNode
  private def renderValue(p: Props)(v: A): VdomNode = {
    p.outer.renderValue.map(_(v)).getOrElse(p.outer.getValueString(v))
  }

  private def renderGhost(props: Props): VdomElement = {
    <.span(
      TagMod(Style.display.block.overflow.hidden, ^.height := "0"),
      props.measurement.biggestWidthOption.map(option => {
        <.span(Style.display.block, renderValue(props)(option.value))
      }),
      <.span(Style.display.block, props.outer.placeholder)
    )
  }

  private def renderCurrent(props: Props): VdomElement = {
    <.span(
      Style.flexbox.flex.flexbox.itemsCenter,
      props.outer.value.fold(props.outer.placeholder)(renderValue(props))
    )
  }

  private def renderLabel(props: Props): VdomElement = {
    <.span(
      Style.display.block.textAlign.left,
      renderCurrent(props),
      renderGhost(props)
    )
  }

  private def render(outerProps: OuterProps): VdomElement = {
    val props = outerProps.props
    val op = props.outer
    val styles = ButtonStyle.getStyles(
      color = ButtonStyle.ColorWhite,
      size = ButtonStyle.Size32,
      style = DropdownTarget.getButtonStyle(op.style),
      isSelected = props.downshift.isOpen.contains(true),
      isFullWidth = op.isFullWidth
    )
    <.button(
      ^.id :=? op.id,
      ^.tpe := "button",
      ^.disabled := op.isDisabled,
      Util.getModsFromProps(props.downshift.getToggleButtonProps()),
      styles
    )(
      renderLabel(props),
      DropdownTarget.icon
    )
  }

  private val component = ScalaComponent
    .builder[OuterProps](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}

private[dropdown] object DropdownTarget {

  private val icon = <.span(
    // manually move it a little bit to the right for visual reason
    TagMod(Style.margin.leftAuto.position.relative, ^.right := "-4px"),
    Icon(name = Icon.NameCaretDown)()
  )

  private def getButtonStyle(style: Dropdown.Style) = style match {
    case Dropdown.StyleFull    => ButtonStyle.StyleFull
    case Dropdown.StyleMinimal => ButtonStyle.StyleMinimal
  }
}
