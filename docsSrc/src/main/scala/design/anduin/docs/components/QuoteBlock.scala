package design.anduin.docs.components

import design.anduin.style.Style
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
    case "primary" => Style.background.primary1.borderColor.primary4
    case "success" => Style.background.success1.borderColor.success4
    case "warning" => Style.background.warning1.borderColor.warning4
    case "danger"  => Style.background.danger1.borderColor.danger4
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
