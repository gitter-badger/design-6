// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.stepper

import anduin.style.{CssVar, Style}
import anduin.component.icon.Icon

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Stepper(
  steps: Seq[Stepper.Step]
) {
  def apply(): VdomElement = Stepper.component(this)
}

object Stepper {

  case class Step(title: String, render: RenderProps => VdomNode)
  case class RenderProps(next: Option[Callback], back: Option[Callback])

  private type Props = Stepper
  case class State(index: Int)

  private sealed trait Status
  private object StatusPast extends Status
  private object StatusPresent extends Status
  private object StatusFuture extends Status
  private def getStatus(state: State, index: Int): Status = index match {
    case x: Int if x < state.index  => StatusPast
    case y: Int if y == state.index => StatusPresent
    case z: Int if z > state.index  => StatusFuture
  }

  private object Dot {
    private def getMods(status: Status) = status match {
      case StatusPast    => (Style.borderColor.success3.backgroundColor.success3.color.white, Icon.NameCheckBold)
      case StatusPresent => (Style.borderColor.primary4.color.primary4, Icon.NameCircle)
      case StatusFuture  => (Style.borderColor.gray3, Icon.NameBlank)
    }
    private val static = TagMod(
      Style.flexbox.none.width.px20.height.px20,
      Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter,
      Style.border.all.borderWidth.px2.borderRadius.pc100
    )

    def render(props: Props, state: State, step: Step): VdomElement = {
      val index = props.steps.indexOf(step)
      val mods = getMods(getStatus(state, index))
      <.div(static, mods._1, Icon(mods._2)())
    }
  }

  private object Line {
    private def getColor(status: Status) = status match {
      case StatusPast    => (Style.backgroundColor.success3, CssVar.Color.success3)
      case StatusPresent => (Style.backgroundColor.primary4, CssVar.Color.primary4)
      case StatusFuture  => (Style.backgroundColor.gray3, CssVar.Color.gray3)
    }

    private def getGradient(first: String, second: String): TagMod = {
      ^.background := s"linear-gradient(to right, $first, $second)"
    }

    private def renderLine(isInvisible: Boolean, color: TagMod): VdomElement = {
      val opacity = TagMod.when(isInvisible)(Style.opacity.pc0)
      <.div(Style.flexbox.fixed, color, opacity, ^.height := "2px")
    }

    def render(props: Props, state: State, step: Step): (VdomElement, VdomElement) = {
      val thisIndex = props.steps.indexOf(step)
      val prevIndex = thisIndex - 1
      val (thisStatus, prevStatus) = (getStatus(state, thisIndex), getStatus(state, prevIndex))
      val (thisColor, prevColor) = (getColor(thisStatus), getColor(prevStatus))
      (
        renderLine(props.steps.headOption.contains(step), getGradient(prevColor._2, thisColor._2)),
        renderLine(props.steps.lastOption.contains(step), thisColor._1)
      )
    }
  }

  private object Head {

    private def renderTitle(props: Props, state: State, step: Step): VdomElement = {
      <.p(
        Style.fontWeight.medium.padding.hor32,
        Style.whiteSpace.preLine.textAlign.center, {
          val isFuture = props.steps.indexOf(step) > state.index
          TagMod.when(isFuture)(Style.color.gray6)
        },
        step.title
      )
    }

    private val upperStyles =
      Style.margin.bottom8.flexbox.flex.flexbox.itemsCenter

    private def renderChild(props: Props, state: State)(step: Step): VdomElement = {
      val lines = Line.render(props, state, step)
      <.div(
        ^.key := step.title,
        <.div(upperStyles, lines._1, Dot.render(props, state, step), lines._2),
        renderTitle(props, state, step)
      )
    }

    private val styles = TagMod(
      Style.flexbox.flex.flexbox.justifyCenter,
      Style.padding.top24.padding.bottom16,
      Style.border.bottom.borderColor.gray3
    )

    def render(props: Props, state: State): VdomElement = {
      <.div(styles, props.steps.toVdomArray(renderChild(props, state)))
    }
  }

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
        Head.render(props, state),
        renderBody(props, state)
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State(0))
    .renderBackend[Backend]
    .build
}
