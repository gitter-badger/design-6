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
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M18 11.26V4H4v17h11v2H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2v7.71a2.5 2.5 0 0 0-2-.45zM12 12h4v1.5h-4V12zm0-5h4v1.5h-4V7zM9.47 5.47l1.06 1.06L7.5 9.56 5.47 7.53l1.06-1.06.97.97 1.97-1.97zm0 5l1.06 1.06-3.03 3.03-2.03-2.03 1.06-1.06.97.97 1.97-1.97zm0 5l1.06 1.06-3.03 3.03-2.03-2.03 1.06-1.06.97.97 1.97-1.97zm9.38-2.11l3.8 3.8a1.2 1.2 0 0 1 0 1.7l-3.8 3.79a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7L19.5 19h-6a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h6l-2.15-2.14a.5.5 0 0 1 0-.71l.8-.8a.5.5 0 0 1 .7 0z"/>
      """.stripMargin
  }
  object NameViewChecklist extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M18 13V4H4v17h6.79a8.5 8.5 0 0 0 1.73 2H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2v9.24a8.5 8.5 0 0 0-2-.24zm-6-1h4v1.5h-4V12zm0-5h4v1.5h-4V7zM9.47 5.47l1.06 1.06L7.5 9.56 5.47 7.53l1.06-1.06.97.97 1.97-1.97zm0 5l1.06 1.06-3.03 3.03-2.03-2.03 1.06-1.06.97.97 1.97-1.97zm0 5l1.06 1.06-3.03 3.03-2.03-2.03 1.06-1.06.97.97 1.97-1.97zM12 19a6.5 6.5 0 0 1 12 0 6.5 6.5 0 0 1-12 0zm6 2a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm0-1a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
        """.stripMargin
  }
  object NameCompare extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M13 5v13h9V8.41L18.59 5H13zm10 15H1a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h7a1 1 0 0 1 .7.3L11 5.58V4a1 1 0 0 1 1-1h7a1 1 0 0 1 .7.3l4 4a1 1 0 0 1 .3.7v11a1 1 0 0 1-1 1zm-12-2V8.41L7.59 5H2v13h9zm-6-8l4 3-4 3v-6zm14 0v6l-4-3 4-3z"/>
      """.stripMargin
  }
  object NameComment extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M4 4v11h4v2.73L11.28 15H19V4H4zM3 2h17a1 1 0 0 1 1 1v13a1 1 0 0 1-1 1h-8l-4.57 3.81A.87.87 0 0 1 6 20.14V17H3a1 1 0 0 1-1-1V3a1 1 0 0 1 1-1zm3 5h11v1.5H6V7zm0 3.5h7V12H6v-1.5z"/>
      """.stripMargin
  }
  object NameConfirmCertificateSent extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M19.44 11.4a2.5 2.5 0 0 0-2.7.54l-.8.8a2.5 2.5 0 0 0-.68 2.26H13.5a2.49 2.49 0 0 0-1.07.24A2 2 0 0 1 12 14a2 2 0 1 1-4 0 2 2 0 1 1-4 0 2 2 0 0 1-2 2H0v-2a2 2 0 0 1 2-2 2 2 0 1 1 0-4 2 2 0 1 1 0-4l2-2a2 2 0 1 1 4 0H4a2 2 0 1 1-4 0V0h2a2 2 0 0 1 2 2h4a2 2 0 1 1 4 0 2 2 0 1 1 4 0 2 2 0 0 1 2-2h2v2a2 2 0 0 1-2 2 2 2 0 1 1 0 4 2 2 0 0 1 1.44 3.4zM15.27 3H5a2 2 0 0 1-2 2v6a2 2 0 0 1 2 2h10a2 2 0 0 1 2-2V4a2 2 0 0 1-1.73-1zM6 5h8v2H6V5zM5 8h10v1H5V8zm3 2h4v1H8v-1zm10.85 3.36l3.8 3.8a1.2 1.2 0 0 1 0 1.7l-3.8 3.79a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7L19.5 19h-6a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h6l-2.15-2.14a.5.5 0 0 1 0-.71l.8-.8a.5.5 0 0 1 .7 0z"/>
      """.stripMargin
  }
  object NameConfirmFundReceived extends Name {
    override val content: String =
      """
        |  <g fill="none" fill-rule="evenodd">
        |    <path fill="#4F6F89" d="M20 4H3.5a.5.5 0 0 0-.5.5V15a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1zm-2-2H1.5a.5.5 0 0 0-.5.5V13a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1zm-5.98 13.5a6.08 6.08 0 0 0 .06 1.5H5a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1v5.53a6 6 0 0 0-1.5-.99V9A2.5 2.5 0 0 1 18 6.5H8A2.5 2.5 0 0 1 5.5 9v4A2.5 2.5 0 0 1 8 15.5h4.02zm.79-2.51a2 2 0 1 1 2.18-2.18 6.03 6.03 0 0 0-2.18 2.18zM15 18.65a4 4 0 1 1 6 0V23l-3-2-3 2v-4.35z"/>
        |    <circle cx="18" cy="16" r="2" fill="#FFF"/>
        |  </g>
      """.stripMargin
  }
  object NameConfirmFundSent extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M20 4H3.5a.5.5 0 0 0-.5.5V15a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1zm-2-2H1.5a.5.5 0 0 0-.5.5V13a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1zm-6 13.5a2.5 2.5 0 0 0-.95 1.5H5a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1v7.67l-1.5-1.5V9A2.5 2.5 0 0 1 18 6.5H8A2.5 2.5 0 0 1 5.5 9v4A2.5 2.5 0 0 1 8 15.5h4zm1-2.5a2 2 0 1 1 0-4 2 2 0 0 1 0 4zm5.85.36l3.8 3.8a1.2 1.2 0 0 1 0 1.7l-3.8 3.79a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7L19.5 19h-6a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h6l-2.15-2.14a.5.5 0 0 1 0-.71l.8-.8a.5.5 0 0 1 .7 0z"/>
      """.stripMargin
  }
  object NameEditDoc extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M17.58 8L13 3.42V8h4.58zM11 3H4v18h2.69l-.5 2H3a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h10a1 1 0 0 1 .7.3l5.6 5.59-1.32 1.3-.4.4-1.41 1.4V10H11V3zm5.8 18H18v-1.22l2-2.02V22a1 1 0 0 1-1 1h-4.18l1.98-2zm-2.63-9l-1.5 1.5H6V12h8.17zm-4 4l-1.5 1.5H6V16h4.17zM6 7h4v1.5H6V7zm13 3l1.86-1.85a.5.5 0 0 1 .71 0l2.29 2.29a.5.5 0 0 1 0 .7L22 13l-3-3zM8 24l1-4 9-9 3 2.91L12 23l-4 1zm3.87-3l7-7.07-.86-.82-7 7.01.86.88z"/>
      """.stripMargin
  }
  object NameMarkAsComplete extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M12 20a8 8 0 1 0 0-16 8 8 0 0 0 0 16zm0 2a10 10 0 1 1 0-20 10 10 0 0 1 0 20zm-2.7-6.29l-3.16-3.14a.5.5 0 0 1 0-.71l.71-.7a.5.5 0 0 1 .71 0l2.26 2.25a.25.25 0 0 0 .36 0l5.26-5.26a.5.5 0 0 1 .71 0l.7.7a.5.5 0 0 1 0 .71l-6.14 6.15a1 1 0 0 1-1.42 0z"/>
      """.stripMargin
  }
  object NameMarkLDAsComplete extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M11.07 17a7.06 7.06 0 0 0 0 2H2a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h7l3 3h8a2 2 0 0 1 2 2v7.25a6.97 6.97 0 0 0-2-.96V5h-8.83l-3-3H2v15h9.07zm3.32-5c-.66.4-1.26.9-1.75 1.5H10V12h4.4zM10 8h7v1.5h-7V8zm-4-.57L7.96 5.5 9 6.54 6 9.5 4 7.51l1.05-1.03.96.95zm1.95 3.07L9 11.54 6 14.5l-2-1.98 1.05-1.04.96.95 1.94-1.93zM18 23a5 5 0 1 1 0-10 5 5 0 0 1 0 10zm-3-5l2.5 2.5L21 17l-1-1-2.5 2.5L16 17l-1 1z"/>
      """.stripMargin
  }
  object NameMarkAsFinal extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M17.58 8L13 3.42V8h4.58zM11 3H4v18h9v2H3a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h10a1 1 0 0 1 .7.3l6 6a1 1 0 0 1 .3.71v2.33a5.99 5.99 0 0 0-2-.34h-7V3zm2.53 9c-.4.45-.73.95-.99 1.5H6V12h7.53zM12 16a6 6 0 0 0 .19 1.5H6V16h6zM6 7h4v1.5H6V7zm9 11.65a4 4 0 1 1 6 0V23l-3-2-3 2v-4.35zm3-.65a2 2 0 1 0 0-4 2 2 0 0 0 0 4z"/>
      """.stripMargin
  }
  object NameRedline extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M4 21h13v1a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h1v16zM8 3v14h11V5.42L16.59 3H8zM7 1h9.8a1.5 1.5 0 0 1 1.06.44l2.7 2.72A1.5 1.5 0 0 1 21 5.22V18a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1zm3 12h6v2h-6v-2zm1-4h6v2h-6V9zm-1-4h4v2h-4V5z"/>
      """.stripMargin
  }
  object NameReleaseSignedDoc extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M18 10h-7V3H4v18h9.07c.15.03.31.04.47.04H15V23H3a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h10a1 1 0 0 1 .7.3l6 6a1 1 0 0 1 .3.71v3.71a2.5 2.5 0 0 0-2-.41V10zm-.42-2L13 3.42V8h4.58zm-4.56 7.1a2.5 2.5 0 0 0-1.98 2.44v.4c-.17.04-.35.06-.54.06-.97 0-1.31-.72-1.3-1.57.02-.35.06-.58.21-1.39l.05-.22.1-.6c-.51.45-1.1 1.58-1.61 3.1a1 1 0 1 1-1.9-.64C7.02 13.77 8.11 12 10 12c.93 0 1.47.65 1.56 1.52.05.45 0 .93-.14 1.66l-.04.24-.02.05.18-.13c.3-.21.6-.34.96-.34.19 0 .34.03.52.1zm5.83-1.74l3.8 3.8a1.2 1.2 0 0 1 0 1.7l-3.8 3.79a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7L19.5 19h-6a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h6l-2.15-2.14a.5.5 0 0 1 0-.71l.8-.8a.5.5 0 0 1 .7 0z"/>
      """.stripMargin
  }
  object NameReviewDoc extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M18 10h-7V3H4v18h6.79a8.5 8.5 0 0 0 1.73 2H3a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h10a1 1 0 0 1 .7.3l6 6a1 1 0 0 1 .3.71v5.23a8.5 8.5 0 0 0-2-.24v-3zm-.42-2L13 3.42V8h4.58zM16 13.24a8.47 8.47 0 0 0-.87.26H6V12h10v1.24zM11.52 16c-.39.46-.73.96-1.02 1.5H6V16h5.52zM6 7h4v1.5H6V7zm6 12a6.5 6.5 0 0 1 12 0 6.5 6.5 0 0 1-12 0zm6 2a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm0-1a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
      """.stripMargin
  }
  object NameShareBankInfo extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M18 11.26V7H4v11h7v.5a2.5 2.5 0 0 0 2.38 2.5H.5a.5.5 0 0 1-.5-.5v-2a.5.5 0 0 1 .5-.5H2V7H.5a.5.5 0 0 1-.5-.5V4.35a.5.5 0 0 1 .33-.47L11 0l10.67 3.88a.5.5 0 0 1 .33.47V6.5a.5.5 0 0 1-.5.5h-1.47l-.01 4.72a2.5 2.5 0 0 0-2.02-.46zM11 5a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm1 10.5a2.5 2.5 0 0 0-.95 1.5H10V8h2v7.5zm4-2.83l-.06.06a2.5 2.5 0 0 0-.68 2.27H14V8h2v4.67zM6 8h2v9H6V8zm12.85 5.36l3.8 3.8a1.2 1.2 0 0 1 0 1.7l-3.8 3.79a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7L19.5 19h-6a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h6l-2.15-2.14a.5.5 0 0 1 0-.71l.8-.8a.5.5 0 0 1 .7 0z"/>
      """.stripMargin
  }
  object NameShareViaEmail extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M20.94 17l-4.37-4.37-.42.36a1.75 1.75 0 0 1-2.3 0l-.42-.36L9.06 17h11.88zM22 15.94V7.87l-4.3 3.77 4.3 4.3zm-14 0l4.3-4.3L8 7.87v8.07zM20.72 7H9.28l5.56 4.86a.25.25 0 0 0 .33 0L20.71 7zM.5 6h4a.5.5 0 0 1 .5.5V7a.5.5 0 0 1-.5.5h-4A.5.5 0 0 1 0 7v-.5A.5.5 0 0 1 .5 6zm1 4h3a.5.5 0 0 1 .5.5v.5a.5.5 0 0 1-.5.5h-3A.5.5 0 0 1 1 11v-.5a.5.5 0 0 1 .5-.5zm1 4h2a.5.5 0 0 1 .5.5v.5a.5.5 0 0 1-.5.5h-2A.5.5 0 0 1 2 15v-.5a.5.5 0 0 1 .5-.5zM7 5h16a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1z"/>
      """.stripMargin
  }
  object NameShareWireInfo extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M11 19v2H1a1 1 0 0 1-1-1V3a1 1 0 0 1 1-1h19a1 1 0 0 1 1 1v9.67l-.73-.73a2.49 2.49 0 0 0-1.27-.68V6H2v13h9zm0-10h6v1.5h-6V9zm0 3h4v1.5h-4V12zM6 8h1v1a2 2 0 0 1 2 2H7.5a.5.5 0 0 0-.5-.5h-.73a.77.77 0 0 0 0 1.55h.98c.97 0 1.75.78 1.75 1.75v.7a2 2 0 0 1-2 2v1H6v-1a2 2 0 0 1-2-2h1.5a.5.5 0 0 0 .5.5h.75a.75.75 0 1 0 0-1.5h-1A1.75 1.75 0 0 1 4 11.75V11a2 2 0 0 1 2-2V8zm12.85 5.36l3.8 3.8a1.2 1.2 0 0 1 0 1.7l-3.8 3.79a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7L19.5 19h-6a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h6l-2.15-2.14a.5.5 0 0 1 0-.71l.8-.8a.5.5 0 0 1 .7 0z"/>
      """.stripMargin
  }
  object NameSign extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M14.38 5.15a1 1 0 0 1-.36-1l.7-3.15 8.79 1.95-.7 3.14a1 1 0 0 1-.78.77L22 14.23l-5.59 4.48a1.3 1.3 0 0 1-1.97-.44l-3.18-6.42 3.11-6.7zm1.12 10.74l.84-3.8a1.1 1.1 0 0 1-.44-1.13c.13-.6.75-.97 1.39-.83.64.14 1.04.74.91 1.34-.1.43-.45.74-.88.83l-.84 3.8 3.54-2.83.01-6.76-3.7-.82-2.84 6.14 2.01 4.06zM5.18 13.87a31.75 31.75 0 0 0-2.19 8.25 1 1 0 0 1-1.98-.24 33.74 33.74 0 0 1 2.33-8.8c1.3-3.05 2.63-4.71 4.18-3.91.9.47.95 1.37.65 2.99-.16.87-.31 1.5-.92 3.74l-.1.37a27.56 27.56 0 0 0-.87 3.96c-.03.3-.03.53-.02.7.4-.18.8-.78 1.26-1.92l.6-1.55c.5-1.14.93-1.72 1.87-1.68 1.03.04 1.35.82 1.32 1.82-.02.45-.08.83-.27 1.86l-.02.1a17.04 17.04 0 0 0-.19 1.17l.3-.31.34-.4c.5-.58.97-.89 1.64-.89.43 0 .69.2.9.47.08.1.15.22.23.36l.32.65c.08.17.16.3.22.39H16a1 1 0 0 1 0 2h-1.44c-.85 0-1.29-.5-1.77-1.45l-.15.17c-.73.85-1.35 1.28-2.33 1.28-1.06 0-1.49-.79-1.5-1.8v-.21C8.1 22.31 7.26 23 5.97 23c-1.47 0-1.86-1.21-1.68-2.98.11-1.03.38-2.22.93-4.27l.1-.37c.57-2.1.73-2.75.87-3.5-.32.5-.67 1.18-1.01 1.99z"/>
      """.stripMargin
  }
  object NameRequestToSign extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M17.32 12.3l-.84 3.8L20 13.28l.02-6.76-3.7-.82-2.84 6.14 2.01 4.06.84-3.8a1.1 1.1 0 0 1-.44-1.13c.13-.6.75-.97 1.39-.83.64.14 1.04.74.91 1.34-.1.43-.45.74-.88.83zm-11.82.79l1.8-1.8a1 1 0 1 1 1.4 1.42L6.92 14.5l1.8 1.8a1 1 0 0 1-1.42 1.4L5.5 15.92l-1.8 1.8a1 1 0 1 1-1.4-1.42l1.79-1.79-1.8-1.8a1 1 0 0 1 1.42-1.4l1.79 1.79zM2 23a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zM14.38 5.15a1 1 0 0 1-.36-1l.7-3.15 8.79 1.95-.7 3.14a1 1 0 0 1-.78.77L22 14.23l-5.59 4.48a1.3 1.3 0 0 1-1.97-.44l-3.18-6.42 3.11-6.7z"/>
      """.stripMargin
  }
  object NameUploadDoc extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M18 10h-7V3H4v18h12v1.5c0 .17.02.34.05.5H3a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h10a1 1 0 0 1 .7.3l6 6a1 1 0 0 1 .3.71v3.08a3.01 3.01 0 0 0-2 0V10zm-.42-2L13 3.42V8h4.58zM16 12.67l-.83.83H6V12h10v.67zM12.7 16c-.33.44-.5.97-.5 1.5H6V16h6.7zM6 7h4v1.5H6V7zm8.35 10.15l3.94-3.94a1 1 0 0 1 1.42 0l3.94 3.94a.5.5 0 0 1 0 .7l-.8.8a.5.5 0 0 1-.7 0L20 16.5v6a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-6l-2.15 2.15a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7z"/>
      """.stripMargin
  }
  object NameUploadMultipleDocs extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M18 11.09v-.67L15.59 8H8v13h8v1.5c0 .17.02.34.05.5H7a1 1 0 0 1-1-1V7a1 1 0 0 1 1-1h9a1 1 0 0 1 .7.3l3 3a1 1 0 0 1 .3.71v1.07a3.01 3.01 0 0 0-2 0zM12.4 2H2v16H1a1 1 0 0 1-1-1V1a1 1 0 0 1 1-1h9a1 1 0 0 1 .7.3L12.4 2zm3 3H5v15H4a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h9a1 1 0 0 1 .7.3L15.4 5zm-.73 9l-1.5 1.5H10V14h4.67zm-2.41 3c-.1.5-.05 1.03.15 1.5H10V17h2.26zM10 11h5v1.5h-5V11zm4.35 6.15l3.94-3.94a1 1 0 0 1 1.42 0l3.94 3.94a.5.5 0 0 1 0 .7l-.8.8a.5.5 0 0 1-.7 0L20 16.5v6a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-6l-2.15 2.15a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7z"/>
      """.stripMargin
  }
  object NameUploadDraft extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M18 11.09V4H4v17h12v1.5c0 .17.02.34.05.5H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2v7.08a3.01 3.01 0 0 0-2 0zm-2 1.58l-.83.83H6V12h10v.67zM12.7 16c-.33.44-.5.97-.5 1.5H6V16h6.7zM6 8h10v1.5H6V8zm8.35 9.15l3.94-3.94a1 1 0 0 1 1.42 0l3.94 3.94a.5.5 0 0 1 0 .7l-.8.8a.5.5 0 0 1-.7 0L20 16.5v6a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-6l-2.15 2.15a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7z"/>
      """.stripMargin
  }
  object NameUploadSignedDoc extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M18 10h-7V3H4v18h12v1.5c0 .17.02.34.05.5H3a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h10a1 1 0 0 1 .7.3l6 6a1 1 0 0 1 .3.71v3.08a3.01 3.01 0 0 0-2 0V10zm-.42-2L13 3.42V8h4.58zm-4.18 7.28l-.46.45a2.49 2.49 0 0 0-.73 1.63c-.54.42-1.05.64-1.71.64-.97 0-1.31-.72-1.3-1.57.02-.35.06-.58.21-1.39l.05-.22.1-.6c-.51.45-1.1 1.58-1.61 3.1a1 1 0 1 1-1.9-.64C7.02 13.77 8.11 12 10 12c.93 0 1.47.65 1.56 1.52.05.45 0 .93-.14 1.66l-.04.24-.02.05.18-.13c.3-.21.6-.34.96-.34.3 0 .51.08.87.27h.03zm.95 1.87l3.94-3.94a1 1 0 0 1 1.42 0l3.94 3.94a.5.5 0 0 1 0 .7l-.8.8a.5.5 0 0 1-.7 0L20 16.5v6a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-6l-2.15 2.15a.5.5 0 0 1-.7 0l-.8-.8a.5.5 0 0 1 0-.7z"/>
      """.stripMargin
  }
  object NameViewSignRequest extends Name {
    override val content: String =
      """
        |  <path fill="#4F6F89" fill-rule="evenodd" d="M4 14.59l1.3-1.3a1 1 0 1 1 1.4 1.42L5.42 16l1.3 1.3a1 1 0 0 1-1.42 1.4L4 17.42l-1.3 1.3a1 1 0 1 1-1.4-1.42L2.58 16l-1.3-1.3a1 1 0 0 1 1.42-1.4L4 14.58zm0-12l1.3-1.3a1 1 0 1 1 1.4 1.42L5.42 4l1.3 1.3a1 1 0 0 1-1.42 1.4L4 5.42l-1.3 1.3a1 1 0 0 1-1.4-1.42L2.58 4l-1.3-1.3a1 1 0 1 1 1.42-1.4L4 2.58zM2 11a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zM2 23a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm3 0a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
      """.stripMargin
  }
  // scalastyle:on line.size.limit
}
// scalastyle:off number.of.types number.of.methods
