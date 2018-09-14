// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input

import anduin.component.button.{Button, ButtonStyle}
import anduin.component.icon.Icon
import anduin.component.menu.{Menu, MenuDivider, MenuItem}
import anduin.style.Style

// scalastyle:off underscore.import
import anduin.component.portal._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

case class DropdownOption[A](
  value: A,
  color: MenuItem.Color = MenuItem.ColorNeutral,
  isDisabled: Boolean = false
)

class CustomDropdown[A] {

  def apply(): Props.type = Props

  case class Props(
    value: A,
    renderValue: A => VdomNode,
    // ===
    options: List[DropdownOption[A]] = List.empty,
    optionGroups: List[List[DropdownOption[A]]] = List.empty,
    renderOption: A => VdomNode,
    onChange: A => Callback,
    isDisabled: Boolean = false,
    // ===
    position: Position = PositionBottomLeft,
    footer: VdomNode = EmptyVdom,
    header: VdomNode = EmptyVdom,
    buttonStyle: ButtonStyle.Style = ButtonStyle.StyleFull
  ) {
    def apply(): VdomElement = component(this)
  }

  private def render(props: Props): VdomElement = {
    Popover(
      position = props.position,
      verticalOffset = 4,
      renderTarget = (toggle, isOpened) => renderTarget(props, toggle, isOpened),
      renderContent = close => renderContent(props, close)
    )()
  }

  private def renderTarget(props: Props, toggle: Callback, isOpened: Boolean): VdomElement = {
    val icon = <.span(
      // manually move it a little bit to the right for visual reason
      TagMod(Style.margin.leftAuto.position.relative, ^.right := "-4px"),
      Icon(name = Icon.NameCaretDown)()
    )
    Button(
      onClick = toggle,
      isSelected = isOpened,
      isFullWidth = true,
      isDisabled = props.isDisabled,
      style = props.buttonStyle
    )(props.renderValue(props.value), icon)
  }

  private def renderContent(props: Props, close: Callback): VdomNode = {
    val options = Menu()(if (props.options.nonEmpty) {
      props.options.toVdomArray { renderOption(props, close, _) }
    } else {
      renderOptionGroups(props, close)
    })
    val header = renderBoxIfExisted(props.header)
    val footer = renderBoxIfExisted(props.footer)
    ReactFragment(header, options, footer)
  }

  // the box for header and footer
  private val boxStyles = Style.backgroundColor.gray1.padding.ver8.padding.hor16.border.top.borderColor.gray3
  private def renderBoxIfExisted(content: VdomNode): VdomNode = {
    if (content == EmptyVdom) EmptyVdom else <.div(boxStyles, content)
  }

  private def renderOptionGroups(props: Props, close: Callback): VdomNode = {
    // Convert from groups of options
    //   (eg: List[List(1, 2, 3), List(4, 5), List(6)])
    // to nodes of options with dividers in between:
    //   (eg: List[1, 2, 3, div, 4, 5, div, 6])
    val nodes = props.optionGroups.zipWithIndex
      .flatMap {
        case (group, index) =>
          val options = group.map(renderOption(props, close, _))
          val div: VdomNode = <.div(^.key := index, MenuDivider()())
          options :+ div
      }
      .dropRight(1)
    nodes.toVdomArray(node => node)
  }

  private def renderOption(props: Props, close: Callback, option: DropdownOption[A]): VdomElement = {
    <.div(
      ^.key := option.value.toString,
      MenuItem(
        color = option.color,
        isSelected = option.value == props.value,
        isDisabled = option.isDisabled,
        onClick = props.onChange(option.value) >> close
      )(props.renderOption(option.value))
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build

}
