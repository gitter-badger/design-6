// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.avatar

abstract class AvatarSize(val size: Double)

case object SmallSize extends AvatarSize(20)
case object MediumSize extends AvatarSize(24)
case object DefaultSize extends AvatarSize(32)
case object LargeSize extends AvatarSize(40)
