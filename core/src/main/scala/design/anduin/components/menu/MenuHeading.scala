// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.menu

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class MenuHeading() {
  def apply(children: VdomNode*): VdomElement = {
    MenuHeading.component(this)(children: _*)
  }
}

object MenuHeading {

  private type Props = MenuHeading

  private def render(children: PropsChildren): VdomElement = {
    <.p(
      Style.fontSize.px10.fontWeight.semiBold.color.gray6,
      Style.padding.hor16.padding.top16.textTransform.uppercase,
      children
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_C(render)
    .build
}
