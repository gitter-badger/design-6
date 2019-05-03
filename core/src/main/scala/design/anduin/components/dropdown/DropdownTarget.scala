// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.component.button.Button
import anduin.component.icon.Icon
import anduin.scalajs.downshift.DownshiftRenderProps
import anduin.scalajs.util.ScalaJSUtils
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[dropdown] class DropdownTarget[A] {

  def apply(): Props.type = Props

  case class Props(
    dropdown: Dropdown[A]#Props,
    downshift: DownshiftRenderProps[A],
    measurement: Dropdown.Measurement[A]
  ) {
    def apply(): VdomElement = component(this)
  }

  // This could be simpler if A => String conforms to A => VdomNode
  private def renderValue(p: Props)(v: A): VdomNode = {
    p.dropdown.renderValue.map(_(v)).getOrElse(p.dropdown.getValueString(v))
  }

  private def renderGhost(props: Props): VdomElement = {
    <.span(
      TagMod(Style.display.block.overflow.hidden, ^.height := "0"),
      props.measurement.biggestWidthOption.map(option => {
        <.span(Style.display.block, renderValue(props)(option.value))
      }),
      <.span(Style.display.block, props.dropdown.placeholder)
    )
  }

  private def renderCurrent(props: Props): VdomElement = {
    <.span(
      Style.flexbox.flex.flexbox.itemsCenter,
      props.dropdown.value.fold(props.dropdown.placeholder)(renderValue(props))
    )
  }

  private def renderLabel(props: Props): VdomElement = {
    <.span(
      Style.display.block.textAlign.left,
      renderCurrent(props),
      renderGhost(props)
    )
  }

  private def getStyle(props: Props) = {
    val isSelected = props.downshift.isOpen
    val isFullWidth = props.dropdown.isFullWidth
    props.dropdown.style match {
      case Dropdown.StyleFull    => Button.Style.Full(isSelected = isSelected, isFullWidth = isFullWidth)
      case Dropdown.StyleMinimal => Button.Style.Minimal(isSelected = isSelected, isFullWidth = isFullWidth)
    }
  }

  private def render(props: Props): VdomElement = {
    val style = getStyle(props)
    <.button(
      // Behaviour
      ScalaJSUtils.jsPropsToTagMod(props.downshift.getToggleButtonProps()),
      ^.id :=? props.dropdown.id,
      ^.tpe := "button",
      ^.disabled := props.dropdown.isDisabled,
      // Styling
      style.container,
      style.sizeRect,
      if (props.dropdown.isDisabled) style.colorDisabled else style.colorNormal
    )(<.span(style.body, renderLabel(props), DropdownTarget.icon))
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
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
