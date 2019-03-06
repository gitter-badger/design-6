// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.tag

import anduin.style.Style

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
    final def text: Text = Text(Style.color.white, Style.color.white)
  }
  object Bold {
    object Gray extends Bold {
      def bg: Bg = Bg(sbg.gray7, sbg.hoverGray6.background.activeGray8)
    }
    object Blue extends Bold {
      def bg: Bg = Bg(sbg.blue4, sbg.hoverBlue3.background.activeBlue5)
    }
    object Green extends Bold {
      def bg: Bg = Bg(sbg.green4, sbg.hoverGreen3.background.activeGreen5)
    }
    object Orange extends Bold {
      def bg: Bg = Bg(sbg.orange4, sbg.hoverOrange3.background.activeOrange5)
    }
    object Red extends Bold {
      def bg: Bg = Bg(sbg.red4, sbg.hoverRed3.background.activeRed5)
    }
  }

  object Light {
    object Gray extends TagColor {
      def text: Text = Text(Style.color.gray8, Style.color.gray7)
      def bg: Bg = Bg(sbg.gray3, sbg.hoverGray4.background.activeGray5)
    }
    object Blue extends TagColor {
      def text: Text = Text(Style.color.blue5, Style.color.blue4)
      def bg: Bg = Bg(sbg.blue1, sbg.hoverBlue2.background.activeBlue3)
    }
    object Green extends TagColor {
      def text: Text = Text(Style.color.green5, Style.color.green4)
      def bg: Bg = Bg(sbg.green1, sbg.hoverGreen2.background.activeGreen3)
    }
    object Orange extends TagColor {
      def text: Text = Text(Style.color.orange5, Style.color.orange4)
      def bg: Bg = Bg(sbg.orange1, sbg.hoverOrange2.background.activeOrange3)
    }
    object Red extends TagColor {
      def text: Text = Text(Style.color.red5, Style.color.red4)
      def bg: Bg = Bg(sbg.red1, sbg.hoverRed2.background.activeRed3)
    }
  }
}
