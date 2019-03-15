// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.portal

import anduin.scalajs.popper.PopperPlacement

// scalastyle:off underscore.import
import anduin.scalajs.popper.PopperPlacement._
// scalastyle:on underscore.import

abstract class PortalPosition(
  private[component] val popperPlacement: PopperPlacement
)

object PortalPosition {
  object TopLeft extends PortalPosition(TopStart)
  object TopCenter extends PortalPosition(TopMiddle)
  object TopRight extends PortalPosition(TopEnd)
  object RightTop extends PortalPosition(RightStart)
  object RightCenter extends PortalPosition(RightMiddle)
  object RightBottom extends PortalPosition(RightEnd)
  object BottomLeft extends PortalPosition(BottomStart)
  object BottomCenter extends PortalPosition(BottomMiddle)
  object BottomRight extends PortalPosition(BottomEnd)
  object LeftTop extends PortalPosition(LeftStart)
  object LeftCenter extends PortalPosition(LeftMiddle)
  object LeftBottom extends PortalPosition(LeftEnd)
}
