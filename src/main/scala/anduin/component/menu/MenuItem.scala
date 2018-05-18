// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.menu

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.component.icon.IconAcl
// scalastyle:on underscore.import

import anduin.style.Style

final case class MenuItem(
  // common
  isSelected: Boolean = false,
  onClick: Callback = Callback.empty,
  color: MenuItem.Color = MenuItem.ColorNeutral,
  // link
  url: String = "",
  openIn: MenuItem.OpenIn = MenuItem.OpenInThisTab,
  // button
  isDisabled: Boolean = false
) {
  def apply(children: VdomNode*): VdomElement = MenuItem.component(this)(children: _*)
}

object MenuItem {

  type Props = MenuItem

  sealed trait OpenIn { val value: String }
  case object OpenInNewTab extends OpenIn { val value = "_blank" }
  case object OpenInThisTab extends OpenIn { val value = "_self" }

  sealed trait Color { val styles: Style }
  case object ColorNeutral extends Color {
    val styles: Style = Style.color.gray8.hover.backgroundPrimary4.active.backgroundPrimary5
  }
  case object ColorPrimary extends Color {
    val styles: Style = Style.color.primary5.hover.backgroundPrimary4.active.backgroundPrimary5
  }
  case object ColorSuccess extends Color {
    val styles: Style = Style.color.success5.hover.backgroundSuccess4.active.backgroundSuccess5
  }
  case object ColorWarning extends Color {
    val styles: Style = Style.color.warning5.hover.backgroundWarning4.active.backgroundWarning5
  }
  case object ColorDanger extends Color {
    val styles: Style = Style.color.danger5.hover.backgroundDanger4.active.backgroundDanger5
  }

  def render(props: Props, children: PropsChildren): VdomElement = {
    val commonMods = TagMod(
      // common styles
      Style.flexbox.flex.flexbox.itemsCenter.padding.ver8.padding.hor16.lineHeight.px16,
      Style.hover.colorWhite.disabled.colorGray6.disabled.backgroundNone,
      props.color.styles,
      // behaviour
      TagMod.when(!props.isDisabled) { ^.onClick --> props.onClick },
      // content
      children,
      TagMod.when(props.isSelected) { <.span(Style.margin.leftAuto, IconAcl(name = IconAcl.NameCheck)()) }
    )
    if (props.url.nonEmpty) {
      <.a(
        Style.hover.underlineNone,
        ^.href := props.url,
        ^.target := props.openIn.value,
        commonMods
      )
    } else {
      <.button(
        ^.tpe := "button",
        ^.disabled := props.isDisabled,
        commonMods
      )
    }
  }

  private val component = ScalaComponent
    .builder[MenuItem](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
