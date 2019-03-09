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

  private def getContent(props: Props): TagMod = {
    val htmlContent = trimFirstCollapse(props.content)
      .replace("/*>*/", "<span style=\"opacity: 0.3\">")
      .replace("/*<*/", "</span>")
    if (props.language == "scala") {
      ^.dangerouslySetInnerHtml := htmlContent
    } else htmlContent
  }

  private def render(props: Props): VdomElement = {
    <.div(
      Style.background.gray1.padding.all4,
      <.div(
        Style.padding.ver8.padding.hor4.overflow.auto,
        Style.outline.focusLight.transition.allWithOutline,
        ^.maxHeight := "260px",
        ^.tabIndex := 0,
        <.pre(
          Style.fontSize.px15.lineHeight.px24.fontFamily.mono,
          ^.cls := "line-numbers",
          <.code(
            Style.display.block,
            ^.cls := s"language-${props.language}",
            getContent(props)
          )
        )
      )
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
