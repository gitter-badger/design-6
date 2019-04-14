package anduin.guide.components

import anduin.guide.scalajs.highlight.Highlight
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

  // Replace "/*>*/ and /*<*/"
  private def dim(string: String): String = {
    val cond = string.startsWith("/*>*/\n")
    if (cond) string.replaceFirst("\n", "") else string
  }.replace("/*>*/", "<span style=\"opacity: 0.3\">")
    .replace("/*<*/", "</span>")

  private case class State(highlighted: Option[String])

  private val styleText = Style.fontSize.px15.lineHeight.px24.fontFamily.mono

  private class Backend(scope: BackendScope[Props, State]) {

    private def getCode(highlighted: Option[String]): VdomElement = {
      val html = ^.dangerouslySetInnerHtml := dim(highlighted.getOrElse[String](""))
      <.pre(styleText, <.code(Style.display.block, html))
    }

    def render(props: Props, state: State): VdomElement = {
      <.div(
        Style.background.gray1.padding.all4,
        <.div(
          Style.padding.ver8.padding.hor12.overflow.auto,
          Style.outline.focusLight.transition.allWithOutline,
          ^.maxHeight := "260px",
          ^.tabIndex := 0,
          getCode(state.highlighted)
        )
      )
    }

    def highlight: Callback = {
      for {
        props <- scope.props
        result <- CallbackTo[Highlight.Result] {
          Highlight.Core.highlight(props.language, props.content, ignore_illegals = true)
        }
        _ <- scope.modState(_.copy(highlighted = Some(result.value)))
      } yield ()
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State(highlighted = None))
    .renderBackend[Backend]
    .componentDidMount(_.backend.highlight)
    .build
}
