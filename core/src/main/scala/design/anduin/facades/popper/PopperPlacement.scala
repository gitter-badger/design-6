// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.facades.popper

sealed trait PopperPlacement { val raw: String }

object PopperPlacement {

  sealed abstract class Auto(val raw: String) extends PopperPlacement
  object AutoStart extends Auto("auto-start")
  object AutoMiddle extends Auto("auto")
  object AutoEnd extends Auto("auto-end")

  sealed abstract class Top(val raw: String) extends PopperPlacement
  object TopStart extends Top("top-start")
  object TopMiddle extends Top("top")
  object TopEnd extends Top("top-end")

  sealed abstract class Right(val raw: String) extends PopperPlacement
  object RightStart extends Right("right-start")
  object RightMiddle extends Right("right")
  object RightEnd extends Right("right-end")

  sealed abstract class Bottom(val raw: String) extends PopperPlacement
  object BottomEnd extends Bottom("bottom-end")
  object BottomMiddle extends Bottom("bottom")
  object BottomStart extends Bottom("bottom-start")

  sealed abstract class Left(val raw: String) extends PopperPlacement
  object LeftEnd extends Left("left-end")
  object LeftMiddle extends Left("left")
  object LeftStart extends Left("left-start")

  // @formatter:off
  private val all = List(
    AutoStart, AutoMiddle, AutoEnd,
    TopStart, TopMiddle, TopEnd,
    RightStart, RightMiddle, RightEnd,
    BottomEnd, BottomMiddle, BottomStart,
    LeftEnd, LeftMiddle, LeftStart
  )
  // @formatter:on

  def fromString(string: String): Option[PopperPlacement] =
    all.find(_.raw == string)
}
