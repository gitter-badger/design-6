// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.stepper

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Stepper(
  steps: Seq[Stepper.Step],
  initialStep: Int = 0
) {
  def apply(): VdomElement = Stepper.component(this)
}

object Stepper {

  case class Step(title: String, render: RenderProps => VdomNode)
  case class RenderProps(next: Option[Callback], back: Option[Callback])

  private type Props = Stepper
  case class State(index: Int)

  private class Backend(scope: BackendScope[Props, State]) {

    private def next(props: Props, state: State): Option[Callback] = {
      Option(state.index < props.steps.length - 1).collect {
        case true => scope.setState(State(state.index + 1))
      }
    }

    private def back(state: State): Option[Callback] = {
      Option(state.index > 0).collect {
        case true => scope.setState(State(state.index - 1))
      }
    }

    private def renderBody(props: Props, state: State): VdomElement = {
      <.div(
        Style.padding.all32,
        props.steps.lift(state.index).map { step =>
          val renderProps = RenderProps(next(props, state), back(state))
          step.render(renderProps)
        }
      )
    }

    def render(props: Props, state: State): VdomElement = {
      <.div(
        Style.backgroundColor.white.shadow.blur1Light,
        <.div(
          Style.padding.ver12,
          StepperHeader(
            titles = props.steps.map(_.title),
            current = state.index
          )()
        ),
        <.div(
          Style.border.top.borderColor.gray3,
          renderBody(props, state)
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps(props => State(props.initialStep))
    .renderBackend[Backend]
    .build
}
