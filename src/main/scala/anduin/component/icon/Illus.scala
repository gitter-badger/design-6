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

// scalastyle:off number.of.types number.of.methods
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
  object Size24 extends Size { val value = "24" }
  object Size32 extends Size { val value = "32" }
  final case class SizeDynamic(value: String) extends Size

  // scalastyle:off line.size.limit
  sealed trait Name { val content: String }
  object NameDoc extends Name {
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
  object NameDocEmpty extends Name {
    val content: String =
      """
        |<path d="M27 10h-7.11c-.5 0-.89-.5-.89-1.12V1M5 2.04C5 1.46 5.45 1 6 1h14l7 8.28v20.69c0 .57-.44 1.03-1 1.03H6c-.55 0-1-.47-1-1.03V2.04z" stroke="#A7B6C2" stroke-linecap="round" stroke-linejoin="round" stroke-dasharray="3 3"/>
      """.stripMargin
  }
  object NamePdf extends Name {
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
  object NameSheet extends Name {
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
  object NameShareChecklist extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="evenodd">
        |    <path fill="#C456A0" fill-rule="nonzero" d="M15 21.003V23H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2v7.71a2.5 2.5 0 0 0-3.268.232l-.793.793a2.498 2.498 0 0 0-.682 2.268H13.5a2.5 2.5 0 0 0-2.5 2.5v1a2.5 2.5 0 0 0 2.5 2.5H15z"/>
        |    <path fill="#F3DDEC" fill-rule="nonzero" d="M18 11.26a2.488 2.488 0 0 0-1.268.682l-.793.793a2.498 2.498 0 0 0-.682 2.268H13.5a2.5 2.5 0 0 0-2.5 2.5v1A2.5 2.5 0 0 0 13.379 21H4V4h14v7.26z"/>
        |    <path fill="#C456A0" fill-rule="nonzero" d="M12 12h4v1.5h-4V12zm0-5h4v1.5h-4V7z"/>
        |    <path stroke="#C456A0" stroke-width="1.5" d="M6 7l1.5 1.5L10 6M6 12l1.5 1.5L10 11M6 17l1.5 1.5L10 16"/>
        |    <path fill="#C456A0" fill-rule="nonzero" d="M18.854 13.356l3.797 3.798a1.2 1.2 0 0 1 0 1.697l-3.797 3.798a.5.5 0 0 1-.708 0l-.792-.793a.5.5 0 0 1 0-.707l2.146-2.146h-6a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h6l-2.146-2.147a.5.5 0 0 1 0-.707l.792-.793a.5.5 0 0 1 .708 0z"/>
        |  </g>
      """.stripMargin
  }
  object NameViewChecklist extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="evenodd">
        |    <path fill="#C456A0" fill-rule="nonzero" d="M20 13.24a8.5 8.5 0 0 0-9.85 4.99l-.32.77.32.77A8.5 8.5 0 0 0 12.52 23H4a2 2 0 0 1-2-2V4c0-1.1.9-2 2-2h14a2 2 0 0 1 2 2v9.24z"/>
        |    <path fill="#F3DDEC" fill-rule="nonzero" d="M18 13a8.5 8.5 0 0 0-7.85 5.23l-.32.77.32.77c.18.43.4.84.64 1.23H4V4h14v9z"/>
        |    <path fill="#C456A0" fill-rule="nonzero" d="M12 12h4v1.5h-4V12zm0-5h4v1.5h-4V7z"/>
        |    <path stroke="#C456A0" stroke-width="1.5" d="M6 7l1.5 1.5L10 6M6 12l1.5 1.5L10 11M6 17l1.5 1.5L10 16"/>
        |    <path fill="#C456A0" fill-rule="nonzero" d="M12 19a6.5 6.5 0 0 1 12 0 6.5 6.5 0 0 1-12 0z"/>
        |    <path fill="#FFF" fill-rule="nonzero" d="M18 21a2 2 0 1 1 0-4 2 2 0 0 1 0 4zm0-1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
        |  </g>
        """.stripMargin
  }
  object NameReview extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#D5EAF6" stroke="#2B95D6" stroke-width="2" d="M7 19.86L11.64 16H20V3H3v13h4v3.86z"/>
        |    <path fill="#2B95D6" d="M6 7h11v1.5H6zM6 10.5h7V12H6z"/>
        |  </g>
      """.stripMargin
  }
  object NameCompare extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#CDEDF0" stroke="#08A0B1" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4h7l4 4v11H12z"/>
        |    <path fill="#FFF" stroke="#08A0B1" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 4h7l4 4v11H1z"/>
        |    <path fill="#08A0B1" d="M5 10l4 3-4 3v-6zM19 10l-4 3 4 3v-6z"/>
        |  </g>
      """.stripMargin
  }
  object NameConfirmCertificateSent extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#15B371" d="M19.44 11.4a2.5 2.5 0 0 0-2.7.54l-.8.8a2.5 2.5 0 0 0-.68 2.26H13.5c-.38 0-.74.09-1.07.24A2 2 0 0 1 12 14a2 2 0 1 1-4 0 2 2 0 1 1-4 0 2 2 0 0 1-2 2H0v-2c0-1.1.9-2 2-2a2 2 0 1 1 0-4 2 2 0 1 1 0-4l2-2a2 2 0 1 1 4 0H4a2 2 0 1 1-4 0V0h2a2 2 0 0 1 2 2h4a2 2 0 1 1 4 0 2 2 0 1 1 4 0c0-1.1.9-2 2-2h2v2a2 2 0 0 1-2 2 2 2 0 1 1 0 4 2 2 0 0 1 1.44 3.4z"/>
        |    <path fill="#D1F0E3" d="M15.27 3c.34.6.99 1 1.73 1v7a2 2 0 0 0-2 2H5a2 2 0 0 0-2-2V5a2 2 0 0 0 2-2h10.27z"/>
        |    <path fill="#15B371" d="M6 5h8v2H6V5zM5 8h10v1H5V8zm3 2h4v1H8v-1zm10.85 3.36l3.8 3.8a1.2 1.2 0 0 1 0 1.7l-3.8 3.79a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7L19.5 19h-6a.5.5 0 0 1-.5-.5v-1c0-.27.22-.5.5-.5h6l-2.15-2.14a.5.5 0 0 1 0-.71l.8-.8c.2-.19.5-.19.7 0z"/>
        |  </g>
      """.stripMargin
  }
  object NameConfirmFundReceived extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="evenodd">
        |    <path fill="#15B371" fill-rule="nonzero" d="M20 4H3.5a.5.5 0 0 0-.5.5V15a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1z"/>
        |    <path fill="#15B371" fill-rule="nonzero" d="M18 2H1.5a.5.5 0 0 0-.5.5V13a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1z"/>
        |    <path fill="#D1F0E3" fill-rule="nonzero" d="M22 11.53A6 6 0 0 0 12.08 17H5a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1v5.53z"/>
        |    <path fill="#15B371" fill-rule="nonzero" d="M12.02 15.5a6.08 6.08 0 0 0 .06 1.5H5a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1v5.53a6 6 0 0 0-1.5-.99V9A2.5 2.5 0 0 1 18 6.5H8A2.5 2.5 0 0 1 5.5 9v4A2.5 2.5 0 0 1 8 15.5h4.02zm.79-2.5a2 2 0 1 1 2.18-2.2 6.03 6.03 0 0 0-2.18 2.2z"/>
        |    <path fill="#15B371" d="M15 18.65a4 4 0 1 1 6 0V23l-3-2-3 2v-4.35z"/>
        |    <circle cx="18" cy="16" r="2" fill="#FFF"/>
        |  </g>
      """.stripMargin
  }
  object NameConfirmFundSent extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#15B371" d="M20 4H3.5a.5.5 0 0 0-.5.5V15a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1z"/>
        |    <path fill="#15B371" d="M18 2H1.5a.5.5 0 0 0-.5.5V13a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1z"/>
        |    <path fill="#D1F0E3" d="M22 13.67l-1.73-1.73a2.5 2.5 0 0 0-3.54 0l-.8.8a2.5 2.5 0 0 0-.67 2.26H13.5a2.5 2.5 0 0 0-2.45 2H5a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1v7.67z"/>
        |    <path fill="#15B371" d="M12 15.5a2.5 2.5 0 0 0-.95 1.5H5a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1v7.67l-1.5-1.5V9A2.5 2.5 0 0 1 18 6.5H8A2.5 2.5 0 0 1 5.5 9v4A2.5 2.5 0 0 1 8 15.5h4zm1-2.5a2 2 0 1 1 0-4 2 2 0 0 1 0 4z"/>
        |    <path fill="#15B371" d="M18.85 13.36l3.8 3.8a1.2 1.2 0 0 1 0 1.7l-3.8 3.79a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7L19.5 19h-6a.5.5 0 0 1-.5-.5v-1c0-.27.22-.5.5-.5h6l-2.15-2.14a.5.5 0 0 1 0-.71l.8-.8c.2-.19.5-.19.7 0z"/>
        |  </g>
      """.stripMargin
  }
  object NameEditDoc extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#2B95D6" d="M19.3 6.89l-1.32 1.3-.4.4-1.41 1.4V10L7.2 18.98 6.2 23H3a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h10a1 1 0 0 1 .7.3l5.6 5.59zm.7 10.88V22a1 1 0 0 1-1 1h-4.18L20 17.77z"/>
        |    <path fill="#D5EAF6" d="M17.87 8.3l-.28.28L16.17 10 7.2 18.98 6.7 21H4V3h8.59l5.28 5.3zM18 19.78V21h-1.2l1.2-1.22z"/>
        |    <path fill="#2B95D6" d="M18.17 8l-.19.2-.4.38L16.18 10H11V2h2v6h5.17zm-4 4l-1.5 1.5H6V12h8.17zm-4 4l-1.5 1.5H6V16h4.17zM6 7h4v1.5H6V7zM19 10l1.86-1.85c.2-.2.52-.2.71 0l2.29 2.29c.2.2.2.5 0 .7L22 13l-3-3zM8 24l1-4 9-9 3 2.91L12 23l-4 1z"/>
        |    <path fill="#D5EAF6" d="M11.87 21l7-7.07-.85-.82L11 20.12z"/>
        |  </g>
      """.stripMargin
  }
  object NameMarkAsFinal extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="evenodd">
        |    <path fill="#15B371" fill-rule="nonzero" d="M20 10.34a5.99 5.99 0 0 0-7 8.98V23H3a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h10a1 1 0 0 1 .7.3l6 6a1 1 0 0 1 .3.71v2.33z"/>
        |    <path fill="#D1F0E3" fill-rule="nonzero" d="M18 10a6 6 0 0 0-5 9.32V21H4V3h8.59L18 8.42V10z"/>
        |    <path fill="#15B371" fill-rule="nonzero" d="M13.53 12a6 6 0 0 0-.99 1.5H6V12h7.53zM12 16c0 .51.06 1.02.19 1.5H6V16h6zm1-8h6v2h-8V2h2v6zM6 7h4v1.5H6V7z"/>
        |    <path fill="#15B371" d="M15 18.65a4 4 0 1 1 6 0V23l-3-2-3 2v-4.35z"/>
        |    <circle cx="18" cy="16" r="2" fill="#FFF"/>
        |  </g>
      """.stripMargin
  }
  object NameRedline extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#FDDDDD" stroke="#F55656" stroke-linejoin="round" stroke-width="2" d="M3 6h10l3 3.01V22H3z"/>
        |    <path fill="#FFF" stroke="#F55656" stroke-linejoin="round" stroke-width="2" d="M7 2h9.8a.5.5 0 0 1 .35.15l2.7 2.71c.1.1.15.22.15.36V18H7V2z"/>
        |    <path fill="#F55656" d="M10 13h6v2h-6zM11 9h6v2h-6zM10 5h4v2h-4z"/>
        |  </g>
      """.stripMargin
  }
  object NameReleaseSignedDoc extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#15B371" d="M15 21.04V23H3a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h10a1 1 0 0 1 .7.3l6 6a1 1 0 0 1 .3.71v3.71a2.5 2.5 0 0 0-3.23.26l-.79.8a2.5 2.5 0 0 0-.68 2.26h-1.76a2.5 2.5 0 0 0-2.5 2.5v1a2.5 2.5 0 0 0 2.5 2.5H15z"/>
        |    <path fill="#D1F0E3" d="M18 11.31c-.45.1-.88.32-1.23.67l-.79.8a2.5 2.5 0 0 0-.68 2.26h-1.76a2.5 2.5 0 0 0-2.5 2.5v1A2.5 2.5 0 0 0 13.07 21H4V3h8.59L18 8.42v2.89z"/>
        |    <path fill="#15B371" d="M13.02 15.1a2.5 2.5 0 0 0-1.98 2.44v.4c-.17.04-.35.06-.54.06-.97 0-1.32-.72-1.3-1.57.02-.35.06-.58.21-1.39l.04-.22.1-.6c-.5.45-1.1 1.58-1.6 3.1a1 1 0 1 1-1.9-.64C7.02 13.78 8.11 12 10 12c.93 0 1.47.65 1.56 1.52.05.45 0 .92-.14 1.66l-.04.24-.02.05.18-.13c.3-.21.6-.34.96-.34.19 0 .34.03.52.1zM13 8h6v2h-8V2h2v6zm5.85 5.36l3.8 3.8a1.2 1.2 0 0 1 0 1.7l-3.8 3.79a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7L19.5 19h-6a.5.5 0 0 1-.5-.5v-1c0-.27.22-.5.5-.5h6l-2.15-2.14a.5.5 0 0 1 0-.71l.8-.8c.2-.19.5-.19.7 0z"/>
        |  </g>
      """.stripMargin
  }
  object NameReviewDoc extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#08A0B1" d="M18 13V8.42L12.59 3H4v18h6.79a8.5 8.5 0 0 0 1.73 2H3a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h10a1 1 0 0 1 .7.3l6 6a1 1 0 0 1 .3.71v5.23a8.5 8.5 0 0 0-2-.24z"/>
        |    <path fill="#CDEDF0" d="M18 13a8.5 8.5 0 0 0-7.85 5.23l-.32.77.32.77c.18.43.4.84.64 1.23H4V3h8.59L18 8.42V13z"/>
        |    <path fill="#08A0B1" d="M16 13.24c-.3.07-.59.16-.87.26H6V12h10v1.24zM11.52 16a8.5 8.5 0 0 0-1.02 1.5H6V16h5.52zM13 8h6v2h-8V2h2v6zM6 7h4v1.5H6V7zm6 12a6.5 6.5 0 0 1 12 0 6.5 6.5 0 0 1-12 0z"/>
        |    <path fill="#FFF" d="M18 21a2 2 0 1 1 0-4 2 2 0 0 1 0 4zm0-1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
        |  </g>
      """.stripMargin
  }
  object NameShareBankInfo extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#0C7BC3" d="M20.02 11.72a2.5 2.5 0 0 0-3.29.22l-.8.8a2.5 2.5 0 0 0-.67 2.26H13.5a2.5 2.5 0 0 0-2.5 2.5v1a2.5 2.5 0 0 0 2.38 2.5H.5a.5.5 0 0 1-.5-.5v-2c0-.28.22-.5.5-.5H2V7H.5a.5.5 0 0 1-.5-.5V4.35c0-.21.13-.4.33-.47L11 0l10.67 3.88c.2.07.33.26.33.47V6.5a.5.5 0 0 1-.5.5h-1.47l-.01 4.72z"/>
        |    <path fill="#CFE5F3" d="M18 11.26c-.46.1-.9.32-1.27.68l-.8.8a2.5 2.5 0 0 0-.67 2.26H13.5a2.5 2.5 0 0 0-2.5 2.5v.5H4V7h14v4.26z"/>
        |    <path fill="#0C7BC3" d="M12 15.5a2.5 2.5 0 0 0-.95 1.5H10V8h2v7.5zm4-2.83l-.06.07a2.5 2.5 0 0 0-.68 2.26H14V8h2v4.67zM6 8h2v9H6V8z"/>
        |    <circle cx="11" cy="4" r="1" fill="#FFF"/>
        |    <path fill="#0C7BC3" d="M18.85 13.36l3.8 3.8a1.2 1.2 0 0 1 0 1.7l-3.8 3.79a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7L19.5 19h-6a.5.5 0 0 1-.5-.5v-1c0-.27.22-.5.5-.5h6l-2.15-2.14a.5.5 0 0 1 0-.71l.8-.8c.2-.19.5-.19.7 0z"/>
        |  </g>
      """.stripMargin
  }
  object NameShareViaEmail extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="evenodd">
        |    <path fill="#F29D49" fill-rule="nonzero" d="M.5 6h4c.28 0 .5.22.5.5V7a.5.5 0 0 1-.5.5h-4A.5.5 0 0 1 0 7v-.5c0-.28.22-.5.5-.5zm1 4h3c.28 0 .5.22.5.5v.5a.5.5 0 0 1-.5.5h-3A.5.5 0 0 1 1 11v-.5c0-.28.22-.5.5-.5zm1 4h2c.28 0 .5.22.5.5v.5a.5.5 0 0 1-.5.5h-2A.5.5 0 0 1 2 15v-.5c0-.28.22-.5.5-.5z"/>
        |    <path fill="#FAECDE" fill-rule="nonzero" stroke="#F29D49" stroke-linejoin="round" stroke-width="2" d="M7 6h16v12H7z"/>
        |    <path stroke="#F29D49" stroke-linejoin="round" stroke-width="1.5" d="M7 6l7.34 6.42a1 1 0 0 0 1.32 0L23 6M7 18l6-6"/>
        |    <path stroke="#F29D49" stroke-width="1.5" d="M22.5 17.5L17 12"/>
        |  </g>
      """.stripMargin
  }
  object NameShareWireInfo extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#0C7CC2" d="M11 17.5V21H1a1 1 0 0 1-1-1V3a1 1 0 0 1 1-1h19a1 1 0 0 1 1 1v9.67l-.73-.73a2.5 2.5 0 0 0-3.54 0l-.8.8a2.5 2.5 0 0 0-.67 2.26H13.5a2.5 2.5 0 0 0-2.5 2.5z"/>
        |    <path fill="#CFE5F3" d="M19 11.26a2.5 2.5 0 0 0-2.27.68l-.8.8a2.5 2.5 0 0 0-.67 2.26H13.5a2.5 2.5 0 0 0-2.5 2.5v1c0 .17.02.34.05.5H2V6h17v5.26z"/>
        |    <path fill="#0C7BC3" d="M11 9h6v1.5h-6V9zm0 3h4v1.5h-4V12zM6 8h1v1a2 2 0 0 1 2 2H7.5a.5.5 0 0 0-.5-.5h-.73a.77.77 0 0 0 0 1.55h.98c.97 0 1.75.78 1.75 1.75v.7a2 2 0 0 1-2 2v1H6v-1a2 2 0 0 1-2-2h1.5c0 .28.22.5.5.5h.75a.75.75 0 1 0 0-1.5h-1c-.97 0-1.75-.78-1.75-1.75V11c0-1.1.9-2 2-2V8zm12.85 5.36l3.8 3.8a1.2 1.2 0 0 1 0 1.7l-3.8 3.79a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7L19.5 19h-6a.5.5 0 0 1-.5-.5v-1c0-.27.22-.5.5-.5h6l-2.15-2.14a.5.5 0 0 1 0-.71l.8-.8c.2-.19.5-.19.7 0z"/>
        |  </g>
      """.stripMargin
  }
  object NameSign extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="evenodd">
        |    <path stroke="#66C" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2 22c.72-6.07 3.61-12.69 5.06-11.94C8.5 10.8 3.44 22 5.97 22c2.53 0 2.77-5.27 3.97-5.22 1.2.04-1.08 5.22.37 5.22 1.44 0 1.83-1.87 2.8-1.87.27 0 .72 1.87 1.45 1.87H16"/>
        |    <path fill="#E1E1F5" fill-rule="nonzero" stroke="#66C" stroke-width="2" d="M21.01 13.75l.02-8.05-5.26-1.16-3.39 7.3 2.96 5.99a.3.3 0 0 0 .46.1L21 13.75z"/>
        |    <ellipse cx="17.05" cy="11.21" fill="#66C" fill-rule="nonzero" rx="1.11" ry="1.18" transform="rotate(-77.5 17.05 11.21)"/>
        |    <path stroke="#66C" stroke-linecap="round" stroke-linejoin="round" d="M15.7 17.32l1.27-5.75-1.28 5.75z"/>
        |    <path fill="#66C" fill-rule="nonzero" d="M14.02 4.15l.7-3.15 8.79 1.95-.7 3.14a1 1 0 0 1-1.2.76l-6.83-1.51a1 1 0 0 1-.76-1.2z"/>
        |  </g>
      """.stripMargin
  }
  object NameRequestToSign extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="evenodd">
        |    <path stroke="#66C" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l5 5M8 12l-5 5"/>
        |    <path fill="#66C" fill-rule="nonzero" d="M2 23a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
        |    <path fill="#E1E1F5" fill-rule="nonzero" stroke="#66C" stroke-width="2" d="M21.01 13.75l.02-8.05-5.26-1.16-3.39 7.3 2.96 5.99a.3.3 0 0 0 .46.1L21 13.75z"/>
        |    <ellipse cx="17.05" cy="11.21" fill="#66C" fill-rule="nonzero" rx="1.11" ry="1.18" transform="rotate(-77.5 17.05 11.21)"/>
        |    <path stroke="#66C" stroke-linecap="round" stroke-linejoin="round" d="M15.7 17.32l1.27-5.75-1.28 5.75z"/>
        |    <path fill="#66C" fill-rule="nonzero" d="M14.02 4.15l.7-3.15 8.79 1.95-.7 3.14a1 1 0 0 1-1.2.76l-6.83-1.51a1 1 0 0 1-.76-1.2z"/>
        |  </g>
      """.stripMargin
  }
  object NameUploadDoc extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#2B95D6" d="M20 11.085a3 3 0 0 0-3.121.708l-3.94 3.94a2.5 2.5 0 0 0 0 3.535l.793.793a2.498 2.498 0 0 0 2.268.682V22.5c0 .171.017.338.05.5H3a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h10a1 1 0 0 1 .708.294l6 6.01A1 1 0 0 1 20 8.01v3.075z"/>
        |    <path fill="#D5EAF6" d="M18 11.085c-.41.144-.794.38-1.121.708l-3.94 3.94a2.5 2.5 0 0 0 0 3.535l.793.793a2.498 2.498 0 0 0 2.268.682V21H4V3h8.585L18 8.424v2.661z"/>
        |    <path fill="#2B95D6" d="M16 12.672l-.828.828H6V12h10v.672zM12.707 16c-.333.443-.5.972-.5 1.5H6V16h6.707zM13 8h6v2h-8V2h2v6zM6 7h4v1.5H6V7zm8.354 10.146l3.939-3.939a1 1 0 0 1 1.414 0l3.94 3.94a.5.5 0 0 1 0 .707l-.793.792a.5.5 0 0 1-.708 0L20 16.5v6a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-6l-2.146 2.146a.5.5 0 0 1-.708 0l-.792-.792a.5.5 0 0 1 0-.708z"/>
        |  </g>
      """.stripMargin
  }
  object NameUploadMultipleDocs extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#2B95D6" d="M18 11.085v-.661L15.585 8H8v13h8v1.5c0 .171.017.338.05.5H7a1 1 0 0 1-1-1V7a1 1 0 0 1 1-1h9a1 1 0 0 1 .708.294l3 3.01a1 1 0 0 1 .292.706v1.075a3.012 3.012 0 0 0-2 0zM12.408 2H2v16H1a1 1 0 0 1-1-1V1a1 1 0 0 1 1-1h9a1 1 0 0 1 .708.294L12.408 2zm3 3H5v15H4a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h9a1 1 0 0 1 .708.294L15.408 5z"/>
        |    <path fill="#D5EAF6" d="M18 11.085c-.41.144-.794.38-1.121.708l-3.94 3.94a2.5 2.5 0 0 0 0 3.535l.793.793a2.498 2.498 0 0 0 2.268.682V21H8V8h7.585L18 10.424v.661z"/>
        |    <path fill="#2B95D6" d="M14.672 14l-1.5 1.5H10V14h4.672zm-2.415 3c-.101.5-.049 1.026.158 1.5H10V17h2.257zM10 11h5v1.5h-5V11zm4.354 6.146l3.939-3.939a1 1 0 0 1 1.414 0l3.94 3.94a.5.5 0 0 1 0 .707l-.793.792a.5.5 0 0 1-.708 0L20 16.5v6a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-6l-2.146 2.146a.5.5 0 0 1-.708 0l-.792-.792a.5.5 0 0 1 0-.708z"/>
        |  </g>
      """.stripMargin
  }
  object NameUploadDraft extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#F29D49" d="M20 11.085a3 3 0 0 0-3.121.708l-3.94 3.94a2.5 2.5 0 0 0 0 3.535l.793.793a2.498 2.498 0 0 0 2.268.682V22.5c0 .171.017.338.05.5H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2v7.085z"/>
        |    <path fill="#FCEBDB" d="M18 11.085c-.41.144-.794.38-1.121.708l-3.94 3.94a2.5 2.5 0 0 0 0 3.535l.793.793a2.498 2.498 0 0 0 2.268.682V21H4V4h14v7.085z"/>
        |    <path fill="#F29D49" d="M16 12.672l-.828.828H6V12h10v.672zM12.707 16c-.333.443-.5.972-.5 1.5H6V16h6.707zM6 8h10v1.5H6V8zm8.354 9.146a.5.5 0 0 0 0 .708l.792.792a.5.5 0 0 0 .708 0L18 16.5v6a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-6l2.146 2.146a.5.5 0 0 0 .708 0l.792-.792a.5.5 0 0 0 0-.708l-3.939-3.939a1 1 0 0 0-1.414 0l-3.94 3.94z"/>
        |  </g>
      """.stripMargin
  }
  object NameUploadSignedDoc extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="nonzero">
        |    <path fill="#66C" d="M20 11.085a3.012 3.012 0 0 0-2 0V8.424L12.585 3H4v18h12v1.5c0 .171.017.338.05.5H3a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h10a1 1 0 0 1 .708.294l6 6.01A1 1 0 0 1 20 8.01v3.075z"/>
        |    <path fill="#D5EAF6" d="M18 11.085c-.41.144-.794.38-1.121.708l-3.94 3.94a2.5 2.5 0 0 0 0 3.535l.793.793a2.498 2.498 0 0 0 2.268.682V21H4V3h8.585L18 8.424v2.661z"/>
        |    <path fill="#66C" d="M13.396 15.276l-.457.456a2.49 2.49 0 0 0-.728 1.628c-.543.416-1.05.64-1.711.64-.968 0-1.315-.72-1.293-1.568.01-.35.049-.586.205-1.392l.043-.224c.044-.237.076-.434.097-.598-.506.453-1.097 1.58-1.603 3.098a1 1 0 1 1-1.898-.632C7.022 13.77 8.112 12 10 12c.934 0 1.474.651 1.565 1.521.047.453-.006.924-.145 1.663l-.045.236-.01.054c.071-.057.137-.108.176-.135.301-.213.594-.339.959-.339.301 0 .514.082.874.265l.022.011zM13 8h6v2h-8V2h2v6zm1.354 9.146l3.939-3.939a1 1 0 0 1 1.414 0l3.94 3.94a.5.5 0 0 1 0 .707l-.793.792a.5.5 0 0 1-.708 0L20 16.5v6a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-6l-2.146 2.146a.5.5 0 0 1-.708 0l-.792-.792a.5.5 0 0 1 0-.708z"/>
        |  </g>
      """.stripMargin
  }
  object NameVewiSignRequest extends Name {
    override val content: String =
      """
        |  <g fill="none">
        |    <g stroke="#08A0B1" stroke-linecap="round" stroke-linejoin="round" stroke-width="2">
        |      <path d="M2 2l4 4M6 2L2 6"/>
        |    </g>
        |    <path fill="#08A0B1" d="M2 11a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
        |    <g stroke="#08A0B1" stroke-linecap="round" stroke-linejoin="round" stroke-width="2">
        |      <path d="M2 14l4 4M6 14l-4 4"/>
        |    </g>
        |    <path fill="#08A0B1" d="M2 23a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
        |  </g>
      """.stripMargin
  }
  // scalastyle:on line.size.limit
}
// scalastyle:off number.of.types number.of.methods
