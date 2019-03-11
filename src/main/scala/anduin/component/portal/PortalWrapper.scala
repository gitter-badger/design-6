// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.portal

import anduin.style.Style
import org.scalajs.dom.raw.HTMLElement

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// When render positioned portals such as Popover or Tooltip we usually need
// to wrap the target inside a wrapper to have a "ref" to it. This trait defines
// the behaviour of that wrapper.
// - In the future we can consider to remove the "trait" here to allow
//   the consumers to define it, but for now these 3 cases should be enough
sealed trait PortalWrapper { def tag: PortalWrapper.Tag }

object PortalWrapper {
  private type Tag = VdomTagOf[HTMLElement]
  object BlockContent extends PortalWrapper { def tag: Tag = <.div(Style.width.maxContent) }
  object BlockFull extends PortalWrapper { def tag: Tag = <.div }
  object Inline extends PortalWrapper { def tag: Tag = <.span }
}
