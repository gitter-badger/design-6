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

  sealed trait Color { val styles: TagMod }
  case object ColorGray extends Color {
    val styles: TagMod = Style.color.gray8.hover.backgroundGray2.active.backgroundGray3
  }
  case object ColorNeutral extends Color {
    private[MenuItem] val color = Style.hover.colorWhite.active.colorWhite
    private[MenuItem] val bg = Style.hover.backgroundPrimary4.active.backgroundPrimary5
    val styles = TagMod(Style.color.gray8, color, bg)
  }
  case object ColorPrimary extends Color {
    val styles = TagMod(ColorNeutral.color, Style.color.primary5, ColorNeutral.bg)
  }
  case object ColorSuccess extends Color {
    val styles = TagMod(ColorNeutral.color, Style.color.success5.hover.backgroundSuccess4.active.backgroundSuccess5)
  }
  case object ColorWarning extends Color {
    val styles = TagMod(ColorNeutral.color, Style.color.warning5.hover.backgroundWarning4.active.backgroundWarning5)
  }
  case object ColorDanger extends Color {
    val styles = TagMod(ColorNeutral.color, Style.color.danger5.hover.backgroundDanger4.active.backgroundDanger5)
  }

  def render(props: Props, children: PropsChildren): VdomElement = {
    val commonMods = TagMod(
      // common styles
      Style.flexbox.flex.flexbox.itemsCenter.padding.ver8.padding.hor16.lineHeight.px16,
      props.color.styles,
      // behaviour
      TagMod.when(!props.isDisabled) { ^.onClick --> props.onClick },
      // content
      children,
      TagMod.when(props.isSelected) { <.span(Style.margin.leftAuto, IconAcl(name = IconAcl.NameCheck)()) }
    )
    if (props.url.nonEmpty) {
      <.a(
        Style.hover.underlineNone.hover.colorGray9,
        ^.href := props.url,
        ^.target := props.openIn.value,
        commonMods
      )
    } else {
      <.button(
        Style.width.pc100.textAlign.left,
        Style.disabled.colorGray6.disabled.backgroundNone,
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
