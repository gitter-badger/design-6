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

  private val actualStyles = Style.flexbox.flex.flexbox.itemsCenter
  private val ghostStyles =
    TagMod(Style.display.block.overflow.hidden, ^.height := "0")

  private def renderGhostLabel(props: Props): Option[VdomElement] = {
    props.measurement.biggestWidthOption.map(option => {
      <.span(ghostStyles, props.outer.renderValue(option.value))
    })
  }

  private def renderLabel(props: Props): VdomElement = {
    val op = props.outer
    <.span(
      Style.display.block.textAlign.left,
      <.span(actualStyles, op.value.fold(op.placeholder)(op.renderValue)),
      // below 2 ghosts are for fixed target width
      renderGhostLabel(props),
      <.span(ghostStyles, op.placeholder)
    )
  }

  private def render(outerProps: OuterProps): VdomElement = {
    val props = outerProps.props
    val op = props.outer
    val styles = ButtonStyle.getStyles(
      color = ButtonStyle.ColorWhite,
      size = ButtonStyle.SizeMedium,
      style = DropdownTarget.getButtonStyle(op.style),
      isSelected = props.downshift.isOpen.contains(true),
      isFullWidth = op.isFullWidth
    )
    <.button(
      ^.tpe := "button",
      ^.disabled := op.isDisabled || op.options.isEmpty,
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
