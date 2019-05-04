package design.anduin.docs.components

import design.anduin.docs.facades.highlight.Highlight
import design.anduin.style.Style
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

  private def getCodeContent(props: Props): TagMod = {
    if (Highlight.Core.listLanguages().contains(props.language)) {
      val result = Highlight.Core.highlight(props.language, props.content, ignore_illegals = true)
      ^.dangerouslySetInnerHtml := result.value
    } else {
      props.content
    }
  }

  private def render(props: Props): VdomNode = {
    // Body is wrapped to avoid scrollbar at edges (i.e. just visual thing)
    <.div(
      Style.background.gray1.padding.all4,
      <.div(
        // Scrollable and thus, focusable
        Style.padding.ver8.padding.hor12.overflow.auto,
        Style.outline.focusLight.transition.allWithOutline,
        ^.maxHeight := "260px",
        ^.tabIndex := 0,
        <.pre(
          Style.fontSize.px15.lineHeight.px24.fontFamily.mono,
          <.code(Style.display.block, getCodeContent(props))
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
