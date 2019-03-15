// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.portal

import anduin.style.Style
import org.scalajs.dom.raw.HTMLElement

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// When render positioned portals such as Popover or Tooltip we usually need
// to wrap the target inside a wrapper to have a "ref" to it. This trait
// defines the behaviour of that wrapper.
// - In the future we can consider to remove the "trait" here to allow
//   the consumers to define it, but for now these 3 cases should be enough
sealed trait PortalWrapper { def tag: PortalWrapper.Tag }

object PortalWrapper {
  private type Tag = VdomTagOf[HTMLElement]

  // This is the default tag wrapper, as it seems to be the best one we know
  // so far: https://codepen.io/dvkndn/pen/XGzYRy. In short, it:
  // 1. Reports the correct width of short text
  // 2. Allows long text to wrap to new lines
  // 3. Allows both CSS-based truncation and JS-based truncation
  // 4. Works with `layout: auto` tables
  // 5. Be a block element if all of its siblings are also block. Similarly, be
  //    inline if all siblings are inline.
  // The "verticalAlign" is to deal with "overflow: hidden" issue (in CSS-based
  // truncation). See more: https://stackoverflow.com/a/20566810
  object BlockContent extends PortalWrapper {
    def tag: Tag = <.div(Style.display.inlineBlock.verticalAlign.bottom)
  }

  // Mostly to have full width
  object BlockFull extends PortalWrapper { def tag: Tag = <.div }

  // No practical usage so far. Could be useful to have true inline
  object Inline extends PortalWrapper { def tag: Tag = <.span }
}
