package anduin.guide.components

import anduin.style.Style
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

final case class QuoteBlock(
  content: String
) {
  def apply(): VdomElement = QuoteBlock.component(this)
}

object QuoteBlock {

  private type Props = QuoteBlock

  private def getColors(key: String): TagMod = key match {
    case "primary" => Style.background.blue1.borderColor.blue4
    case "success" => Style.background.green1.borderColor.green4
    case "warning" => Style.background.orange1.borderColor.orange4
    case "danger"  => Style.background.red1.borderColor.red4
    case _         => Style.background.gray1.borderColor.gray4
  }

  private def render(props: QuoteBlock): VdomElement = {
    val parts = props.content.split("::")
    val (styles: TagMod, content: String) = if (parts.isDefinedAt(1)) {
      // well
      val key = parts(1)
      (getColors(key), props.content.replace(s"::$key::", ""))
    } else {
      // block quote
      // IMPORTANT: I don't know why but without TagMod here we have run-time
      // error in Markdown
      (TagMod(Style.fontStyle.italic.borderColor.gray4), props.content)
    }
    <.blockquote(
      styles,
      Style.padding.hor24.border.left.borderWidth.px4,
      ^.dangerouslySetInnerHtml := content
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
