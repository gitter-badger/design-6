// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.email

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class EventFrame() {
  def apply(): VdomElement = EventFrame.component(this)
}

object EventFrame {

  type Props = EventFrame

  type MessageFrameRef = Ref.Handle[ScalaComponent.RawMounted[EventFrame, Unit, EventFrame.Backend]]

  case class Backend(scope: BackendScope[Props, _]) {

    def render(): VdomElement = {
      <.iframe(
        ^.width := "100%",
        ^.border := "0",
        ^.padding := "0"
      )
    }
  }

  val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build
}
