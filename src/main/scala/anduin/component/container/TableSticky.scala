// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.style.Style
// scalastyle:on underscore.import

private[component] final case class TableSticky(
  widths: List[String],
  styles: TagMod,
  head: VdomElement,
  body: VdomElement
) {
  def apply(): VdomElement = TableSticky.component(this)
}

private[component] object TableSticky {

  type Props = TableSticky

  private def render(props: Props): VdomElement = {
    // To simplify implementation, sticky head requires (all - 1) columns to define width
    if (props.widths.count(_.isEmpty) > 1) {
      throw new RuntimeException("Sticky head requires all columns to have width defined")
    }
    <.div(
      <.table(Style.position.sticky.coordinate.top0.zIndex.idx1, props.styles, props.head),
      // 36px is the height of head + 1px border. This will work for a long time
      <.table(^.marginTop := "-37px", props.styles, props.head, props.body)
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
