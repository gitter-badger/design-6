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
  case object NameSlide extends Name {
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
  case object NameFolder extends Name {
    override val content: String =
      """
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M2 0a2 2 0 0 0-2 2v20c0 1.1.9 2 2 2h24a2 2 0 0 0 2-2V5a2 2 0 0 0-2-2H13c-.82 0-2.2-1.39-3-2.27C9.6.28 9.04 0 8.44 0H2z" transform="translate(2 4)" fill="url(#paint0_linear)"/>
        |  <defs>
        |    <linearGradient id="paint0_linear" x2="1" gradientUnits="userSpaceOnUse" gradientTransform="scale(29.1331 24.9712) rotate(73.97)">
        |      <stop offset=".02" stop-color="#76CBFF"/>
        |      <stop offset="1" stop-color="#54B2F8"/>
        |    </linearGradient>
        |  </defs>
      """.stripMargin
  }
  case object NameFolderEmpty extends Name {
    override val content: String =
      """
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M2 0a2 2 0 0 0-2 2v20c0 1.1.9 2 2 2h24a2 2 0 0 0 2-2V5a2 2 0 0 0-2-2H13c-.82 0-2.2-1.39-3-2.27C9.6.28 9.04 0 8.44 0H2z" transform="translate(2 4)" fill="url(#paint1_linear)"/>
        |  <defs>
        |    <linearGradient id="paint1_linear" x2="1" gradientUnits="userSpaceOnUse" gradientTransform="scale(28.6772 24.5805) rotate(73.97 0 .01)">
        |      <stop stop-color="#BACDDB"/>
        |      <stop offset="1" stop-color="#9BB0C2"/>
        |    </linearGradient>
        |  </defs>|
      """.stripMargin
  }
  case object NameFolderNA extends Name {
    override val content: String =
      """
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M2 0a2 2 0 0 0-2 2v20c0 1.1.9 2 2 2h24a2 2 0 0 0 2-2V5a2 2 0 0 0-2-2H13c-.82 0-2.2-1.39-3-2.27C9.6.28 9.04 0 8.44 0H2z" transform="translate(2 4)" fill="url(#paint2_linear)"/>
        |  <defs>
        |    <linearGradient id="paint2_linear" x2="1" gradientUnits="userSpaceOnUse" gradientTransform="scale(28.6772 24.5805) rotate(73.97 0 .01)">
        |      <stop stop-color="#ECD576"/>
        |      <stop offset="1" stop-color="#E5B76E"/>
        |    </linearGradient>
        |  </defs>
      """.stripMargin
  }
  case object NameFolderComplete extends Name {
    override val content: String =
      """
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M2 0a2 2 0 0 0-2 2v20c0 1.1.9 2 2 2h24a2 2 0 0 0 2-2V5a2 2 0 0 0-2-2H13c-.82 0-2.2-1.39-3-2.27C9.6.28 9.04 0 8.44 0H2z" transform="translate(2 4)" fill="url(#paint3_linear)"/>
        |  <defs>
        |    <linearGradient id="paint3_linear" x2="1" gradientUnits="userSpaceOnUse" gradientTransform="matrix(7.30363 21.78557 -25.41654 6.26025 .44 1.3)">
        |      <stop stop-color="#5FD7B5"/>
        |      <stop offset="1" stop-color="#4EBC9D"/>
        |    </linearGradient>
        |  </defs>
      """.stripMargin
  }
  case object NameFolderSelect extends Name {
    override val content: String =
      """
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M2 0a2 2 0 0 0-2 2v20c0 1.1.9 2 2 2h24a2 2 0 0 0 2-2V5a2 2 0 0 0-2-2H13c-.82 0-2.2-1.39-3-2.27C9.6.28 9.04 0 8.44 0H2z" transform="translate(2 4)" fill="url(#paint4_linear)"/>
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M19.88 15.82l-3.12 3.83a1 1 0 0 1-1.52 0l-3.12-3.82a.5.5 0 0 1 .38-.83h7c.43 0 .66.5.38.82" fill="#137CBD"/>
        |  <defs>
        |    <linearGradient id="paint4_linear" x2="1" gradientUnits="userSpaceOnUse" gradientTransform="scale(27.557 23.6203) rotate(73.97 -.03 .04)">
        |      <stop stop-color="#76CBFF"/>
        |      <stop offset="1" stop-color="#50ABEF"/>
        |    </linearGradient>
        |  </defs>
      """.stripMargin
  }
  case object NameFolderShared extends Name {
    override val content: String =
      """
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M2 0a2 2 0 0 0-2 2v20c0 1.1.9 2 2 2h24a2 2 0 0 0 2-2V5a2 2 0 0 0-2-2H13c-.82 0-2.2-1.39-3-2.27C9.6.28 9.04 0 8.44 0H2z" transform="translate(2 4)" fill="url(#paint5_linear)"/>
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M13.97 18c-.72 0-1.35-.5-1.5-1.2l-.28-1.43a2 2 0 0 1 2-2.37h.12a2 2 0 0 1 2 2.37l-.29 1.43c-.14.7-.77 1.2-1.5 1.2h-.55zm3.76-.9c.1.52.58.9 1.13.9h.4c.56 0 1.03-.38 1.13-.9l.22-1.07a1.5 1.5 0 0 0-1.5-1.78H19a1.5 1.5 0 0 0-1.5 1.78l.22 1.07zm4.03 2.22c.48.11.86.45 1.01.9l.19.52c.17.5-.22 1.01-.77 1.01h-1.25c-.32 0-.6-.2-.7-.49-.24-.7-.75-1.4-1.28-1.83a.25.25 0 0 1 .17-.44c.79 0 1.58.08 2.2.23l.43.1zm-12.7 2.33c-.24.66.28 1.35 1.02 1.35h8.34c.74 0 1.26-.69 1.02-1.35l-.24-.7c-.2-.6-.72-1.04-1.35-1.2l-.57-.13c-1.72-.4-4.34-.4-6.06 0l-.57.14c-.63.15-1.14.6-1.35 1.2l-.24.69z" fill="#2C5A7E"/>
        |  <defs>
        |    <linearGradient id="paint5_linear" x2="1" gradientUnits="userSpaceOnUse" gradientTransform="scale(27.557 23.6203) rotate(73.97 -.03 .04)">
        |      <stop stop-color="#76CBFF"/>
        |      <stop offset="1" stop-color="#50ABEF"/>
        |    </linearGradient>
        |  </defs>
      """.stripMargin
  }
  case object NameFolderCheckList extends Name {
    override val content: String =
      """
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M2 0a2 2 0 0 0-2 2v20c0 1.1.9 2 2 2h24a2 2 0 0 0 2-2V5a2 2 0 0 0-2-2H13c-.82 0-2.2-1.39-3-2.27C9.6.28 9.04 0 8.44 0H2z" transform="translate(2 4)" fill="url(#paint6_linear)"/>
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M11.47 15.17c.15.15.39.15.53 0l2.37-2.29a.22.22 0 0 0 0-.32l-.5-.5a.23.23 0 0 0-.31 0l-1.82 1.75-.86-.75a.23.23 0 0 0-.32 0l-.5.5a.23.23 0 0 0 0 .32l1.41 1.3zm4.76-.82h5.54c.13 0 .23-.1.23-.22v-.98c0-.12-.1-.22-.22-.22h-5.55c-.13 0-.23.1-.23.22v.98c0 .12.1.22.23.22zM12 19.53a.38.38 0 0 1-.53 0l-1.4-1.3a.22.22 0 0 1 0-.31l.49-.5a.23.23 0 0 1 .32 0l.86.75 1.82-1.74a.23.23 0 0 1 .32 0l.5.49c.08.09.08.23 0 .32L12 19.54zm4.23-.82h5.54c.13 0 .23-.1.23-.22v-.98c0-.12-.1-.22-.22-.22h-5.55c-.13 0-.23.1-.23.22v.98c0 .12.1.22.23.22zM12 23.9a.38.38 0 0 1-.53 0l-1.4-1.3a.23.23 0 0 1 0-.31l.49-.5a.23.23 0 0 1 .32 0l.86.75 1.82-1.75a.23.23 0 0 1 .32 0l.5.5c.08.09.08.23 0 .32L12 23.89zm4.23-.82h5.54c.13 0 .23-.1.23-.22v-.98c0-.12-.1-.22-.22-.22h-5.55c-.13 0-.23.1-.23.22v.98c0 .12.1.22.23.22z" fill="#2C5A7E"/>
        |  <defs>
        |    <linearGradient id="paint6_linear" x2="1" gradientUnits="userSpaceOnUse" gradientTransform="scale(27.557 23.6203) rotate(73.97 -.03 .04)">
        |      <stop stop-color="#76CBFF"/>
        |      <stop offset="1" stop-color="#50ABEF"/>
        |    </linearGradient>
        |  </defs>
      """.stripMargin
  }
}
