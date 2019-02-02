package anduin.guide.components

import anduin.style.Style
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

final case class CodeBlock(
  content: String,
  language: String = "scala"
) {
  def apply(): VdomElement = CodeBlock.component(this)
}

object CodeBlock {

  private type Props = CodeBlock

  private def trimFirstCollapse(content: String): String = {
    val cond = content.startsWith("/*>*/\n")
    if (cond) content.replaceFirst("\n", "") else content
  }

  private def render(props: Props): VdomElement = {
    val htmlContent = trimFirstCollapse(props.content)
      .replace("/*>*/", "<span style=\"opacity: 0.3\">")
      .replace("/*<*/", "</span>")
    val content: TagMod = if (props.language == "scala") {
      ^.dangerouslySetInnerHtml := htmlContent
    } else htmlContent
    val cls = ^.cls := s"language-${props.language}"
    <.div(
      Style.background.gray1,
      Style.padding.ver12.padding.hor8.overflow.auto,
      Style.focus.outline.transition.allWithOutline,
      ^.tabIndex := 0,
      <.pre(
        Style.fontSize.px16.lineHeight.px24.fontFamily.mono,
        ^.cls := "line-numbers",
        <.code(Style.display.block, cls, content)
      )
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
