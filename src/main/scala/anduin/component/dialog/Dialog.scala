// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.dialog

import anduin.component.button.{Button, ButtonStyle}
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Dialog(
  message: Dialog.Message,
  submit: Dialog.Submit,
  cancel: Option[Dialog.Cancel] = None,
  other: Option[VdomNode] = None
) {
  def apply(): VdomElement = Dialog.component(this)
}

object Dialog {

  private type Props = Dialog

  case class Message(primary: String, secondary: Option[String] = None)
  case class Cancel(cb: Callback, label: String = "Cancel")
  case class Submit(cb: Callback, label: String, color: ButtonStyle.Color = Button.Color.Blue)

  private def renderFooter(props: Props): VdomElement = {
    <.div(
      Style.flexbox.flex.flexbox.justifyEnd,
      props.other.map(<.div(Style.flexbox.fill, _)),
      props.cancel.map { cancel =>
        val button = Button(
          onClick = cancel.cb,
          tpe = Button.Tpe.Plain(isAutoFocus = true)
        )(cancel.label)
        <.div(Style.flexbox.none.margin.right8, button)
      }, {
        val button = Button(
          style = Button.Style.Full(color = props.submit.color),
          tpe = Button.Tpe.Plain(isAutoFocus = props.cancel.isEmpty),
          onClick = props.submit.cb
        )(props.submit.label)
        <.div(Style.flexbox.none, button)
      }
    )
  }

  private def renderBody(props: Props): VdomElement = {
    <.div(
      <.p(
        Style.fontWeight.medium.fontSize.px15.width.px256,
        props.message.primary
      ),
      props.message.secondary.map { message =>
        <.p(Style.margin.top4, message)
      }
    )
  }

  private def render(props: Props): VdomElement = {
    <.div(
      Style.padding.all16,
      <.div(renderBody(props)),
      <.div(Style.margin.top16, renderFooter(props))
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
