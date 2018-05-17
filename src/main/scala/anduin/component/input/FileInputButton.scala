// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input

import org.scalajs.dom.FileList

import anduin.component.button.{Button, ButtonStyle}
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class FileInputButton(
  color: ButtonStyle.Color = ButtonStyle.ColorWhite,
  size: ButtonStyle.Size = ButtonStyle.SizeMedium,
  style: ButtonStyle.Style = ButtonStyle.StyleFull,
  isFullWidth: Boolean = false,
  isSelected: Boolean = false,
  isDisabled: Boolean = false,
  // Specific behaviours for FileButton
  onChange: FileList => Callback,
  acceptTypes: String = "",
  isMultiple: Boolean = false
) {
  def apply(children: VdomNode*): VdomElement = {
    FileInputButton.component(this)(children: _*)
  }
}

object FileInputButton {

  private val ComponentName = this.getClass.getSimpleName

  private class Backend(scope: BackendScope[FileInputButton, _]) {

    private def onChange(e: ReactEventFromInput): Callback = {
      for {
        _ <- e.preventDefaultCB
        _ <- e.stopPropagationCB
        props <- scope.props
        _ <- props.onChange(e.target.files)
      } yield ()
    }

    def render(props: FileInputButton, children: PropsChildren): VdomElement = {
      if (props.isDisabled) {
        // when disabled we simply swap it with a disabled Button with
        // corresponding styles. This is because the actual input and the
        // element that has style (div) are not the same element, so
        // ":disabled" styles are not triggered
        Button(props.color, props.size, props.style, props.isFullWidth, props.isSelected, isDisabled = true)(children)
      } else {
        // this is the actual input element that opens file browser
        val inputStyles = Style.position.absolute.coordinate.fill.opacity.pc0.width.pc100.height.pc100.cursor.pointer
        val behaviours = TagMod(^.accept := props.acceptTypes, ^.multiple := props.isMultiple, ^.onChange ==> onChange)
        val input = <.input(^.tpe := "file", inputStyles, behaviours)
        // now use the fake div for styling
        val styles = ButtonStyle.getStyles(
          color = props.color,
          size = props.size,
          style = props.style,
          isSelected = props.isSelected,
          isFullWidth = props.isFullWidth
        )
        <.div(Style.position.relative, styles, children, input)
      }
    }
  }

  private val component = ScalaComponent
    .builder[FileInputButton](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
