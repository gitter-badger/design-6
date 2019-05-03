// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.menu

import anduin.component.icon.Icon

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

import anduin.style.Style

final case class MenuItem(
  // common
  isSelected: Boolean = false,
  onClick: Callback = Callback.empty,
  color: MenuItem.Color = MenuItem.ColorNeutral,
  icon: Option[Icon.Name] = None,
  // link
  url: String = "",
  openIn: MenuItem.OpenIn = MenuItem.OpenInThisTab,
  // button
  isDisabled: Boolean = false
) {
  def apply(children: VdomNode*): VdomElement = {
    MenuItem.component(this)(children: _*)
  }
}

object MenuItem {

  private type Props = MenuItem

  sealed trait OpenIn { def tag: TagMod }
  case object OpenInNewTab extends OpenIn { val tag: TagMod = ^.target.blank }
  case object OpenInThisTab extends OpenIn { val tag: TagMod = ^.target.self }

  sealed trait Color { def styles: TagMod }
  case object ColorGray extends Color {
    val styles: TagMod = Style.color.gray8.background.hoverGray3.background.activeGray4
  }
  case object ColorNeutral extends Color {
    private[MenuItem] val color = Style.color.hoverGray0.color.activeGray0
    private[MenuItem] val bg = Style.background.hoverPrimary4.background.activePrimary5
    val styles = TagMod(Style.color.gray8, color, bg)
  }
  case object ColorPrimary extends Color {
    val styles = TagMod(ColorNeutral.color, Style.color.primary5, ColorNeutral.bg)
  }
  case object ColorSuccess extends Color {
    val styles = TagMod(ColorNeutral.color, Style.color.success5.background.hoverSuccess4.background.activeSuccess5)
  }
  case object ColorWarning extends Color {
    val styles = TagMod(ColorNeutral.color, Style.color.warning5.background.hoverWarning4.background.activeWarning5)
  }
  case object ColorDanger extends Color {
    val styles = TagMod(ColorNeutral.color, Style.color.danger5.background.hoverDanger4.background.activeDanger5)
  }

  case class ColorCustom(styles: TagMod) extends Color

  private val commonStyles = TagMod(
    Style.flexbox.flex.flexbox.itemsCenter,
    Style.padding.ver8.padding.hor16.lineHeight.px16
  )
  private val linkStyles = TagMod(commonStyles, Style.textDecoration.hoverNone)
  private[component] val buttonStyles: TagMod = TagMod(
    commonStyles,
    Style.width.pc100.textAlign.left
  )

  private val checkIcon = <.span(
    Style.margin.leftAuto,
    Icon(name = Icon.Glyph.Check)()
  )

  private def render(props: Props, children: PropsChildren): VdomElement = {
    val commonMods = TagMod(
      // common styles
      if (props.isDisabled) Style.color.gray4 else props.color.styles,
      // behaviour
      TagMod.when(!props.isDisabled) { ^.onClick --> props.onClick },
      // content
      props.icon.map(name => {
        val margin = TagMod.when(children.nonEmpty)(Style.margin.right8)
        <.span(margin, Icon(name = name)())
      }),
      children,
      TagMod.when(props.isSelected) { checkIcon }
    )
    if (props.url.nonEmpty) {
      <.a(linkStyles, ^.href := props.url, props.openIn.tag, commonMods)
    } else {
      <.button(buttonStyles, ^.tpe := "button", ^.disabled := props.isDisabled, commonMods)
    }
  }

  private val component = ScalaComponent
    .builder[MenuItem](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
