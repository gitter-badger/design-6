// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input

import anduin.component.icon.IconAcl
import anduin.component.util.ComponentUtils
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// TODO: Provide default for:
// - renderItem
// - checkItemSelected
final case class MenuSelectProps[I](
  items: List[I],
  renderItem: I => VdomNode,
  checkItemSelected: I => Boolean,
  selectItem: I => Callback
)

class MenuSelect[I] {
  type Props = MenuSelectProps[I]

  val ComponentName: String = ComponentUtils.name(this)

  private class Backend() {
    def render(props: Props, children: PropsChildren): VdomElement = {
      <.div(
        Style.border.all.borderRadius.px4,
        ^.cls := "at-b--gray-2",
        <.ul(
          ^.cls := "at-menu -divider -custom-height -clickable -light-active",
          props.items.toVdomArray(id => {
            val isSelected = props.checkItemSelected(id)
            <.li(
              ^.key := id.toString,
              ^.cls := s"at-menu-item ${if (isSelected) "-active" else ""}",
              ^.onClick --> props.selectItem(id),
              ^.role := "button",
              ^.tabIndex := 0,
              <.div(Style.flexbox.grow1, props.renderItem(id)),
              TagMod.when(isSelected)(
                <.div(
                  Style.flexbox.grow0.padding.right8,
                  ^.cls := "at-green",
                  IconAcl(name = IconAcl.NameCheck)()
                )
              )
            )
          })
        ),
        TagMod.unless(children.isEmpty)(
          <.div(Style.border.top, ^.cls := "at-b--gray-2", children)
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}

object MenuSelect {
  def apply[I](
    items: List[I],
    renderItem: I => VdomNode,
    checkItemSelected: I => Boolean,
    selectItem: I => Callback
  )(children: VdomNode*)(implicit menuSelect: MenuSelect[I]): TagMod = {
    val props = MenuSelectProps(
      items,
      renderItem,
      checkItemSelected,
      selectItem
    )
    menuSelect.component(props)(children: _*)
  }
}
