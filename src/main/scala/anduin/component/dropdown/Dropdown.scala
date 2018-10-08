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
  private val Target = (new DropdownTarget[A])()
  private val Content = (new DropdownContent[A])()

  def apply(): Props.type = Props

  case class Props(
    // Basic
    value: Option[A],
    options: List[Dropdown.Opt[A]],
    onChange: A => Callback,
    isDisabled: Boolean = false,
    // Common rendering option
    footer: Option[VdomNode] = None,
    header: Option[VdomNode] = None,
    style: Dropdown.Style = Dropdown.StyleFull,
    isFullWidth: Boolean = false,
    // Custom rendering
    valueToString: A => String = _.toString,
    renderValue: A => VdomNode = _.toString,
    placeholder: VdomNode = "Select…",
    renderOption: DropdownOpt.Render[A] = DropdownOpt.defaultRender[A]
  ) {
    def apply(): VdomElement = component(this)
  }

  private def renderChildren(outerProps: Props)(
    downshiftProps: DownshiftA.RenderProps
  ): raw.React.Node = {
    val popover = Popover(
      isOpened = downshiftProps.isOpen.toOption,
      position = PositionBottomLeft,
      renderTarget = (_, _) => Target(outerProps, downshiftProps)(),
      renderContent = _ => Content(outerProps, downshiftProps)(),
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

  private def itemToString(outerProps: Props)(item: A): String =
    if (item == null) "" else outerProps.valueToString(item)

  private def onChange(outerProps: Props)(item: A): Unit =
    outerProps.onChange(item).runNow()

  private def render(outerProps: Props): VdomElement = {
    val props = new DownshiftA.Props(
      onChange = onChange(outerProps),
      itemToString = itemToString(outerProps),
      defaultSelectedItem = outerProps.value.orUndefined,
      stateReducer = stateReducer,
      children = renderChildren(outerProps)
    )
    DownshiftA.component(props)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}

object Dropdown {

  case class Opt[A](value: A, isDisabled: Boolean = false)

  sealed trait Style
  case object StyleFull extends Style
  case object StyleMinimal extends Style
}
