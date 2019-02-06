// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input

import org.scalajs.dom.FileList

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class FileMenuItemInput(
  onChange: FileList => Callback,
  shouldClearAfterChanged: Boolean = false,
  acceptTypes: String = "",
  isMultiple: Boolean
) {
  def apply(children: VdomNode*): VdomElement = {
    FileMenuItemInput.component(this)(children: _*)
  }
}

object FileMenuItemInput {

  private type Props = FileMenuItemInput

  private class Backend(scope: BackendScope[Props, _]) {

    private def onChange(e: ReactEventFromInput): Callback = {
      for {
        props <- scope.props
        _ <- props.onChange(e.target.files)
        _ <- Callback { e.target.value = "" }.when(props.shouldClearAfterChanged)
      } yield ()
    }

    lazy val menuStyles = TagMod(
      Style.color.gray8.lineHeight.px16,
      Style.flexbox.flex.flexbox.itemsCenter.padding.ver8.padding.hor16,
      Style.color.hoverWhite.color.activeWhite,
      Style.background.hoverBlue4.background.activeBlue5,
      Style.position.relative.width.pc100.textAlign.left
    )

    def render(props: Props, children: PropsChildren): VdomElement = {
      val input = <.input(
        ^.tpe := "file",
        Style.position.absolute.position.pinAll.opacity.pc0.width.pc100.height.pc100.cursor.pointer,
        ^.accept := props.acceptTypes,
        ^.multiple := props.isMultiple,
        ^.onChange ==> onChange
      )
      // Fake a menu item
      <.div(^.tpe := "button", menuStyles, children, input)
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
