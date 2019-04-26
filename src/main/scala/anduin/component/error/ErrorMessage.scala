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

  private val supportLink = "https://www.anduintransact.com/contact"

  private val renderBody: VdomElement = ModalBody()(
    <.p(
      "Please save all work in progress and reload the page. ",
      "If the problem persists, please ",
      <.a(^.href := supportLink, ^.target.blank, "contact support"),
      " and include the error detail in your message."
    )
  )

  private def getDetail(throwable: Throwable): String =
    s"Please give the following to our support at $supportLink:$break" +
      dom.window.btoa {
        List(
          dom.document.location.href,
          throwable.toString,
          throwable.getStackTrace.map(_.toString).mkString(break)
        ).mkString(break)
      }

  private def renderFooter(props: Props, close: Callback): VdomNode = ModalFooter()(
    <.div(
      Style.flexbox.flex,
      <.div(Style.flexbox.fill)(
        ClipboardCopy(
          render = (copy, isJustCopied) => {
            val label = if (isJustCopied) "Copied to clipboard" else "Copy error detail"
            Button(onClick = copy)(label)
          },
          text = getDetail(props.throwable)
        )()
      ),
      <.div(Style.flexbox.none.margin.right8)(Button(onClick = close)("Reload later")),
      <.div(Style.flexbox.none)(
        Button(
          style = Button.Style.Full(color = Button.Color.Primary),
          onClick = Callback(dom.document.location.reload())
        )("Reload now")
      )
    )
  )

  private def render(props: Props): VdomElement = {
    Modal(
      title = "We encountered an unexpected problem",
      renderContent = close => React.Fragment(renderBody, renderFooter(props, close)),
      isClosable = None,
      defaultIsOpened = true,
      beforeClose = Callback.alert(
        """
          |Please reload the page as soon as possible. You might lose any
          |unsaved changes if you continue using without reloading the page.
        """.stripMargin
      )
    )()
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
