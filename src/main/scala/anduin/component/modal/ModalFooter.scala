// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.modal

import anduin.component.util.ComponentUtils

import anduin.style.Style

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
