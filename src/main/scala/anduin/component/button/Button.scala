// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.button

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Button(
  color: ButtonStyle.Color = ButtonStyle.ColorWhite,
  size: ButtonStyle.Size = ButtonStyle.SizeMedium,
  style: ButtonStyle.Style = ButtonStyle.StyleFull,
  isFullWidth: Boolean = false,
  isSelected: Boolean = false,
  isDisabled: Boolean = false, // if tpe != link
  // Specific behaviours for Button
  tpe: Button.Tpe = Button.TpeButton,
  onClick: Callback = Callback.empty,
  href: String = "" // if tpe == link
) {
  def apply(children: VdomNode*): VdomElement = {
    Button.component(this)(children: _*)
  }
}

object Button {

  private val ComponentName = this.getClass.getSimpleName

  sealed trait Tpe { val value: String }
  case object TpeLink extends Tpe { val value = "link" }
  case object TpeButton extends Tpe { val value = "button" }
  case object TpeSubmit extends Tpe { val value = "submit" }
  case object TpeReset extends Tpe { val value = "reset" }

  private case class Backend(scope: BackendScope[Button, _]) {

    private def onClick(e: ReactEventFromHtml) = {
      for {
        _ <- e.preventDefaultCB
        _ <- e.stopPropagationCB
        props <- scope.props
        _ <- props.onClick
      } yield ()
    }

    def render(props: Button, children: PropsChildren): VdomElement = {
      // onClick.isEmpty: allow default behaviour if onClick is not defined
      // eg: TpeSubmit and TpeReset
      val onClickMod = TagMod.when(!props.isDisabled && !props.onClick.isEmpty_?) {
        ^.onClick ==> onClick
      }
      // common for <button> and <a>
      val styles = ButtonStyle.getStyles(
        color = props.color,
        size = props.size,
        style = props.style,
        isSelected = props.isSelected,
        isFullWidth = props.isFullWidth
      )
      val commonMods = TagMod(styles, onClickMod, children)
      props.tpe match {
        // link that looks like a button
        case TpeLink => <.a(^.href := props.href, commonMods)
        // real button
        case _ => <.button(^.tpe := props.tpe.value, ^.disabled := props.isDisabled, commonMods)
      }
    }
  }

  private val component = ScalaComponent
    .builder[Button](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
