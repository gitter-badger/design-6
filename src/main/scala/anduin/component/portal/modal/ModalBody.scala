// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal.modal

// scalastyle:off underscore.import
import anduin.style.Style
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ModalBody() {
  def apply(children: VdomNode*): VdomElement =
    ModalBody.component(this)(children: _*)
}

object ModalBody {

  private type Props = ModalBody

  private def render(children: PropsChildren) = {
    <.div(Style.padding.all20, children)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_C(render)
    .build
}
