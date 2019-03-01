// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.field

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Field(
  layout: Field.Layout,
  id: String,
  label: Option[String] = None,
  help: Option[VdomNode] = None,
  desc: Option[String] = None,
  error: Option[String] = None,
  isRequired: Boolean = false
) {
  def apply(children: VdomNode*): VdomElement = {
    Field.component(this)(children: _*)
  }
}

object Field {

  sealed abstract class Layout
  object Layout {
    sealed abstract class Flex(private[Field] val style: TagMod)
    case object Fixed extends Flex(Style.flexbox.none)
    case class Grow(value: Int) extends Flex(^.flex := s"${value.toString} 0 0")

    case class Hor(left: Flex = Grow(1), right: Flex = Grow(1)) extends Layout
    case object Ver extends Layout
  }

  private type Props = Field

  private object Static {
    val desc = Style.fontSize.px10.lineHeight.px16.color.gray7
    val label = Style.fontWeight.semiBold
    val error = Style.fontSize.px11.color.red5
  }

  private def renderHor(props: Props, children: PropsChildren, layout: Layout.Hor): VdomElement = {
    <.div(
      Style.flexbox.flex,
      <.div(
        Style.margin.right24,
        layout.left.style,
        props.label.map(label => {
          <.p(
            Style.flexbox.flex.flexbox.itemsCenter,
            ^.paddingTop := "6px",
            <.label(^.htmlFor := props.id, Static.label, label),
            TagMod.when(props.isRequired)(
              <.span(Style.color.red4.margin.hor4, "*")
            ),
            props.help
          )
        }),
        props.desc.map(<.p(Static.desc, _))
      ),
      <.div(
        layout.right.style,
        children,
        props.error.map(<.p(Static.error, Style.margin.top8, _))
      )
    )
  }

  private def renderVer(props: Props, children: PropsChildren): VdomElement = {
    <.div(
      <.div(
        props.label.map { label =>
          <.div(
            Style.flexbox.flex.flexbox.itemsCenter,
            <.label(^.htmlFor := props.id, Static.label, label),
            TagMod.when(props.isRequired)(
              <.span(Style.color.red4.margin.hor4, "*")
            ),
            props.help
          )
        },
        props.desc.map(<.p(Static.desc, _))
      ),
      <.div(Style.margin.top8, children),
      props.error.map(<.p(Static.error, Style.margin.top8, _))
    )
  }

  private def render(props: Props, children: PropsChildren): VdomElement = props.layout match {
    case layout: Layout.Hor => renderHor(props, children, layout)
    case Layout.Ver         => renderVer(props, children)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
