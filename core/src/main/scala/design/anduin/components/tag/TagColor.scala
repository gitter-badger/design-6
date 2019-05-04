// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.tag

import design.anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

trait TagColor {
  def text: TagColor.Text
  def bg: TagColor.Bg
}

object TagColor {

  case class Text(primary: TagMod, secondary: TagMod)
  case class Bg(rest: TagMod, interactive: TagMod)

  private val sbg = Style.background

  sealed trait Bold extends TagColor {
    final def text: Text = Text(Style.color.gray0, Style.color.gray0)
  }
  object Bold {
    object Gray extends Bold {
      def bg: Bg = Bg(sbg.gray7, sbg.hoverGray6.background.activeGray8)
    }
    object Primary extends Bold {
      def bg: Bg = Bg(sbg.primary4, sbg.hoverPrimary3.background.activePrimary5)
    }
    object Success extends Bold {
      def bg: Bg = Bg(sbg.success4, sbg.hoverSuccess3.background.activeSuccess5)
    }
    object Warning extends Bold {
      def bg: Bg = Bg(sbg.warning4, sbg.hoverWarning3.background.activeWarning5)
    }
    object Danger extends Bold {
      def bg: Bg = Bg(sbg.danger4, sbg.hoverDanger3.background.activeDanger5)
    }
  }

  object Light {
    object Gray extends TagColor {
      def text: Text = Text(Style.color.gray8, Style.color.gray7)
      def bg: Bg = Bg(sbg.gray3, sbg.hoverGray4.background.activeGray5)
    }
    object Primary extends TagColor {
      def text: Text = Text(Style.color.primary5, Style.color.primary4)
      def bg: Bg = Bg(sbg.primary1, sbg.hoverPrimary2.background.activePrimary3)
    }
    object Success extends TagColor {
      def text: Text = Text(Style.color.success5, Style.color.success4)
      def bg: Bg = Bg(sbg.success1, sbg.hoverSuccess2.background.activeSuccess3)
    }
    object Warning extends TagColor {
      def text: Text = Text(Style.color.warning5, Style.color.warning4)
      def bg: Bg = Bg(sbg.warning1, sbg.hoverWarning2.background.activeWarning3)
    }
    object Danger extends TagColor {
      def text: Text = Text(Style.color.danger5, Style.color.danger4)
      def bg: Bg = Bg(sbg.danger1, sbg.hoverDanger2.background.activeDanger3)
    }
  }
}
