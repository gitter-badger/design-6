// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input

import anduin.component.button.Button
import anduin.component.icon.Icon
import anduin.component.menu.{Menu, MenuDivider, MenuItem}
import anduin.style.Style

// scalastyle:off underscore.import
import anduin.component.portal._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

case class DropdownOption[T](
  value: T,
  color: MenuItem.Color = MenuItem.ColorNeutral,
  isDisabled: Boolean = false
)

class Dropdown[T] {

  def apply(): Props.type = Props

  case class Props(
    value: T,
    renderValue: T => VdomNode,
    // ===
    options: List[DropdownOption[T]] = List.empty,
    optionGroups: List[List[DropdownOption[T]]] = List.empty,
    renderOption: T => VdomNode,
    onChange: T => Callback,
    isDisabled: Boolean = false,
    // ===
    position: Position = PositionBottomLeft,
    footer: VdomNode = EmptyVdom,
    header: VdomNode = EmptyVdom
  ) {
    def apply(): VdomElement = component(this)
  }

  private def render(props: Props): VdomElement = {
    Popover(
      position = props.position,
      verticalOffset = 4,
      renderTarget = (open, close, _, status) => renderTarget(props, open, close, status),
      renderContent = (close, _) => renderContent(props, close)
    )()
  }

  private def renderTarget(props: Props, open: Callback, close: Callback, status: Status): VdomElement = {
    val icon = <.span(
      // manually move it a little bit to the right for visual reason
      TagMod(Style.margin.leftAuto.position.relative, ^.right := "-4px"),
      Icon(name = Icon.NameCaretDown)()
    )
    Button(
      onClick = if (status == StatusOpen) close else open,
      isSelected = status == StatusOpen,
      isFullWidth = true,
      isDisabled = props.isDisabled
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

  private def renderOption(props: Props, close: Callback, option: DropdownOption[T]): VdomElement = {
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
