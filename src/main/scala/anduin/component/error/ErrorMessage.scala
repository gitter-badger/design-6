// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.error

import anduin.component.button.Button
import anduin.component.input.textbox.TextBox
import anduin.component.modal.{Modal, ModalBody, ModalFooter}
import anduin.component.toggle.Toggle
import anduin.style.Style
import org.scalajs.dom

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ErrorMessage(exception: Exception) {
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

  private def getDetail(e: Exception): String = dom.window.btoa {
    List(
      dom.document.location.href,
      e.toString,
      e.getStackTrace.map(_.toString).mkString("\r\n")
    ).mkString("\r\n")
  }

  private def renderBody(props: Props, isShowDetail: Boolean): VdomElement = ModalBody()(
    <.p(
      "Please save all work in progress and reload the page. ",
      "If the problem persists, please ",
      <.a(^.href := "https://www.anduintransact.com/contact", "contact support"),
      " and include the error detail in your message."
    ),
    if (isShowDetail) {
      <.div(Style.margin.top16)(
        TextBox(
          tpe = TextBox.Tpe.Area(3),
          value = getDetail(props.exception),
          isReadOnly = true
        )()
      )
    } else {
      EmptyVdom
    }
  )

  private def renderFooter(close: Callback, showDetail: (Boolean, Callback)): VdomNode = ModalFooter()(
    <.div(
      Style.flexbox.flex,
      <.div(Style.flexbox.fill)(
        Button(onClick = showDetail._2)(
          s"${if (showDetail._1) "Hide" else "Show"} error detail"
        )
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

  private def render(props: Props): VdomElement = {
    Modal(
      title = "We encountered an unexpected problem",
      renderContent = close => {
        Toggle(
          render = (toggle, isExpanded) => {
            React.Fragment(
              renderBody(props, isExpanded),
              renderFooter(close, (isExpanded, toggle))
            )
          }
        )()
      },
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
