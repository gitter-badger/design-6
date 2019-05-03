// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.tag

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

sealed trait TagTarget {
  def tag: VdomTag
  def isInteractive: Boolean
}

object TagTarget {

  object None extends TagTarget {
    final def tag: VdomTag = <.div
    final def isInteractive: Boolean = false
  }

  trait Button extends TagTarget {
    def onClick: Callback
    final def tag: VdomTag = <.button(^.tpe := "button", ^.onClick --> onClick)
    final def isInteractive: Boolean = true
  }

  trait Link extends TagTarget {
    def href: String
    final def tag: VdomTag = <.a(^.href := href, Style.textDecoration.hoverNone)
    final def isInteractive: Boolean = true
  }
}
