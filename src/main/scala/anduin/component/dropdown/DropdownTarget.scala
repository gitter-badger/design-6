// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.component.button.Button
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

  private def getButtonStyle(props: Props) = {
    val isSelected = props.downshift.isOpen.contains(true)
    val isFullWidth = props.outer.isFullWidth
    props.outer.style match {
      case Dropdown.StyleFull =>
        Button.Style.Full(isSelected = isSelected, isFullWidth = isFullWidth)
      case Dropdown.StyleMinimal =>
        Button.Style.Minimal(isSelected = isSelected, isFullWidth = isFullWidth)
    }
  }

  private def render(outerProps: OuterProps): VdomElement = {
    val props = outerProps.props
    val style = getButtonStyle(props)
    val isDisabled = props.outer.isDisabled
    <.button(
      ^.id :=? props.outer.id,
      ^.tpe := "button",
      ^.disabled := isDisabled,
      Util.getModsFromProps(props.downshift.getToggleButtonProps()),
      style.container,
      style.sizeRect,
      if (isDisabled) style.colorDisabled else style.colorNormal
    )(
      <.span(
        style.body,
        renderLabel(props),
        DropdownTarget.icon
      )
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
    Icon(name = Icon.Glyph.CaretDown)()
  )
}
