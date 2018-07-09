// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.icon

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.svg_<^._

import anduin.style.Style
// scalastyle:on underscore.import

final case class Illus(
  name: Illus.Name,
  size: Illus.Size = Illus.Size32
) {
  def apply(): VdomElement = Illus.component(this)
}

object Illus {

  type Props = Illus

  private def render(props: Illus): VdomElement = {
    <.svg(
      Style.display.block, // https://codepen.io/dvkndn/pen/wmQmbm
      ^.fill := "none",
      ^.xmlns := "http://www.w3.org/2000/svg",
      ^.viewBox := "0 0 32 32",
      ^.width := props.size.value,
      ^.height := props.size.value,
      vdom.html_<^.^.dangerouslySetInnerHtml := props.name.content
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build

  sealed trait Size { val value: String }
  case object Size24 extends Size { val value = "24" }
  case object Size32 extends Size { val value = "32" }
  case class SizeDynamic(value: String) extends Size

  // scalastyle:off line.size.limit
  sealed trait Name { val content: String }
  case object NameDoc extends Name {
    val content: String =
      """
        |  <path d="M6 2.5h13.77l6.73 7.69V30a.5.5 0 0 1-.5.5H6a.5.5 0 0 1-.5-.5V3c0-.27.22-.5.5-.5z" fill="#fff" stroke="#BDC4C9" stroke-linecap="round" stroke-linejoin="round"/>
        |  <g style="mix-blend-mode:darken">
        |    <path d="M26.56 10.5h-6.67c-.11 0-.39-.16-.39-.62V2.56l7.06 7.94z" fill="#F3F4F5"/>
        |    <path d="M26.56 10.5h-6.67c-.11 0-.39-.16-.39-.62V2.56l7.06 7.94z" stroke="#BDC4C9" stroke-linecap="round" stroke-linejoin="round"/>
        |  </g>
        |  <path d="M9.5 7.5h4M9.5 10.5h7M16.5 13.5h6M16.5 16.5h6M16.5 19.5h6M9.5 22.5h13M9.5 25.5h7" stroke="#8ECCF5" stroke-linecap="round"/>
        |  <path d="M10 13.5h3c.28 0 .5.23.5.5v5a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5v-5c0-.27.22-.5.5-.5z" fill="#8ECCF5" fill-opacity=".3" stroke="#8ECCF5"/>
      """.stripMargin
  }
  case object NameDocEmpty extends Name {
    val content: String =
      """
        |<path d="M27 10h-7.11c-.5 0-.89-.5-.89-1.12V1M5 2.04C5 1.46 5.45 1 6 1h14l7 8.28v20.69c0 .57-.44 1.03-1 1.03H6c-.55 0-1-.47-1-1.03V2.04z" stroke="#A7B6C2" stroke-linecap="round" stroke-linejoin="round" stroke-dasharray="3 3"/>
      """.stripMargin
  }
  case object NamePdf extends Name {
    val content: String =
      """
        |  <path d="M6 2.5h13.77l6.73 7.69V30a.5.5 0 0 1-.5.5H6a.5.5 0 0 1-.5-.5V3c0-.28.22-.5.5-.5z" fill="#fff" stroke="#BDC4C9" stroke-linejoin="round"/>
        |  <g style="mix-blend-mode:darken">
        |    <path d="M19.5 2.56l7.06 7.94h-6.67c-.11 0-.39-.16-.39-.62V2.56z" fill="#F3F4F5"/>
        |    <path d="M19.5 2.56l7.06 7.94h-6.67c-.11 0-.39-.16-.39-.62V2.56z" stroke="#BDC4C9"/>
        |  </g>
        |  <path d="M10 8h4M10 20h5M10 23h4M10 26h5M10 11h7M10 14h13M10 17h13" stroke="#E2B1A2" stroke-linecap="round"/>
        |  <path d="M19 22.5a.5.5 0 0 0 .5-.5v-2.5a3 3 0 1 1-3 3H19z" fill="#E2B1A2" fill-opacity=".3" stroke="#E2B1A2" stroke-linejoin="round"/>
      """.stripMargin
  }
  case object NameSheet extends Name {
    override val content: String =
      """
        |  <path d="M6 2.5h13.77l6.73 7.69V30a.5.5 0 0 1-.5.5H6a.5.5 0 0 1-.5-.5V3c0-.27.22-.5.5-.5z" fill="#fff" stroke="#BDC4C9" stroke-linejoin="round"/>
        |  <g style="mix-blend-mode:darken">
        |    <path d="M19.5 2.56l7.06 7.94h-6.67c-.11 0-.39-.16-.39-.62V2.56z" fill="#F3F4F5"/>
        |    <path d="M19.5 2.56l7.06 7.94h-6.67c-.11 0-.39-.16-.39-.62V2.56z" stroke="#BDC4C9"/>
        |  </g>
        |  <path d="M9.5 7.5h2M14.5 7.5h2M9.5 10.5h2M14.5 10.5h2M9.5 13.5h2M14.5 13.5h2M19.5 13.5h3M9.5 16.5h2M14.5 16.5h2M9.5 19.5v6M9.5 25.5h13" stroke="#ADCC8F" stroke-linecap="round"/>
        |  <path d="M9.5 22.67L14 21l3 2 5.5-3.5" stroke="#ADCC8F" stroke-linecap="round" stroke-linejoin="round"/>
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M10 23v2.5h13v-6L17 23l-3-2-4 2z" fill="#ADCC8F" fill-opacity=".3"/>
        |  <path d="M19.5 16.5h3" stroke="#ADCC8F" stroke-linecap="round"/>
      """.stripMargin
  }
  // scalastyle:on line.size.limit
}
