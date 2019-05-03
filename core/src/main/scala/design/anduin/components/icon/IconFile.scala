// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.icon

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// No "sealed" because IconFolder should use this
trait IconFile extends Icon.Name {
  def path16: String
  def path24: String
  def path32: String
  final def sizeMods: Seq[Icon.SizeMod] = Vector(
    Icon.SizeMod(Icon.Size.Px32, ^.dangerouslySetInnerHtml := path32),
    Icon.SizeMod(Icon.Size.Px24, ^.dangerouslySetInnerHtml := path24),
    Icon.SizeMod(Icon.Size.Px16, ^.dangerouslySetInnerHtml := path16)
  )
}

// scalastyle:off line.size.limit
object IconFile {
  // File Extensions
  case class ByExtension(extension: String) extends IconFile {
    val icon: IconFile = extension.toLowerCase match {
      case e: String if e.endsWith("pdf")                       => Pdf
      case e: String if e.endsWith("doc") || e.endsWith("docx") => Word
      case e: String if e.endsWith("zip") || e.endsWith("rar")  => Archive
      case e: String if e.endsWith("txt")                       => Text
      case _                                                    => Generic
    }
    def path16: String = icon.path16
    def path24: String = icon.path24
    def path32: String = icon.path32
  }
  object Generic extends IconFile {
    val path16: String =
      """
        |  <path d="M3 1h9l3 4v11H3V1z" fill="#000" fill-opacity=".1"/>
        |  <path d="M2.5.5h8.25l2.75 3.67V14.5h-11V.5z" fill="#F7FAFC" stroke="#9BB0C2"/>
      """.stripMargin
    val path24: String =
      """
        |  <path d="M5 3h10.6L21 9v14H5V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M4.5 2.5h9.88l5.12 5.7v13.3h-15v-19z" fill="#F7FAFC" stroke="#9BB0C2"/>
      """.stripMargin
    val path32: String =
      """
        |  <path d="M6 3h15l7 8v20H6V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M5.5 2.5h14.27l6.73 7.69V29.5h-21v-27z" fill="#F7FAFC" stroke="#9BB0C2"/>
      """.stripMargin
  }
  object Pdf extends IconFile {
    val path16: String =
      """
        |  <path d="M3 1h9l3 4v11H3V1z" fill="#000" fill-opacity=".1"/>
        |  <path d="M2.5.5h8.25l2.75 3.67V14.5h-11V.5z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M4.65 12c1 1.23 4.66-6.8 2.9-6.5-1.76.3 2.85 6.69 3.9 5 1.05-1.69-7.8.27-6.8 1.5z" stroke="#809AAD"/>
      """.stripMargin
    val path24: String =
      """
        |  <path d="M6 3h11l4 4v16H6V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M4.5 2.5h9.88l5.12 5.7v13.3h-15v-19z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M7.67 17.5c1.66 2 6.33-10.39 3.47-9.89S15 17.34 16 15c1-2.34-10 .5-8.33 2.5z" stroke="#F55656"/>
      """.stripMargin
    val path32: String =
      """
        |  <path d="M6 3h15l7 8v20H6V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M5.5 2.5h14.27l6.73 7.69V29.5h-21v-27z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M10 23c2 2.5 8.5-12.5 5-12s4.5 12 7 9-14 .5-12 3z" stroke="#F55656" stroke-width="1.23"/>
      """.stripMargin
  }
  object Word extends IconFile {
    val path16: String =
      """
        |  <path d="M3 1h8l4 4v11H3V1z" fill="#000" fill-opacity=".1"/>
        |  <path d="M2.5.5h7.3l3.7 3.7v10.3h-11V.5z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M7 12H5.55L4 7h1.5l.86 3.32L7.5 7h1l1.16 3.32L10.5 7H12l-1.55 5H9L8 9l-1 3z" fill="#809AAD"/>
      """.stripMargin
    val path24: String =
      """
        |  <path d="M6 3h11l4 4v16H6V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M4.5 2.5h8.9l6.1 5.72V21.5h-15v-19z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M10.7 18H9l-2-8h2l1 5.3 1-5.3h2l1 5.3 1-5.3h2l-2 8h-1.7L12 13l-1.3 5z" fill="#2B95D6"/>
      """.stripMargin
    val path32: String =
      """
        |  <path d="M6 3h15l7 8v20H6V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M5.5 2.5h13.3l7.7 7.7v19.3h-21v-27z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M14.5 23h-2.2L10 14h2.5l1 6 1.5-6h2l1.5 6 1-6H22l-2.3 9h-2.2L16 17.5 14.5 23z" fill="#2B95D6"/>
      """.stripMargin
  }
  object Text extends IconFile {
    val path16: String =
      """
        |  <path d="M3 1h10l2 2v13H3V1z" fill="#000" fill-opacity=".1"/>
        |  <path d="M2.5.5h8.25l2.75 3.67V14.5h-11V.5z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M5 5.5h6M5 8.5h6M5 11.5h4" stroke="#9BB0C2"/>
      """.stripMargin
    val path24: String =
      """
        |  <path d="M5 3h11l5 6v14H5V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M4.5 2.5h10.27l4.73 5.68V21.5h-15v-19z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M7 8.5h6M7 11.5h10M7 14.5h10M7 17.5h6" stroke="#9BB0C2"/>
      """.stripMargin
    val path32: String =
      """
        |  <path d="M6 3h15l7 8v20H6V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M5.5 2.5h14.27l6.73 7.69V29.5h-21v-27z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M9 8.5h6M9 11.5h14M9 14.5h14M9 17.5h14M9 20.5h14M9 23.5h6" stroke="#9BB0C2"/>
      """.stripMargin
  }
  object Archive extends IconFile {
    val path16: String =
      """
        |  <path d="M3 1h10l2 2v13H3V1z" fill="#000" fill-opacity=".1"/>
        |  <path d="M2.5.5h8.25l2.75 3.67V14.5h-11V.5z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path fill="#9BB0C2" d="M5 1h2v1H5zM5 3h2v1H5zM5 5h2v1H5zM5 7h2v1H5zM5 9h2v1H5zM5 11h2v1H5zM5 13h2v1H5zM7 2h2v1H7zM7 4h2v1H7zM7 6h2v1H7zM7 8h2v1H7zM7 10h2v1H7zM7 12h2v1H7z"/>
      """.stripMargin
    val path24: String =
      """
        |  <path d="M5 3h11l5 6v14H5V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M4.5 2.5h10.27l4.73 5.68V21.5h-15v-19z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M9 3H7v1h2v1H7v1h2v1H7v1h2v1H7v1h2v1H7v6h4v-5H9v-1h2v-1H9V9h2V8H9V7h2V6H9V5h2V4H9V3zM8 16v-2h2v2H8z" fill="#9BB0C2"/>
      """.stripMargin
    val path32: String =
      """
        |  <path d="M6 3h15l7 8v20H6V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M5.5 2.5h14.27l6.73 7.69V29.5h-21v-27z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path fill="#9BB0C2" d="M10 3h4v2h-4zM10 7h4v2h-4zM13 5h4v2h-4zM13 9h4v2h-4zM10 11h4v2h-4zM13 13h4v2h-4zM10 15h4v2h-4zM13 17h4v2h-4z"/>
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M17 19h-7v6h7v-6zm-5 4v-2h3v2h-3z" fill="#9BB0C2"/>
      """.stripMargin
  }
  // Doc Stages
  object Categorized extends IconFile {
    val path16: String =
      """
        |  <path d="M0 4a1 1 0 0 1 1-1h2v8H1a1 1 0 0 1-1-1V4z" fill="#809AAD"/>
        |  <path d="M3 1h8l4 4v11H3V1z" fill="#000" fill-opacity=".1"/>
        |  <path d="M2.5.5h7.3l3.7 3.7v10.3h-11V.5z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M5 5.5h6M5 8.5h6M5 11.5h3.5" stroke="#9BB0C2"/>
      """.stripMargin
    val path24: String =
      """
        |  <path d="M1.5 5c0-.28.22-.5.5-.5h2.5v9H2a.5.5 0 0 1-.5-.5V5z" fill="#76D5AD" stroke="#15B371"/>
        |  <path d="M6 3h11l4 4v16H6V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M4.5 2.5h8.9l6.1 5.72V21.5h-15v-19z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M7 8.5h6M7 11.5h10M7 14.5h10M7 17.5h6" stroke="#9BB0C2"/>
      """.stripMargin
    val path32: String =
      """
        |  <path d="M2.5 7c0-.28.22-.5.5-.5h2.5v11H3a.5.5 0 0 1-.5-.5V7z" fill="#76D5AD" stroke="#15B371"/>
        |  <path d="M6 3h15l7 8v20H6V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M5.5 2.5h13.3l7.7 7.7v19.3h-21v-27z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M9 8.5h6M9 11.5h14M9 14.5h14M9 17.5h14M9 20.5h14M9 23.5h6" stroke="#9BB0C2"/>
      """.stripMargin
  }
  object Draft extends IconFile {
    val path16: String =
      """
        |  <path d="M3 1h8l4 4v11l-2-1-2 1-2-1-2 1-2-1-2 1V1z" fill="#000" fill-opacity=".1"/>
        |  <path d="M13.5 4.2V14l-1.2-.9-.3-.22-.3.22-1.7 1.28-1.7-1.28-.3-.22-.3.22L6 14.38 4.3 13.1l-.3-.22-.3.22-1.2.9V.5h7.3l3.7 3.7z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M5 5.5h6M5 8.5h4" stroke="#9BB0C2"/>
      """.stripMargin
    val path24: String =
      """
        |  <path d="M5 3h9.6L21 9v14l-2-2-2 2-2-2-2 2-2-2-2 2-2-2-2 2V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M19.5 8.22v12.57l-1.15-1.14-.35-.36-.35.36L16 21.29l-1.65-1.64-.35-.36-.35.36L12 21.29l-1.65-1.64-.35-.36-.35.36L8 21.29l-1.65-1.64-.35-.36-.35.36-1.15 1.14V2.5h8.9l6.1 5.72z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M7 8.5h6M7 11.5h10M7 14.5h10" stroke="#9BB0C2"/>
      """.stripMargin
    val path32: String =
      """
        |  <path d="M6 3h14l8 8v19l-2 2-2-2-2 2-2-2-2 2-2-2-2 2-1.98-2L10 32l-2-2-2 2V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M26.5 28.8L25 30.3l-1.65-1.65-.35-.36-.35.36L21 30.29l-1.65-1.64-.35-.36-.35.36L17 30.29l-1.65-1.64-.35-.36-.35.36L13 30.29l-1.63-1.64-.35-.36-.35.35L9 30.3l-1.65-1.64-.35-.36-.35.36-1.15 1.14V2.5h13.3l7.7 7.7v18.6z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M9 8.5h6M9 11.5h14M9 14.5h14M9 17.5h14M9 20.5h14M9 23.5h6" stroke="#9BB0C2"/>
      """.stripMargin
  }
  object Redline extends IconFile {
    val path16: String =
      """
        |  <path d="M3 1h8l4 4v11H3V1z" fill="#000" fill-opacity=".1"/>
        |  <path d="M2.5.5h7.3l3.7 3.7v10.3h-11V.5z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M9.97 11c-.31 0-.56-.09-.74-.26A1.29 1.29 0 0 1 8.9 10h-.07c-.1.37-.29.66-.58.85-.3.18-.66.27-1.09.27-.59 0-1.04-.15-1.35-.46-.32-.3-.48-.72-.48-1.23 0-.6.22-1.04.64-1.32.43-.3 1.03-.44 1.82-.44h.98v-.42c0-.32-.09-.57-.26-.75-.16-.17-.44-.26-.81-.26-.33 0-.6.07-.8.22-.2.14-.37.3-.5.5l-.85-.74c.22-.33.5-.6.85-.8.35-.2.82-.3 1.4-.3.78 0 1.37.17 1.77.52.4.36.6.86.6 1.52v2.7h.58V11h-.78zm-2.34-.9c.32 0 .59-.06.8-.2a.68.68 0 0 0 .34-.62v-.75h-.9c-.74 0-1.1.23-1.1.7v.19c0 .23.07.4.21.52.16.11.38.17.65.17z" fill="#809AAD"/>
        |  <path fill="#809AAD" d="M4 8h8v1H4z"/>
      """.stripMargin
    val path24: String =
      """
        |  <path d="M5 3h9.6L21 9v14H5V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M4.5 2.5h8.9l6.1 5.72V21.5h-15v-19z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M15.27 18c-.43 0-.76-.12-1-.36a1.68 1.68 0 0 1-.41-.95h-.08c-.15.5-.42.86-.82 1.11-.4.25-.89.38-1.46.38-.8 0-1.43-.21-1.87-.63a2.26 2.26 0 0 1-.65-1.7c0-.78.28-1.36.84-1.75a4.4 4.4 0 0 1 2.5-.58h1.4v-.66c0-.48-.13-.85-.39-1.12-.26-.26-.66-.38-1.21-.38-.46 0-.84.1-1.13.3-.29.2-.53.45-.73.76l-.98-.88c.26-.44.63-.8 1.1-1.08a3.48 3.48 0 0 1 1.84-.44c1.01 0 1.79.24 2.33.7.54.47.8 1.15.8 2.03v3.91h.83V18h-.91zm-3.3-1.07c.5 0 .93-.1 1.26-.32.33-.23.5-.54.5-.92v-1.13h-1.39c-1.13 0-1.7.35-1.7 1.05v.28c0 .34.12.61.35.79.24.17.57.25.98.25z" fill="#FF7373"/>
        |  <path fill="#FF7373" d="M6 14h11v1H6z"/>
      """.stripMargin
    val path32: String =
      """
        |  <path d="M6 3h15l7 8v20H6V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M5.5 2.5h13.3l7.7 7.7v19.3h-21v-27z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M12.23 20a.93.93 0 0 1-.67-.24 1.17 1.17 0 0 1-.3-.66h-.06c-.1.34-.27.6-.53.77-.27.17-.6.25-1 .25-.53 0-.94-.14-1.22-.42a1.5 1.5 0 0 1-.43-1.12c0-.54.19-.94.58-1.2.38-.27.93-.4 1.65-.4h.89v-.38c0-.3-.08-.52-.23-.68-.16-.16-.4-.24-.74-.24-.3 0-.55.07-.73.2-.18.13-.34.28-.46.46l-.76-.68c.19-.3.45-.54.77-.72a2.5 2.5 0 0 1 1.27-.28c.7 0 1.24.16 1.6.48.37.32.56.78.56 1.38v2.46h.52V20h-.71zm-2.12-.81c.28 0 .53-.06.73-.19.2-.13.3-.31.3-.56v-.69h-.82c-.67 0-1 .21-1 .64v.17c0 .21.06.37.2.48a1 1 0 0 0 .59.15zm3.86-6.59h1.28v3.04h.04c.1-.3.27-.54.53-.71.26-.18.56-.27.91-.27.67 0 1.17.24 1.52.71.35.47.53 1.14.53 2.01 0 .88-.18 1.56-.53 2.03-.35.47-.85.71-1.52.71-.35 0-.65-.09-.9-.27a1.4 1.4 0 0 1-.54-.72h-.04V20h-1.28v-7.4zm2.35 6.46c.33 0 .6-.11.81-.33.2-.22.31-.52.31-.89v-.9c0-.37-.1-.67-.3-.89-.22-.23-.49-.34-.82-.34-.3 0-.56.08-.77.23-.2.15-.3.36-.3.61v1.66c0 .27.1.48.3.63.2.15.46.22.77.22zm5.7 1.06c-.38 0-.72-.06-1.02-.19-.3-.13-.56-.31-.76-.55-.2-.24-.36-.53-.46-.86a3.8 3.8 0 0 1-.16-1.14c0-.42.05-.8.16-1.13.1-.33.26-.62.46-.85.2-.24.46-.42.76-.55.3-.13.64-.19 1.03-.19.52 0 .96.12 1.3.35.34.23.6.56.75.97l-1.05.47c-.06-.22-.18-.4-.34-.54a.94.94 0 0 0-.66-.22c-.36 0-.63.11-.81.34-.18.23-.26.52-.26.89v.95c0 .37.08.66.26.89.18.22.45.33.8.33.31 0 .55-.08.72-.23.16-.16.3-.36.39-.59l.98.47c-.18.46-.44.8-.8 1.04-.35.23-.78.34-1.28.34z" fill="#FF7373"/>
        |  <path fill="#FF7373" d="M7 17h18v1H7z"/>
      """.stripMargin
  }
  object Final extends IconFile {
    val path16: String =
      """
        |  <path d="M3 1h8l4 4v11H3V1z" fill="#000" fill-opacity=".1"/>
        |  <path d="M2.5.5h7.3l3.7 3.7v10.3h-11V.5z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M5 5.5h6M5 8.5h6M5 11.5h3.5" stroke="#9BB0C2"/>
        |  <path d="M11.22 16.45l1.78-.9 1.78.9.72.36v-3.36A3.49 3.49 0 0 0 13 7.5a3.5 3.5 0 0 0-2.5 5.95V16.8l.72-.35z" fill="#9BB0C2" stroke="#fff"/>
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M13 12a1 1 0 1 0 0-2 1 1 0 0 0 0 2z" fill="#fff"/>
      """.stripMargin
    val path24: String =
      """
        |  <path d="M5 3h9.6L21 9v14H5V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M4.5 2.5h8.9l6.1 5.72V21.5h-15v-19z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M7 8.5h6M7 11.5h10M7 14.5h10M7 17.5h6" stroke="#9BB0C2"/>
        |  <path d="M20.72 24.42l.78.51v-5.1a4.5 4.5 0 1 0-7 0v5.1l.78-.51L18 22.6l2.72 1.82zM18 15.5c.65 0 1.2.42 1.41 1H16.6c.2-.58.76-1 1.41-1z" fill="#15B371" stroke="#fff"/>
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M18 19a2 2 0 1 0 0-4 2 2 0 0 0 0 4z" fill="#fff"/>
      """.stripMargin
    val path32: String =
      """
        |  <path d="M6 3h15l7 8v20H6V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M5.5 2.5h13.3l7.7 7.7v19.3h-21v-27z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M9 8.5h6M9 11.5h14M9 14.5h14M9 17.5h14M9 20.5h14M9 23.5h6" stroke="#9BB0C2"/>
        |  <path d="M25 28a8 8 0 1 0 0-16 8 8 0 0 0 0 16z" fill="#fff"/>
        |  <path d="M24.78 29.55L21.5 31.2V22.5h7v8.7l-3.28-1.65-.22-.1-.22.1z" fill="#76D5AD" stroke="#0F9960"/>
        |  <path d="M25.22 25.16l-.22-.1-.22.1-1.73.84-.91-1.7-.11-.2-.24-.05-1.9-.34.26-1.9.04-.25-.17-.17L18.7 20l1.33-1.39.17-.17-.04-.24-.25-1.9 1.89-.35.24-.04.11-.22.91-1.69 1.73.84.22.1.22-.1 1.73-.84.91 1.7.11.2.24.05 1.9.34-.26 1.9-.04.25.17.17L31.3 20l-1.33 1.39-.17.17.04.24.25 1.9-1.89.35-.24.04-.11.22-.91 1.69-1.73-.84z" fill="#76D5AD" stroke="#0F9960"/>
        |  <path d="M27.5 20a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0z" fill="#fff" stroke="#0F9960"/>
      """.stripMargin
  }
  object Signed extends IconFile {
    val path16: String =
      """
        |  <path d="M3 1h10l2 2v13H3V1z" fill="#000" fill-opacity=".1"/>
        |  <path d="M2.5.5h7.3l3.7 3.7v10.3h-11V.5z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M4.75 12.25l3-4.5v4.5L10 9.5v2.75h1.25" stroke="#809AAD" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
      """.stripMargin
    val path24: String =
      """
        |  <path d="M5 3h9.6L21 9v14H5V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M4.5 2.5h8.9l6.1 5.72V21.5h-15v-19z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M8.25 18.25C8.25 14.5 11.5 11.5 12 12s-2 6.25-.5 6.25 2-2.5 3-2.5-.5 2.5.5 2.5h1" stroke="#66C" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
      """.stripMargin
    val path32: String =
      """
        |  <path d="M6 3h15l7 8v20H6V3z" fill="#000" fill-opacity=".1"/>
        |  <path d="M5.5 2.5h13.3l7.7 7.7v19.3h-21v-27z" fill="#F7FAFC" stroke="#9BB0C2"/>
        |  <path d="M9 8.5h6M9 11.5h14M9 14.5h14M9 17.5h4" stroke="#9BB0C2"/>
        |  <path d="M12.75 26.25c0-3.12 2.58-6.6 3.61-6.22 1.03.37-2.58 5.97-.77 5.97 1.8 0 1.98-2.64 2.83-2.61.86.02-.77 2.61.26 2.61s1.3-.93 2-.93c.2 0 .52.93 1.04.93h1.03" stroke="#66C" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
      """.stripMargin
  }
  // Folder
  object Folder extends IconFolder {
    val color: IconFolder.Color = IconFolder.Color.Brown
    val glyph: Option[IconGlyph] = None
  }
  object FolderEmpty extends IconFolder {
    val color: IconFolder.Color = IconFolder.Color.Gray
    val glyph: Option[IconGlyph] = None
  }
  object Box extends IconFile {
    val path16: String =
      """
        |  <path fill-rule="evenodd" clip-rule="evenodd" d="M16 3H2v4h1v7a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V7h1V3z" fill="#000" fill-opacity=".1"/>
        |  <path fill="url(#icon_box_16_0)" stroke="#D6A06B" d="M1.5 2.5h13v3h-13z"/>
        |  <path d="M2.5 6c0-.28.22-.5.5-.5h10c.28 0 .5.22.5.5v7a.5.5 0 0 1-.5.5H3a.5.5 0 0 1-.5-.5V6z" fill="url(#icon_box_16_1)" stroke="#D6A06B"/>
        |  <path fill="#fff" stroke="#D6A06B" stroke-linejoin="round" d="M4.5 8.5h7v3h-7z"/>
        |  <defs>
        |    <linearGradient id="icon_box_16_0" x1="-6" y1="4" x2="-3.89" y2="11.4" gradientUnits="userSpaceOnUse">
        |      <stop stop-color="#E9D799"/>
        |      <stop offset="1" stop-color="#E3AC81"/>
        |    </linearGradient>
        |    <linearGradient id="icon_box_16_1" x1="-4" y1="9.5" x2="4.64" y2="21.02" gradientUnits="userSpaceOnUse">
        |      <stop stop-color="#E9D799"/>
        |      <stop offset="1" stop-color="#E3AC81"/>
        |    </linearGradient>
        |  </defs>
      """.stripMargin
    val path24: String =
      """
        |  <path d="M4 9a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V9z" fill="#000" fill-opacity=".1"/>
        |  <path d="M3.5 8c0-.28.22-.5.5-.5h16c.28 0 .5.22.5.5v12a.5.5 0 0 1-.5.5H4a.5.5 0 0 1-.5-.5V8z" fill="url(#icon_box_24_0)" stroke="#D6A06B"/>
        |  <path fill="#fff" stroke="#D6A06B" stroke-linejoin="round" d="M6.5 12.5h11v5h-11z"/>
        |  <rect x="2" y="4" width="22" height="5" rx="1" fill="#000" fill-opacity=".1"/>
        |  <rect x="1.5" y="3.5" width="21" height="4" rx=".5" fill="url(#icon_box_24_1)" stroke="#D6A06B"/>
        |  <defs>
        |    <linearGradient id="icon_box_24_0" x1="-6" y1="14" x2="7.57" y2="31.45" gradientUnits="userSpaceOnUse">
        |      <stop stop-color="#E9D799"/>
        |      <stop offset="1" stop-color="#E3AC81"/>
        |    </linearGradient>
        |    <linearGradient id="icon_box_24_1" x1="-10" y1="5.5" x2="-7.84" y2="15.01" gradientUnits="userSpaceOnUse">
        |      <stop stop-color="#E9D799"/>
        |      <stop offset="1" stop-color="#E3AC81"/>
        |    </linearGradient>
        |  </defs>
      """.stripMargin
    val path32: String =
      """
        |  <path d="M5 11a1 1 0 0 1 1-1h22a1 1 0 0 1 1 1v17a1 1 0 0 1-1 1H6a1 1 0 0 1-1-1V11z" fill="#000" fill-opacity=".1"/>
        |  <path d="M5 10.5h22c.28 0 .5.22.5.5v16a.5.5 0 0 1-.5.5H5a.5.5 0 0 1-.5-.5V11c0-.28.22-.5.5-.5z" fill="url(#icon_box_32_0)" stroke="#D6A06B"/>
        |  <path fill="#fff" stroke="#D6A06B" stroke-linejoin="round" d="M8.5 16.5h15v7h-15z"/>
        |  <rect x="3" y="5" width="28" height="7" rx="1" fill="#000" fill-opacity=".1"/>
        |  <rect x="2.5" y="4.5" width="27" height="6" rx=".5" fill="url(#icon_box_32_1)" stroke="#D6A06B"/>
        |  <defs>
        |    <linearGradient id="icon_box_32_0" x1="-8" y1="19" x2="9.28" y2="42.04" gradientUnits="userSpaceOnUse">
        |      <stop stop-color="#E9D799"/>
        |      <stop offset="1" stop-color="#E3AC81"/>
        |    </linearGradient>
        |    <linearGradient id="icon_box_32_1" x1="-12" y1="7.5" x2="-8.71" y2="20.68" gradientUnits="userSpaceOnUse">
        |      <stop stop-color="#E9D799"/>
        |      <stop offset="1" stop-color="#E3AC81"/>
        |    </linearGradient>
        |  </defs>
      """.stripMargin
  }
  object BoxEmpty extends IconFile {
    val path16: String =
      """
        |  <path d="M3.5 7a1 1 0 0 1 1-1h10a1 1 0 0 1 1 1v7a1 1 0 0 1-1 1h-10a1 1 0 0 1-1-1V7z" fill="#000" fill-opacity=".1"/>
        |  <path d="M3 6c0-.28.22-.5.5-.5h10c.28 0 .5.22.5.5v7a.5.5 0 0 1-.5.5h-10A.5.5 0 0 1 3 13V6z" fill="url(#icon_box_empty_16)" stroke="#9BB0C2" stroke-linejoin="round"/>
        |  <path fill="#fff" stroke="#9BB0C2" stroke-linejoin="round" d="M5 8.5h7v3H5z"/>
        |  <path d="M1.7 4.79a.5.5 0 0 1 .38-.6l11.75-2.44a.5.5 0 0 1 .59.38l.4 1.9a.5.5 0 0 1-.38.6L2.7 7.08a.5.5 0 0 1-.6-.39l-.4-1.9z" fill="#DFE9F0" stroke="#9BB0C2" stroke-linejoin="round"/>
        |  <defs>
        |    <linearGradient id="icon_box_empty_16" x1="-1.68" y1="9.79" x2="5.97" y2="20.25" gradientUnits="userSpaceOnUse">
        |      <stop stop-color="#E7EEF2"/>
        |      <stop offset="1" stop-color="#CDDAE4"/>
        |    </linearGradient>
        |  </defs>
      """.stripMargin
    val path24: String =
      """
        |  <path d="M4.4 9.51a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1h-16a1 1 0 0 1-1-1v-12z" fill="#000" fill-opacity=".1"/>
        |  <path d="M3.9 8.51c0-.28.22-.5.5-.5h16c.27 0 .5.22.5.5v12a.5.5 0 0 1-.5.5h-16a.5.5 0 0 1-.5-.5v-12z" fill="url(#icon_box_empty_24)" stroke="#9BB0C2" stroke-linejoin="round"/>
        |  <path fill="#fff" stroke="#9BB0C2" stroke-linejoin="round" d="M6.9 13.01h11v5h-11z"/>
        |  <path d="M2.2 7.55a1 1 0 0 1 .78-1.19l19.57-4.09a1 1 0 0 1 1.18.77l.62 2.87a1 1 0 0 1-.77 1.2L4 11.18a1 1 0 0 1-1.18-.76L2.2 7.55z" fill="#000" fill-opacity=".1"/>
        |  <path d="M1.7 6.45a.5.5 0 0 1 .38-.6l19.57-4.09a.5.5 0 0 1 .6.39l.61 2.87a.5.5 0 0 1-.38.6L2.9 9.7a.5.5 0 0 1-.6-.38L1.7 6.45z" fill="#DFE9F0" stroke="#9BB0C2" stroke-linejoin="round"/>
        |  <defs>
        |    <linearGradient id="icon_box_empty_24" x1="-2.88" y1="14.96" x2="9.15" y2="30.81" gradientUnits="userSpaceOnUse">
        |      <stop stop-color="#E7EEF2"/>
        |      <stop offset="1" stop-color="#CDDAE4"/>
        |    </linearGradient>
        |  </defs>
      """.stripMargin
    val path32: String =
      """
        |  <path d="M5 13a1 1 0 0 1 1-1h22a1 1 0 0 1 1 1v16a1 1 0 0 1-1 1H6a1 1 0 0 1-1-1V13z" fill="#000" fill-opacity=".1"/>
        |  <path d="M4.6 12c0-.28.22-.5.5-.5h22c.28 0 .5.22.5.5v16a.5.5 0 0 1-.5.5h-22a.5.5 0 0 1-.5-.5V12z" fill="url(#icon_box_empty_32)" stroke="#9BB0C2" stroke-linejoin="round"/>
        |  <path fill="#fff" stroke="#9BB0C2" stroke-linejoin="round" d="M8.5 17.5h15.53v7H8.5z"/>
        |  <path d="M2.21 10a1 1 0 0 1 .78-1.2l26.35-5.32a1 1 0 0 1 1.17.76l1.08 4.8a1 1 0 0 1-.78 1.2L4.46 15.58a1 1 0 0 1-1.18-.76L2.21 10z" fill="#000" fill-opacity=".1"/>
        |  <path d="M1.7 8.9a.5.5 0 0 1 .39-.6l26.35-5.33a.5.5 0 0 1 .59.38l1.07 4.8a.5.5 0 0 1-.39.6L3.36 14.09a.5.5 0 0 1-.59-.38L1.7 8.9z" fill="#DFE9F0" stroke="#9BB0C2" stroke-linejoin="round"/>
        |  <defs>
        |    <linearGradient id="icon_box_empty_32" x1="-4.27" y1="20.58" x2="11.04" y2="41.49" gradientUnits="userSpaceOnUse">
        |      <stop stop-color="#E7EEF2"/>
        |      <stop offset="1" stop-color="#CDDAE4"/>
        |    </linearGradient>
        |  </defs>
      """.stripMargin
  }
}
// scalastyle:on line.size.limit
