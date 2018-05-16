// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.menu

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

import anduin.style.Style

final case class MenuItem(
  color: MenuItem.Color = MenuItem.Default,
  isDisabled: Boolean = false,
  openIn: MenuItem.OpenIn = MenuItem.ThisTab,
  url: String = "",
  onClick: Callback = Callback.empty
) {
  def apply(children: VdomNode*): VdomElement = {
    MenuItem.component(this)(children: _*)
  }
}

object MenuItem {

  private val ComponentName = this.getClass.getSimpleName

  sealed trait OpenIn { val value: String }
  case object NewTab extends OpenIn { val value = "_blank" }
  case object ThisTab extends OpenIn { val value = "_self" }

  sealed trait Color { val style: TagMod }
  case object Default extends Color { val style: TagMod = Style.color.gray8.hover.backgroundPrimary4 }
  case object Primary extends Color { val style: TagMod = Style.color.primary4.hover.backgroundPrimary4 }
  case object Warning extends Color { val style: TagMod = Style.color.warning4.hover.backgroundWarning4 }
  case object Danger extends Color { val style: TagMod = Style.color.danger4.hover.backgroundDanger4 }
  case object Success extends Color { val style: TagMod = Style.color.success4.hover.backgroundSuccess4 }

  private case class Backend(scope: BackendScope[MenuItem, _]) {

    private def onClick(e: ReactEventFromHtml) = {
      for {
        _ <- e.preventDefaultCB
        props <- scope.props
        _ <- props.onClick
      } yield ()
    }

    def render(props: MenuItem, children: PropsChildren): VdomElement = {
      val styles = TagMod(
        Style.flexbox.flex.padding.ver8.padding.hor12.hover.underlineNone.lineHeight.px16.hover.colorWhite,
        TagMod.when(!props.isDisabled)(props.color.style),
        TagMod.when(props.isDisabled)(Style.color.gray6.cursor.notAllowed)
      )

      val onClickMod = TagMod.when(!props.isDisabled && !props.onClick.isEmpty_?) {
        ^.onClick ==> onClick
      }

      val commonMods = TagMod(styles, onClickMod, children)

      if (props.url.nonEmpty && !props.isDisabled) {
        <.a(^.href := props.url, ^.target := props.openIn.value, commonMods)
      } else {
        <.button(commonMods)
      }
    }
  }

  private val component = ScalaComponent
    .builder[MenuItem](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
