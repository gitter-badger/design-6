// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

import anduin.component.icon.IconAcl
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Well(
  color: Well.Color = Well.ColorNeutral,
  title: String = ""
) {
  def apply(children: VdomNode*): VdomElement = {
    Well.component(this)(children: _*)
  }
}

object Well {

  sealed trait Color {
    private[Well] val iconName: IconAcl.Name
    private[Well] val iconColor: Style
    private[Well] val bg: Style
  }
  case object ColorNeutral extends Color {
    private[Well] val iconName = IconAcl.NameBookmark
    private[Well] val iconColor = Style.color.gray7
    private[Well] val bg = Style.backgroundColor.gray2
  }
  case object ColorPrimary extends Color {
    private[Well] val iconName = IconAcl.NameInfoCircle
    private[Well] val iconColor = Style.color.primary4
    private[Well] val bg = Style.backgroundColor.primary1
  }
  case object ColorSuccess extends Color {
    private[Well] val iconName = IconAcl.NameInfoCircle
    private[Well] val iconColor = Style.color.success4
    private[Well] val bg = Style.backgroundColor.success1
  }
  case object ColorWarning extends Color {
    private[Well] val iconName = IconAcl.NameWaring
    private[Well] val iconColor = Style.color.warning4
    private[Well] val bg = Style.backgroundColor.warning1
  }
  case object ColorDanger extends Color {
    private[Well] val iconName = IconAcl.NameExclamationCircle
    private[Well] val iconColor = Style.color.danger4
    private[Well] val bg = Style.backgroundColor.danger1
  }

  def render(props: Well, children: PropsChildren): VdomElement = {
    <.div(
      props.color.bg,
      Style.borderRadius.px2.padding.all12.flexbox.flex,
      <.div(
        // - Manually pushed this down a little bit to align nicely with 20px
        //   content on the right, in case there is only 1 line
        // - We can't use itemsCenter here because the we want the icon to
        //   always stay on top left
        ^.paddingTop := "2px",
        props.color.iconColor,
        Style.flexbox.none.margin.right8,
        IconAcl(name = props.color.iconName)()
      ),
      <.div(
        Style.flexbox.fixed,
        TagMod.when(props.title.nonEmpty) {
          <.h4(Style.margin.bottom4.color.gray7, props.title)
        },
        children
      )
    )
  }

  private val component = ScalaComponent
    .builder[Well](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
