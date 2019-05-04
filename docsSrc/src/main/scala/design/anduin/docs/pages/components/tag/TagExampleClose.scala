// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.docs.pages.components.tag

import design.anduin.components.button.Button
import design.anduin.components.tag.Tag
import design.anduin.style.Style

import scala.util.Random

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class TagExampleClose() {
  def apply(): VdomElement = TagExampleClose.component(this)
}

object TagExampleClose {

  private type Props = TagExampleClose

  private case class State(labels: List[String])

  private def getRandomString: String =
    Random.alphanumeric.take(3).mkString

  private def getRandom: String =
    s"$getRandomString@$getRandomString.com"

  private class Backend(scope: BackendScope[Props, State]) {

    private def remove(state: State, label: String): Callback =
      scope.modState(_.copy(labels = state.labels.filter(_ != label)))

    private def renderTag(state: State)(label: String): VdomElement = {
      val tag = Tag(close = Some(remove(state, label)))(label)
      <.div(^.key := label, Style.margin.right8.margin.bottom8, tag)
    }

    private def add(state: State): Callback =
      scope.modState(_.copy(labels = state.labels :+ getRandom))

    def render(state: State): VdomElement = {
      <.div(
        <.div(
          Style.flexbox.flex.flexbox.wrap,
          state.labels.toVdomArray(renderTag(state))
        ),
        <.div(
          Button(onClick = add(state))("Add random email")
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State(List(getRandom, getRandom)))
    .renderBackend[Backend]
    .build
}
