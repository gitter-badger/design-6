// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.dropdown

import design.anduin.components.popover.PopoverContent
import design.anduin.components.portal.PortalPosition
import design.anduin.facades.util.ScalaJSUtils
import org.scalajs.dom.raw.HTMLElement

import scala.scalajs.js

// scalastyle:off underscore.import
import design.anduin.facades.downshift._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

class Dropdown[A] {

  private val DownshiftA = new Downshift[A]
  private val Target = (new DropdownTarget[A])()
  private val Content = (new DropdownContent[A])()
  private val StateReducer = new DropdownStateReducer[A]

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
    staticMeasurement: Option[Dropdown.Measurement[A]] = None
  ) {
    def apply(): VdomElement = component(this)
  }

  private final class Backend(backendScope: BackendScope[Props, Unit]) {

    val _ = backendScope
    private val targetRef = Ref[HTMLElement]

    // This needs to be new for each instance of Dropdown component
    private val Measure = new DropdownMeasure[A]

    private def itemToString(props: Props)(item: js.|[A, Null]): String =
      ScalaJSUtils.jsNullToOption(item).fold("")(props.getValueString)

    private def onChange(props: Props)(item: A): Unit =
      props.onChange(item).runNow()

    private def renderChildren(props: Props)(
      downshift: DownshiftRenderProps[A]
    ): raw.React.Node = {
      val measurement = props.staticMeasurement.getOrElse(Measure.get(props))
      <.div(
        <.div.withRef(targetRef)(Target(props, downshift, measurement)()),
        TagMod.when(downshift.isOpen)(
          PopoverContent(
            targetRef = targetRef,
            position = PortalPosition.BottomLeft,
            /* Downshift's condition check to close menu doesn't work with
             * React's Portal so we need to close the menu ourselves.
             * - See DropdownStateReducer > preventDefaultOuterClick */
            onOverlayClick = Some(Callback(downshift.closeMenu())),
            /* Keep the focus at the button so Downshift's keyboard event
             * handler could work properly */
            isAutoFocus = false
          )(Content(props, downshift, measurement)())
        )
      ).rawNode
    }

    def render(props: Props): VdomElement = {
      val downshiftProps = new DownshiftA.Props(
        onChange = onChange(props),
        itemToString = itemToString(props),
        stateReducer = StateReducer.get,
        children = renderChildren(props),
        // ===
        defaultHighlightedIndex = js.undefined,
        // Avoid inputValue being set to selectedItem in first render
        // - See also: DropdownStateReducer > clearInputValue
        initialInputValue = js.defined(""),
        // However we don't control inputValue in subsequent renders
        inputValue = js.undefined,
        onInputValueChange = js.undefined,
        // We do control selected item, using props.value:
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
