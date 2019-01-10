// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.progressindicators

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class CircleIndicatorBlock(
  title: Option[String] = None
) {
  def apply(): VdomElement = CircleIndicatorBlock.component(this)
}

object CircleIndicatorBlock {

  private type Props = CircleIndicatorBlock

  private def render(props: Props): VdomElement = {
    <.div(
      Style.height.px256,
      Style.flexbox.flex.flexbox.justifyCenter.flexbox.itemsCenter,
      <.div(
        <.div(
          Style.color.primary4,
          CircleIndicator(size = CircleIndicator.Size.Px48)()
        ),
        props.title.map { title =>
          <.p(Style.margin.top8.fontSize.px16, title)
        }
      )
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
