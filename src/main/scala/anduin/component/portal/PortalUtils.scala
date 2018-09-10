// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// - These are common functionality for Portal consumers (e.g. Modal, Popover,
//   Tooltip) but should not be inside PortalWrapper (whose main focus is state
//   management).
// - Think of these like plugins for Portal. Currently we have:
//   1. Closable
//   2. BodyOverflow
//   3. Detach
//   4. Auto positioning by Popper
object PortalUtils {

  private val body = dom.document.body

  // Closable ===

  case class IsClosable(onEsc: Boolean, onOutsideClick: Boolean)

  val defaultIsClosable = Some(PortalUtils.IsClosable(onEsc = true, onOutsideClick = true))

  private def closeIfEsc(close: Callback)(event: ReactKeyboardEvent) = {
    Callback.when(event.keyCode == KeyCode.Escape) { close }
  }

  private def closeIfOutside(close: Callback)(event: ReactMouseEvent) = {
    Callback.when(event.target == event.currentTarget) { close }
  }

  // NOTE: in order for "closeOnEsc" to work, there should be an interact-able
  // child (i.e. tabIndex != -1). Preferably autoFocus too.
  private[portal] def getClosableMods(isClosableOpt: Option[IsClosable], close: Callback): TagMod = {
    isClosableOpt.fold(TagMod.empty) { isClosable =>
      TagMod(
        TagMod.when(isClosable.onOutsideClick) { ^.onClick ==> closeIfOutside(close) },
        TagMod.when(isClosable.onEsc) { ^.onKeyDown ==> closeIfEsc(close) }
      )
    }
  }

  // Detach into DOM ===

  // This is an advanced feature to keep an instance lives longer than its
  // parent (i.e. prevent unmount with parent).
  // - Should be called in parent's willUnmount
  // - The Callback in renderContent is to remove the instance completely
  //   (i.e. unmount and remove the container)
  private[portal] def detach(renderContent: Callback => VdomElement): Callback = Callback {
    val container = dom.document.createElement("div")
    body.appendChild(container)
    val close = Callback {
      ReactDOM.unmountComponentAtNode(container)
      body.removeChild(container)
    }
    val content = renderContent(close)
    content.renderIntoDOM(container)
  }
}
