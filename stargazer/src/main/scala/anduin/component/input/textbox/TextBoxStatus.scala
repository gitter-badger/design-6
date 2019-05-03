// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.textbox

import anduin.component.icon.Icon
import anduin.component.progressindicators.CircleIndicator
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

sealed trait TextBoxStatus {
  def node: Option[VdomNode]
  def borderColor: TagMod
}

object TextBoxStatus {
  trait Busy extends TextBoxStatus {
    def node: Option[VdomNode] = Some(<.div(Style.color.primary4, CircleIndicator()()))
    def borderColor: TagMod = Style.borderColor.gray4
  }
  trait Valid extends TextBoxStatus {
    def node: Option[VdomNode] = Some(<.div(Style.color.success4, Icon(Icon.Glyph.Check)()))
    def borderColor: TagMod = Style.borderColor.success4
  }
  trait Invalid extends TextBoxStatus {
    def node: Option[VdomNode] = None
    def borderColor: TagMod = Style.borderColor.danger4
  }
  trait None extends TextBoxStatus {
    def node: Option[VdomNode] = None
    def borderColor: TagMod = Style.borderColor.gray4
  }
}
