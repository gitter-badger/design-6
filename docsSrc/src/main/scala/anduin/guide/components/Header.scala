package anduin.guide.components

import anduin.style.Style
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

final case class Header(
  title: String,
  obj: Option[Object] = None
) {
  def apply(): VdomElement = Header.component(this)
}

object Header {

  private type Props = Header

  private def render(props: Props): VdomElement = {
    <.header(
      Style.border.bottom.borderWidth.px2.borderColor.gray2,
      Style.margin.bottom32,
      <.h1(
        Style.margin.bottom32,
        ^.fontSize := "40px",
        ^.lineHeight := "48px",
        props.title
      ),
      props.obj.whenDefined { obj =>
        <.div(
          Style.borderRadius.px4.border.all.borderColor.gray3,
          Style.margin.bottom32.overflow.hidden,
          CodeBlock(obj.getClass.getName.replace("$", ""))()
        )
      }
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
