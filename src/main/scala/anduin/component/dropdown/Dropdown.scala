// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.component.popover.PopoverContent
import anduin.component.portal.PortalPosition
import org.scalajs.dom.raw.HTMLElement

import scala.scalajs.js

// scalastyle:off underscore.import
import anduin.scalajs.downshift._

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
    options: Seq[Dropdown.Opt[A]],
    onChange: A => Callback,
    getValueString: A => String,
    // Basic options
    id: Option[String] = None,
    isDisabled: Boolean = false,
    isFullWidth: Boolean = false,
    placeholder: VdomNode = "",
    style: Dropdown.Style = Dropdown.StyleFull,
    footer: Option[VdomNode] = None,
    header: Option[VdomNode] = None,
    // Advanced options
    getFilterValue: Option[A => String] = None,
    renderValue: Option[A => VdomNode] = None,
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

  private final class Backend(backendScope: BackendScope[Props, Unit]) {

    val _ = backendScope
    private val targetRef = Ref[HTMLElement]

    // This variable will be set immediately before Downshift's stateReducer
    // (and reset after that) to help Downshift detects whether the click is
    // inside or outside the Dropdown's area.
    // - Learn more in the comment in StateReducer > preventClosing
    private var isInnerClick: Boolean = false // scalastyle:ignore var.field
    private val isInnerClickWrapper = TagMod(
      ^.onMouseDown --> Callback(isInnerClick = true),
      ^.onMouseUp --> Callback(isInnerClick = false).delayMs(100).toCallback
    )

    // This needs to be new for each instance of Dropdown component
    private val Measure = new DropdownMeasure[A]

    private def getMeasurement(props: Props): Measurement = {
      props.staticMeasurement.getOrElse(Measure.get(props))
    }

    private def itemToString(props: Props)(item: A): String =
      if (item == null) "" else props.getValueString(item)

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
      <.div(
        <.div.withRef(targetRef)(Target(innerProps)()),
        TagMod.when(innerProps.downshift.isOpen.contains(true)) {
          PopoverContent(
            targetRef = targetRef,
            // Downshift will handle this outside click for us
            onOverlayClick = None,
            position = PortalPosition.BottomLeft,
            // Avoid auto focus to the popover because all (keyboard)
            // navigation should happen at the target button, which requires
            // it to be focused instead.
            isAutoFocus = false
          )(<.div(isInnerClickWrapper, Content(innerProps)()))
        }
      ).rawElement
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
