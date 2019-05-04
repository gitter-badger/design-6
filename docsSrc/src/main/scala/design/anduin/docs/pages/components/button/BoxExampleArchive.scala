// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.docs.pages.components.button

import design.anduin.components.button.Button
import design.anduin.components.modal.{Modal, ModalBody, ModalFooterWCancel}
import design.anduin.docs.components.ExampleSimple
import design.anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class BoxExampleArchive() {
  def apply(): VdomElement = BoxExampleArchive.component(this)
}

object BoxExampleArchive {

  private type Props = BoxExampleArchive

  private def renderContent(closeModal: Callback): VdomElement = {
    React.Fragment(
      ModalBody()(
        <.p(
          Style.fontSize.px17.fontWeight.semiBold,
          "Are you sure want to archive this deal?"
        ),
        <.ul(
          Style.margin.top8,
          <.li("Archived deals can be restored from your dashboard"),
          <.li("All of deal's participants will be notified")
        )
      ),
      ModalFooterWCancel(closeModal)(
        Button(
          style = Button.Style.Full(color = Button.Color.Danger),
          onClick = Callback.alert("Deal archived") >> closeModal
        )("Archive")
      )
    )
  }

  private def render(props: Props): VdomElement = {
    ExampleSimple()(
      Modal(
        renderTarget = open => Button(onClick = open)("Archive Deal"),
        renderContent = renderContent
      )()
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
