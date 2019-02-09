// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.guide.pages.style

import anduin.guide.components.Markdown
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PageColorPurposeful() {
  def apply(): VdomElement = PageColorPurposeful.component(this)
}

object PageColorPurposeful {

  private type Props = PageColorPurposeful

  case class Shade(backgroundMod: TagMod, name: String, hex: String)
  case class Color(desc: String, shades: List[Shade])
  object Color {
    def apply(name: String)(shades: Shade*): Color = Color(name, List(shades: _*))
  }

  private val colors = List(
    Color("**Blue colors** to highlight elements")(
      Shade(Style.background.blue5, "blue5", "137CBD"),
      Shade(Style.background.blue4, "blue4", "2B95D6"),
      Shade(Style.background.blue3, "blue3", "48AFF0"),
      Shade(Style.background.blue2, "blue2", "ACDBF8"),
      Shade(Style.background.blue1, "blue1", "D3EEFF")
    ),
    Color("**Green colors** to indicate successful operations")(
      Shade(Style.background.green5, "green5", "0F9960"),
      Shade(Style.background.green4, "green4", "15B371"),
      Shade(Style.background.green3, "green3", "3DCC91"),
      Shade(Style.background.green2, "green2", "76D5AD"),
      Shade(Style.background.green1, "green1", "C7F2DF")
    ),
    Color("**Orange colors** to indicate warnings")(
      Shade(Style.background.orange5, "orange5", "D9822B"),
      Shade(Style.background.orange4, "orange4", "F29D49"),
      Shade(Style.background.orange3, "orange3", "FFB366"),
      Shade(Style.background.orange2, "orange2", "EFBE8E"),
      Shade(Style.background.orange1, "orange1", "FAECDE")
    ),
    Color("**Red colors** to indicate errors and negative actions")(
      Shade(Style.background.red5, "red5", "DB3737"),
      Shade(Style.background.red4, "red4", "F55656"),
      Shade(Style.background.red3, "red3", "FF7373"),
      Shade(Style.background.red2, "red2", "F5AAAA"),
      Shade(Style.background.red1, "red1", "FCF0F0")
    )
  )

  private def renderShade(shade: Shade): VdomElement = {
    <.div(
      ^.key := shade.name,
      Style.flexbox.fill,
      <.div(
        Style.borderRadius.px4,
        TagMod(^.width := "56px", ^.height := "56px"),
        shade.backgroundMod,
      ),
      <.div(
        Style.fontFamily.mono.margin.top8,
        <.p(Style.fontWeight.bold, shade.name),
        <.p(Style.opacity.pc80, s"#${shade.hex}")
      )
    )
  }

  private def renderColor(color: Color): VdomElement = {
    <.div(
      Style.padding.bottom16,
      ^.key := color.desc,
      Markdown(s"${color.desc}:")(),
      <.div(
        Style.flexbox.flex.fontSize.px17.lineHeight.px24,
        Style.padding.bottom24.border.bottom.borderColor.gray3,
        color.shades.toVdomArray(renderShade)
      )
    )
  }

  private def render(props: Props): VdomElement = {
    <.div(colors.toVdomArray(renderColor))
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
