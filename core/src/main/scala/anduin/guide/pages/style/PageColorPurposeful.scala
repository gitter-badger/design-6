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
    Color("**Primary colors** to highlight elements")(
      Shade(Style.background.primary5, "primary5", "137CBD"),
      Shade(Style.background.primary4, "primary4", "2B95D6"),
      Shade(Style.background.primary3, "primary3", "48AFF0"),
      Shade(Style.background.primary2, "primary2", "ACDBF8"),
      Shade(Style.background.primary1, "primary1", "D3EEFF")
    ),
    Color("**Success colors** to indicate successful operations")(
      Shade(Style.background.success5, "success5", "0F9960"),
      Shade(Style.background.success4, "success4", "15B371"),
      Shade(Style.background.success3, "success3", "3DCC91"),
      Shade(Style.background.success2, "success2", "76D5AD"),
      Shade(Style.background.success1, "success1", "C7F2DF")
    ),
    Color("**Warning colors** to indicate warnings")(
      Shade(Style.background.warning5, "warning5", "D9822B"),
      Shade(Style.background.warning4, "warning4", "F29D49"),
      Shade(Style.background.warning3, "warning3", "FFB366"),
      Shade(Style.background.warning2, "warning2", "EFBE8E"),
      Shade(Style.background.warning1, "warning1", "FAECDE")
    ),
    Color("**Danger colors** to indicate errors and negative actions")(
      Shade(Style.background.danger5, "danger5", "DB3737"),
      Shade(Style.background.danger4, "danger4", "F55656"),
      Shade(Style.background.danger3, "danger3", "FF7373"),
      Shade(Style.background.danger2, "danger2", "F5AAAA"),
      Shade(Style.background.danger1, "danger1", "FCF0F0")
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
