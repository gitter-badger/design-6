// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.component.portal.{Popover, PositionBottomLeft}
import anduin.scalajs.downshift.Downshift
import scala.scalajs.js

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import scala.scalajs.js.JSConverters._
// scalastyle:on underscore.import

class Dropdown[A] {

  private val DownshiftA = new Downshift[A]
  private val Target = new DropdownTarget[A]
  private val Content = new DropdownContent[A]
  private type Measurement = Dropdown.Measurement[A]

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

  private def renderChildren(props: Props, measurement: Measurement)(
    downshift: DownshiftA.RenderProps
  ): raw.React.Node = {
    val innerProps = DropdownInnerProps[A](props, downshift, measurement)
    val popover = Popover(
      isOpened = downshift.isOpen.toOption,
      position = PositionBottomLeft,
      renderTarget = (_, _) => Target.component(innerProps),
      renderContent = _ => Content.component(innerProps),
      isFullWidth = true
    )()
    <.div(popover).rawElement // Downshift requires native HTML root
  }

  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
  private def stateReducer(
    state: DownshiftA.State,
    changes: DownshiftA.StateChanges
  ): DownshiftA.StateChanges = {
    val _ = state
    if (changes.isOpen.exists(identity) &&
        !changes.tpe.contains(Downshift.StateChangeTypes.changeInput)) {
      val newChanges = new DownshiftA.StateChanges {
        override val inputValue = ""
      }
      // Because Scala.js does not support ES2015 yet and we cannot "copy"
      // a ScalaJS-defined class
      js.Dynamic.global.Object
        .assign(js.Dynamic.literal(), changes, newChanges)
        .asInstanceOf[DownshiftA.StateChanges]
    } else {
      changes
    }
  }

  private def itemToString(props: Props)(item: A): String =
    if (item == null) "" else props.getFilterValue(item)

  private def onChange(props: Props)(item: A): Unit =
    props.onChange(item).runNow()

  private class Backend() {
    // This needs to be new for each instance of Dropdown component
    private val Measure = new DropdownMeasure[A]

    private def getMeasurement(props: Props): Measurement = {
      props.staticMeasurement.getOrElse(Measure.get(props))
    }

    def render(props: Props): VdomElement = {
      val downshiftProps = new DownshiftA.Props(
        onChange = onChange(props),
        itemToString = itemToString(props),
        stateReducer = stateReducer,
        children = renderChildren(props, getMeasurement(props)),
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
