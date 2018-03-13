// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import org.scalajs.dom
import org.scalajs.dom.raw.MouseEvent

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class ClickOutside(
  offsetTop: Double = 0,
  offsetBottom: Double = 0,
  onClickOutside: Callback
) {
  def apply(children: VdomNode*): ScalaComponent.Unmounted[_, _, _] = ClickOutside.component(this)(children: _*)
}

private[editor] object ClickOutside {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[ClickOutside, _]) extends OnUnmount {

    def render(children: PropsChildren): VdomElement = {
      <.div(children)
    }

    def onWindowClick(e: MouseEvent): Callback = {
      for {
        props <- scope.props
        node <- scope.getDOMNode.map(_.asElement)
        rect = node.getBoundingClientRect()
        (x, y) = (e.clientX, e.clientY)
        (top, left, width, height) = (rect.top, rect.left, rect.width, rect.height)
        isInside = left <= x && x <= (left + width) && (top + props.offsetTop) <= y && y <= (top + props.offsetBottom + height)
        _ <- Callback.when(!isInside) {
          props.onClickOutside
        }
      } yield ()
    }
  }

  private val component = ScalaComponent
    .builder[ClickOutside](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .configure(
      EventListener[MouseEvent].install("click", _.backend.onWindowClick, _ => dom.window)
    )
    .build
}
