// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.email

import org.scalajs.dom.raw.{HTMLDocument, HTMLIFrameElement}

import anduin.scalajs.resizeobserver.ResizeObserver

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[email] final case class EmailFrame(
  content: String
) {
  def apply(): VdomElement = EmailFrame.component(this)
}

private[email] object EmailFrame {

  private type Props = EmailFrame

  private case class Backend(scope: BackendScope[Props, _]) {

    private var resizeObserverOpt: Option[ResizeObserver] = None // scalastyle:ignore var.field

    private val eventFrameRef = Ref.toScalaComponent(EventFrame.component)

    def componentDidMount(): Callback = {
      for {
        frameRef <- eventFrameRef.get
        _ <- writeContent()
        _ <- Callback.traverseOption(ReactDOM.findDOMNode(frameRef.raw)) {
          _.node match {
            case frame: HTMLIFrameElement =>
              Callback {
                val resizeObserver = ResizeObserver((_, _) => {
                  recalculateContentSize()
                })
                resizeObserver.observe(frame.contentDocument.firstElementChild)
                resizeObserverOpt = Some(resizeObserver)
              }
            case _ => Callback.empty
          }
        }
      } yield ()
    }

    def componentDidUpdate(): Callback = {
      writeContent()
    }

    def componentWillUnmount(): Callback = {
      Callback.traverseOption(resizeObserverOpt) { resizeObserver =>
        Callback {
          resizeObserver.disconnect()
        }
      }
    }

    private def recalculateContentSize() = {
      for {
        frameRef <- eventFrameRef.get
        _ <- Callback.traverseOption(ReactDOM.findDOMNode(frameRef.raw)) {
          _.node match {
            case frame: HTMLIFrameElement =>
              Callback {
                val doc = frame.contentDocument.documentElement
                org.scalajs.dom.window.console.log(doc.scrollHeight)

                frame.style.height = s"${doc.scrollHeight}px"
              }
            case _ => Callback.empty
          }
        }
      } yield ()
    }

    private def writeContent() = {
      for {
        props <- scope.props
        frameRef <- eventFrameRef.get
        _ <- Callback.traverseOption(ReactDOM.findDOMNode(frameRef.raw)) {
          _.node match {
            case frame: HTMLIFrameElement =>
              @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
              val doc = frame.contentDocument.asInstanceOf[HTMLDocument]
              Callback {
                doc.open()
                doc.write(s"<!DOCTYPE html>${formatEmailContent(props)}")
                doc.close()
              }
            case _ => Callback.empty
          }
        }
      } yield ()
    }

    private def formatEmailContent(props: Props) = {
      props.content
    }

    def render(): VdomElement = {
      eventFrameRef.component(
        EventFrame()
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .componentDidMount(_.backend.componentDidMount())
    .componentDidUpdate(_.backend.componentDidUpdate())
    .componentWillUnmount(_.backend.componentWillUnmount())
    .build
}
