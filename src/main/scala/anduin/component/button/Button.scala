// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.button

import anduin.component.util.ComponentUtils

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Button(
  intent: Button.Intent = Button.Intent.Default,
  isMinimal: Boolean = false,
  isDisable: Boolean = false,
  size: Button.Size = Button.Size.Default,
  tpe: Button.Tpe = Button.Tpe.Default,
  onClick: Callback = Callback.empty
) {
  def apply(children: VdomNode*): VdomElement = {
    Button.component(this)(children: _*)
  }
}

object Button {

  sealed trait Intent {
    val className: String
  }
  case object Intent {
    case object Default extends Intent { val className = "" }
    case object Primary extends Intent { val className = "-primary" }
    case object Success extends Intent { val className = "-success" }
    case object Warning extends Intent { val className = "-warning" }
    case object Danger extends Intent { val className = "-danger" }
  }

  sealed trait Size {
    val className: String
  }
  case object Size {
    case object Default extends Size { val className = "" }
    case object Large extends Size { val className = "-large" }
  }

  sealed trait Tpe {
    def tpe: String
  }
  case object Tpe {
    case object Default extends Tpe { val tpe = "button" }
    case object Submit extends Tpe { val tpe = "submit" }
    case object Reset extends Tpe { val tpe = "reset" }
  }

  private final val ComponentName = ComponentUtils.name(this)

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
      <.button(
        ^.tpe := props.tpe.toString,
        ^.disabled := props.isDisable,
        ^.classSet(
          "at-btn" -> true,
          "-minimal" -> props.isMinimal,
          props.intent.className -> true,
          props.size.className -> true
        ),
        ^.onClick ==> onClick,
        children
      )
    }
  }

  private val component = ScalaComponent
    .builder[Button](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
