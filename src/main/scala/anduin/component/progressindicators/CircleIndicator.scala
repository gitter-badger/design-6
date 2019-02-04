// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.progressindicators

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class CircleIndicator(
  size: CircleIndicator.Size = CircleIndicator.Size.Px16
) {
  def apply(): VdomElement = CircleIndicator.component(this)
}

object CircleIndicator {

  private type Props = CircleIndicator

  sealed trait Size {
    protected def px: Int
    final def mod: TagMod = TagMod(^.width := s"${px}px", ^.height := s"${px}px")
  }

  object Size {
    object Px16 extends Size { val px: Int = 16 }
    object Px48 extends Size { val px: Int = 48 }
  }

  private val common: TagMod = TagMod(
    Style.width.pc100.height.pc100,
    Style.position.absolute.position.pinAll,
    Style.border.all.borderRadius.pc100,
    ^.borderWidth := "3px"
  )

  private val animated: VdomElement = {
    <.div(
      common,
      Style.animation.circle.borderColor.transparent,
      ^.borderTopColor := "currentColor"
    )
  }

  private val static: VdomElement = {
    <.div(common, Style.opacity.pc20)
  }

  private def render(props: Props): VdomElement = {
    <.div(Style.position.relative, props.size.mod, static, animated)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
