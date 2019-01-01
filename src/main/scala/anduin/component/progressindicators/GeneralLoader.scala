// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.progressindicators

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class GeneralLoader() {
  def apply(): VdomElement = GeneralLoader.component(this)
}

object GeneralLoader {

  private type Props = GeneralLoader

  private val render: VdomElement = {
    <.div(
      ^.cls := "loader-general",
      (0 to 4).toTagMod(_ => <.span(^.cls := "item"))
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderStatic(render)
    .build
}


