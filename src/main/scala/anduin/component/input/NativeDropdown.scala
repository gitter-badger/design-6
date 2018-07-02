// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input

import anduin.component.button.ButtonStyle
import anduin.component.icon.Icon
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

case class NativeDropdown(
  value: String,
  options: List[NativeDropdown.Option],
  onChange: String => Callback,
  isDisabled: Boolean = false
) {
  def apply(): VdomElement = NativeDropdown.component(this)
}

object NativeDropdown {
  private type Props = NativeDropdown

  case class Option(
    value: String,
    label: String,
    isDisabled: Boolean = false
  )

  private val icon = <.div(
    Style.margin.verAuto.height.px16.pointerEvents.none,
    Style.position.absolute.coordinate.top0.coordinate.bottom0,
    ^.right := "8px",
    Icon(name = Icon.NameCaretDown)()
  )

  private val selectStyles = ButtonStyle.getStyles(
    color = ButtonStyle.ColorWhite,
    size = ButtonStyle.SizeMedium,
    style = ButtonStyle.StyleFull,
    isSelected = false,
    isFullWidth = true
  )

  private def renderOption(option: Option): VdomElement = <.option(
    ^.key := option.value,
    ^.value := option.value,
    ^.disabled := option.isDisabled,
    option.label
  )

  private class Backend(scope: BackendScope[Props, _]) {

    private def onChange(e: ReactEventFromInput) = {
      for {
        props <- scope.props
        _ <- props.onChange(e.target.value)
      } yield ()
    }

    def render(props: Props): VdomElement = {
      <.div(
        Style.position.relative,
        <.select(
          selectStyles,
          ^.value := props.value,
          ^.onChange ==> onChange,
          ^.disabled := props.isDisabled,
          props.options.toVdomArray(renderOption)
        ),
        icon
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build
}
