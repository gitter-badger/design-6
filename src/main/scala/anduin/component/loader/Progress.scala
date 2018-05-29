// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.loader

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Progress(
  value: Option[Double] = None,
  max: Double = 100
) {
  def apply(): VdomElement = Progress.component(this)
}

object Progress {

  type Props = Progress

  private def renderValue(props: Props): VdomElement = {
    val styles = Style.backgroundColor.primary3.height.pc100
    props.value.fold {
      <.div(styles, Style.width.pc30.animation.translateX330) // inderterminate
    } { value =>
      <.div(styles, ^.width := s"${value / props.max * 100}%") // determinate
    }
  }

  private def render(props: Props): VdomElement = {
    // we don't use the native <progress> here due to difficult in customization using atomic CSS
    <.div(Style.backgroundColor.gray3.overflow.hidden, ^.height := "4px", renderValue(props))
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
