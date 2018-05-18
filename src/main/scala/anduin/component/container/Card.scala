// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

import japgolly.scalajs.react.component.Scala.Unmounted

import anduin.component.util.ComponentUtils

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Card(
  content: VdomElement
) {
  def apply(): Unmounted[_, _, _] = Card.component(this)
}

object Card {

  private val ComponentName = ComponentUtils.name(this)

  private type Props = Card

  private case class Backend(scope: BackendScope[Props, _]) {

    def render(props: Props): VdomElement = {
      <.div(
        ^.cls := "card",
        props.content
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
