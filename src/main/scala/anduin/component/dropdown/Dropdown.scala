// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.component.portal.{Popover, PositionBottomLeft}
import anduin.scalajs.downshift.{Downshift, DownshiftRenderProps, DownshiftState, DownshiftStateChanges}

import scala.scalajs.js

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

class Dropdown[A] {

  private val DownshiftA = new Downshift[A]
  private val Target = (new DropdownTarget[A])()
  private val Content = (new DropdownContent[A])()
  private val StateReducer = new DropdownStateReducer[A]

  private type Measurement = Dropdown.Measurement[A]
  private type Changes = DownshiftStateChanges[A]
  private type State = DownshiftState[A]

  def apply(): Props.type = Props

  case class Props(
    // Required
    value: Option[A],
    options: List[Dropdown.Opt[A]],
    onChange: A => Callback,
    renderValue: A => VdomNode,
    // Basic options
    isDisabled: Boolean = false,
    isFullWidth: Boolean = false,
    placeholder: VdomNode = "Select…",
    style: Dropdown.Style = Dropdown.StyleFull,
    // Advanced options
    footer: Option[VdomNode] = None,
    header: Option[VdomNode] = None,
    getFilterValue: A => String = _.toString,
    renderOption: Option[A => VdomNode] = None,
    staticMeasurement: Option[Measurement] = None
  ) {
    def apply(): VdomElement = component(this)
  }

  private[dropdown] case class InnerProps(
    outer: Dropdown[A]#Props,
    downshift: DownshiftRenderProps[A],
    measurement: Dropdown.Measurement[A]
  )

  private class Backend() {

    // This needs to be new for each instance of Dropdown component
    private val Measure = new DropdownMeasure[A]

    private def getMeasurement(props: Props): Measurement = {
      props.staticMeasurement.getOrElse(Measure.get(props))
    }

    private def itemToString(props: Props)(item: A): String =
      if (item == null) "" else props.getFilterValue(item)

    private def onChange(props: Props)(item: A): Unit =
      props.onChange(item).runNow()

    private def stateReducer(state: State, changes: Changes): Changes = {
      val data = StateReducer.Data(this.isInnerClick)
      val input = StateReducer.Input(state, changes, data)
      StateReducer.get(input)
    }

    private def renderChildren(props: Props)(
      downshift: DownshiftRenderProps[A]
    ): raw.React.Node = {
      val measurement = getMeasurement(props)
      val innerProps = InnerProps(props, downshift, measurement)
      val popover = Popover(
        isOpened = innerProps.downshift.isOpen.toOption,
        position = PositionBottomLeft,
        renderTarget = (_, _) => Target(innerProps)(),
        renderContent = _ => Content(innerProps)(),
        isFullWidth = true
      )()
      // can't return Popover directly here. Downshift
      // requires a low-level native tag
      <.div(popover).rawElement
    }

    def render(props: Props): VdomElement = {
      val downshiftProps = new DownshiftA.Props(
        onChange = onChange(props),
        itemToString = itemToString(props),
        stateReducer = stateReducer,
        children = renderChildren(props),
        // ===
        selectedItem = js.defined(props.value match {
          case Some(value) => value
          case None        => null // scalastyle:ignore null
        })
      )
      DownshiftA.component(downshiftProps)
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build
}

object Dropdown {

  case class Opt[A](value: A, isDisabled: Boolean = false)

  case class Measurement[A](
    biggestWidthOption: Option[Opt[A]],
    optionHeight: Option[Int]
  )

  sealed trait Style
  case object StyleFull extends Style
  case object StyleMinimal extends Style

}
