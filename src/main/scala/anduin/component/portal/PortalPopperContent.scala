// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.portal

import anduin.scalajs.popper.Popper
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[component] object PortalPopperContent {
  val staticStyles = TagMod(
    // Ensure Popper will deal with the "real" size
    Style.width.maxContent,
    // Avoid showing the content before Popper positioned it
    // - Fixed to top to avoid page jump if there is focus inside the content
    // - Opacity will be set to 1 after positioned
    Style.position.fixed.position.pinTop.opacity.pc0
  )

  private def onCreatedDefault(data: Popper.Data): Unit = {
    // Show the content (see staticStyles)
    data.instance.popper.style.opacity = "1"
  }

  private def modifiers = new Popper.Modifiers(
    preventOverflow = new Popper.Overflow(Popper.Boundaries.ViewPort),
    offset = new Popper.Offset("0px,4px")
  )

  def getOptions(
    onCreated: Popper.Data => Unit = _ => (),
    position: PortalPosition
  ): Popper.Options = new Popper.Options(
    onCreate = data => { onCreatedDefault(data); onCreated(data) },
    modifiers = modifiers,
    placement = position.popperPlacement
  )

}
