// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

import anduin.component.button.{Button, ButtonStyle}
import anduin.component.icon.IconAcl
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Well(
  color: Well.Color = Well.ColorNeutral,
  title: String = "",
  close: Callback = Callback.empty
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
    private[Well] val close = Style.backgroundColor.gray3
  }
  case object ColorPrimary extends Color {
    private[Well] val iconName = IconAcl.NameInfo
    private[Well] val iconColor = Style.color.primary4
    private[Well] val bg = Style.backgroundColor.primary1
  }
  case object ColorSuccess extends Color {
    private[Well] val iconName = IconAcl.NameInfo
    private[Well] val iconColor = Style.color.success4
    private[Well] val bg = Style.backgroundColor.success1
  }
  case object ColorWarning extends Color {
    private[Well] val iconName = IconAcl.NameWarning
    private[Well] val iconColor = Style.color.warning4
    private[Well] val bg = Style.backgroundColor.warning1
  }
  case object ColorDanger extends Color {
    private[Well] val iconName = IconAcl.NameError
    private[Well] val iconColor = Style.color.danger4
    private[Well] val bg = Style.backgroundColor.danger1
  }

  private def renderClose(props: Well): VdomNode = {
    if (props.close == Callback.empty) {
      EmptyVdom
    } else {
      val button = Button(
        onClick = props.close,
        style = ButtonStyle.StyleMinimal,
        size = ButtonStyle.SizeIcon
      )(IconAcl(name = IconAcl.NameCross)())
      <.div(^.padding := "6px 6px 0 0", button)
    }
  }

  private def renderIcon(props: Well): VdomElement = {
    <.div(
      ^.padding := "14px 0 0 12px",
      props.color.iconColor,
      IconAcl(name = props.color.iconName)()
    )
  }

  private def render(props: Well, children: PropsChildren): VdomElement = {
    <.div(
      props.color.bg,
      Style.borderRadius.px2.flexbox.flex,
      <.div(Style.flexbox.none, renderIcon(props)),
      <.div(
        Style.flexbox.fixed.padding.ver12.padding.hor8,
        TagMod.when(props.title.nonEmpty) {
          <.h4(Style.margin.bottom4.color.gray7, props.title)
        },
        children
      ),
      <.div(Style.flexbox.none, renderClose(props))
    )
  }

  private val component = ScalaComponent
    .builder[Well](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
