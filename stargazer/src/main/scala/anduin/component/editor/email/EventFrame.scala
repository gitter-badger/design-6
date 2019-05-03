// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.email

import org.scalajs.dom.raw.HTMLIFrameElement

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class EventFrame(
  onLoad: Callback
) {
  def apply(): VdomElement = EventFrame.component(this)
}

object EventFrame {

  type Props = EventFrame

  type MessageFrameRef = Ref.Handle[ScalaComponent.RawMounted[EventFrame, Unit, EventFrame.Backend]]

  case class Backend(scope: BackendScope[Props, _]) {

    private val frameRef = Ref[HTMLIFrameElement]

    def setHeight(height: Double): Callback = {
      for {
        frame <- frameRef.get
        _ <- {
          val currentHeight = Option(frame.style).map(_.height).getOrElse("")
          val newHeight = s"${height}px"
          Callback.unless(currentHeight == newHeight) {
            Callback {
              frame.style.height = newHeight
            }
          }
        }
      } yield ()
    }

    def render(props: Props): VdomElement = {
      <.iframe.withRef(frameRef)(
        ^.onLoad --> props.onLoad,
        Style.width.pc100.border.none.padding.all0
      )
    }
  }

  val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build
}
