// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.error

import anduin.component.button.Button
import anduin.component.clipboard.ClipboardCopy
import anduin.component.modal.{Modal, ModalBody, ModalFooter}
import anduin.style.Style
import org.scalajs.dom

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ErrorMessage(throwable: Throwable) {
  def apply(): VdomElement = ErrorMessage.component(this)
  def renderIntoBody(): Unit = {
    val container = dom.document.createElement("div")
    dom.document.body.appendChild(container)
    ErrorMessage.component(this).renderIntoDOM(container)
    ()
  }
}

object ErrorMessage {

  private type Props = ErrorMessage

  private val break = "\r\n"
  private var isShowing = false // scalastyle:ignore var.field

  private def getDetail(throwable: Throwable): String = dom.window.btoa {
    List(
      dom.document.location.href,
      throwable.toString,
      throwable.getStackTrace.map(_.toString).mkString(break)
    ).mkString(break)
  }

  private def renderBody(props: Props): VdomElement = ModalBody()(
    <.p(
      "Please save all work in progress and reload the page.",
      " If the problem persists, please ",
      <.a(
        ^.href := "mailto:support@anduintransact.com" +
          "?subject=Help%20and%20support" +
          s"&body=Error%20detail%3A%20${getDetail(props.throwable)}%0A",
        ^.target.blank,
        "contact support"
      ),
      " and include the error detail in your message."
    )
  )

  private def renderFooter(props: Props, close: Callback): VdomNode = ModalFooter()(
    <.div(
      Style.flexbox.flex,
      <.div(Style.flexbox.fill)(
        ClipboardCopy(
          render = (copy, isJustCopied) => {
            Button(onClick = copy)(
              if (isJustCopied) "Copied to clipboard" else "Copy error detail"
            )
          },
          text = "Please give the following to our support at " +
            s"https://www.anduintransact.com/contact:$break" +
            s"${getDetail(props.throwable)}\n"
        )()
      ),
      <.div(Style.flexbox.none.margin.right8)(
        Button(onClick = close)("Reload later")
      ),
      <.div(Style.flexbox.none)(
        Button(
          style = Button.Style.Full(color = Button.Color.Primary),
          onClick = Callback(dom.document.location.reload())
        )("Reload now")
      )
    )
  )

  private def render(props: Props): VdomNode = {
    if (!isShowing) {
      Modal(
        title = "We encountered an unexpected problem",
        renderContent = close => {
          React.Fragment(renderBody(props), renderFooter(props, close))
        },
        isClosable = None,
        defaultIsOpened = true,
        beforeClose = Callback(isShowing = false) >> Callback.alert(
          """
            |Please reload the page as soon as possible. You might lose any
            |unsaved changes if you continue using without reloading the page.
          """.stripMargin
        )
      )()
    } else {
      EmptyVdom
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .componentDidMount(_ => Callback(isShowing = true))
    .build
}
