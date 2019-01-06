// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.modal

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

trait ModalSize {
  def width: ModalSize.Width
  def height: ModalSize.Height

  // "width" only has "container" atm
  final def overlay: TagMod = height.overlay
  final def container: TagMod = TagMod(width.container, height.container)
  final def content: TagMod = height.content
  final def header: TagMod = height.header
}

private[modal] object ModalSize {

  sealed trait Width {
    def container: TagMod
  }

  object Width {
    trait Content extends Width { final val container: TagMod = TagMod.empty }

    sealed trait Fixed extends Width {
      def width: TagMod
      final def container: TagMod = TagMod(width, Style.overflow.hiddenX)
    }
    trait Px480 extends Fixed { final val width: TagMod = ^.width := "480px" }
    trait Px600 extends Fixed { final val width: TagMod = ^.width := "600px" }
    trait Px720 extends Fixed { final val width: TagMod = ^.width := "720px" }
    trait Px960 extends Fixed { final val width: TagMod = ^.width := "960px" }
    trait Px1160 extends Fixed { final val width: TagMod = ^.width := "1160px" }
    trait Full extends Fixed { final val width: TagMod = Style.width.pc100 }
  }

  sealed trait Height {
    def overlay: TagMod = TagMod.empty
    def container: TagMod = TagMod.empty
    def header: TagMod = TagMod.empty
    def content: TagMod = TagMod.empty
  }

  object Height {
    trait Content extends Height {
      final override val overlay: TagMod = Style.padding.ver32
    }
    // it's intentional to use overflow.hiddenY instead of autoY here since
    // consumers should only use Full to be able to get parent's height
    trait Full extends Height {
      final override val container: TagMod = TagMod(
        Style.height.pc100.overflow.hiddenY,
        Style.flexbox.flex.flexbox.column
      )
      final override val header: TagMod = Style.flexbox.none
      final override val content: TagMod = Style.flexbox.fixed
    }
  }
}
