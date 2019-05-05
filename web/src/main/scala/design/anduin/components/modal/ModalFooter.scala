// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.modal

import design.anduin.components.util.ComponentUtils

import design.anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ModalFooter() {
  def apply(children: VdomNode*): VdomElement =
    ModalFooter.component(this)(children: _*)
}

object ModalFooter {

  private type Props = ModalFooter

  private def render(children: PropsChildren) = {
    <.div(
      ComponentUtils.testId(this, "Container"),
      Style.padding.hor20.padding.bottom20,
      children
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_C(render)
    .build
}
