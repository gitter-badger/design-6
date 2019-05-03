// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.progressindicators

import anduin.component.util.ComponentUtils
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class BlockIndicator(
  title: Option[String] = None,
  isFullHeight: Boolean = false,
  indicator: VdomElement = BlockIndicator.defaultIndicator
) {
  def apply(): VdomElement = BlockIndicator.component(this)
}

object BlockIndicator {

  private type Props = BlockIndicator

  private val defaultIndicator = CircleIndicator(size = CircleIndicator.Size.Px48)()

  private def render(props: Props): VdomElement = {
    <.div(
      ComponentUtils.testId(this, "Loading"),
      if (props.isFullHeight) Style.height.pc100 else Style.height.px256,
      Style.flexbox.flex.flexbox.column.flexbox.justifyCenter.flexbox.itemsCenter,
      <.div(Style.color.primary4, props.indicator),
      props.title.map { title =>
        <.p(Style.margin.top12.fontSize.px15.color.gray6, title)
      }
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
