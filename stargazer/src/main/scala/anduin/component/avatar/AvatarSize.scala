// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.avatar

sealed abstract class AvatarSize(
  val size: Double,
  val fontSize: Double
)

case object Size20 extends AvatarSize(20, 10)
case object Size24 extends AvatarSize(24, 11)
case object Size32 extends AvatarSize(32, 13)
case object Size36 extends AvatarSize(36, 13)
case object Size40 extends AvatarSize(40, 15)
