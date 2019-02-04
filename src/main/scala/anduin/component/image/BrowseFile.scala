// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.image

import org.scalajs.dom.raw.File

import anduin.component.icon.Icon
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[image] final case class BrowseFile(
  onDidSelect: File => Callback
) {
  def apply(children: VdomNode*): VdomElement = BrowseFile.component(this)(children: _*)
}

private[image] object BrowseFile {

  private type Props = BrowseFile

  private case class State(hover: Boolean = false)

  private case class Backend(scope: BackendScope[Props, State]) {

    private def onChange(props: Props)(e: ReactEventFromInput) = {
      e.extract(_.target) { t =>
        val files = t.files
        val fileList = (0 until files.length).map(files(_))
        Callback.traverseOption(fileList.headOption) { file =>
          props.onDidSelect(file)
        }
      }
    }

    private def onMouseEnter = scope.modState(_.copy(hover = true))

    private def onMouseLeave = scope.modState(_.copy(hover = false))

    def render(props: Props, state: State, children: PropsChildren): VdomElement = {
      <.div(
        Style.position.relative.width.pc100.height.pc100.flexbox.flex.flexbox.justifyCenter.flexbox.itemsCenter,
        ^.onMouseEnter --> onMouseEnter,
        ^.onMouseLeave --> onMouseLeave,
        if (state.hover) {
          <.span(
            Style.width.pc100.height.pc100.flexbox.flex.flexbox.justifyCenter.flexbox.itemsCenter.background.white,
            Icon(name = Icon.Glyph.Plus, size = Icon.Size.Px32)()
          )
        } else {
          children
        },
        // Input file
        <.input.file(
          Style.position.absolute.position.pinAll.opacity.pc0.width.pc100.height.pc100.cursor.pointer,
          ^.accept := "image/png, image/jpeg",
          ^.multiple := false,
          ^.onChange ==> onChange(props)
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State())
    .renderBackendWithChildren[Backend]
    .build
}
