// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.well

import anduin.component.button.{Button, ButtonStyle}
import anduin.component.icon.Icon
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Well(
  color: Well.Color = Well.ColorGray,
  title: String = "",
  close: Callback = Callback.empty
) {
  def apply(children: VdomNode*): VdomElement = {
    Well.component(this)(children: _*)
  }
}

object Well {

  sealed trait Color {
    private[Well] val iconName: Icon.Name
    private[Well] val iconColor: Style
    private[Well] val bg: Style
  }
  case object ColorGray extends Color {
    private[Well] val iconName = Icon.NameBookmark
    private[Well] val iconColor = Style.color.gray7
    private[Well] val bg = Style.backgroundColor.gray1
    private[Well] val close = Style.backgroundColor.gray3
  }
  case object ColorBlue extends Color {
    private[Well] val iconName = Icon.NameInfo
    private[Well] val iconColor = Style.color.primary4
    private[Well] val bg = Style.backgroundColor.primary1
  }
  case object ColorGreen extends Color {
    private[Well] val iconName = Icon.NameCheck
    private[Well] val iconColor = Style.color.success4
    private[Well] val bg = Style.backgroundColor.success1
  }
  case object ColorOrange extends Color {
    private[Well] val iconName = Icon.NameWarning
    private[Well] val iconColor = Style.color.warning4
    private[Well] val bg = Style.backgroundColor.warning1
  }
  case object ColorRed extends Color {
    private[Well] val iconName = Icon.NameError
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
        size = ButtonStyle.SizeIcon,
        icon = Some(Icon.NameCross)
      )()
      <.div(^.padding := "6px 6px 0 0", button)
    }
  }

  private def renderIcon(props: Well): VdomElement = {
    <.div(
      ^.padding := "14px 0 0 12px",
      props.color.iconColor,
      Icon(name = props.color.iconName)()
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
