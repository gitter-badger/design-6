// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.icon

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.svg_<^._
// scalastyle:on underscore.import

sealed trait IconProduct extends Icon.Name {
  def bgColor: String
  final def bgMod: TagMod = vdom.html_<^.^.backgroundColor := bgColor

  protected def paths: String
  private def group = <.g(
    TagMod(^.fill := "none", ^.fillRule := "evenodd"),
    vdom.html_<^.^.dangerouslySetInnerHtml := paths
  )
  private[icon] final def sizeMods: Seq[Icon.SizeMod] =
    Vector(Icon.SizeMod(Icon.Size.Px32, group))
}

// scalastyle:off line.size.limit
object IconProduct {
  object Negotiation extends IconProduct {
    val bgColor: String = "#00A6B7"
    protected val paths: String =
      """
        |<path fill="#FFF" d="M7 4h13l6 6v17a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1V5a1 1 0 0 1 1-1z"/>
        |<path fill="#AEDEE6" d="M9 7h6v1.5H9V7zm0 4h14v1.5H9V11zm0 6h14v1.5H9V17zm0-3h14v1.5H9V14zm0 6h14v1.5H9V20zm0 3h10v1.5H9V23zM20 4v6h6z"/>
        |<path fill="#56C0CC" d="M4 7h2v8H4a1 1 0 0 1-1-1V8a1 1 0 0 1 1-1z"/>
      """.stripMargin
  }
  object LegalDiligence extends IconProduct {
    val bgColor: String = "#15B8E6"
    protected val paths: String =
      """
        |<path fill="#C5EDF8" d="M7.42 9h17.16a.5.5 0 0 1 .48.64l-.7 2.36H7.64l-.69-2.36A.5.5 0 0 1 7.42 9z"/>
        |<path fill="#78D6F0" d="M10.65 5h10.7a1 1 0 0 1 .96 1.28L21.8 8H10.2l-.51-1.72A1 1 0 0 1 10.65 5z"/>
        |<path fill="#FFF" d="M5.27 14h21.46a1 1 0 0 1 .97 1.23l-2.65 11a1 1 0 0 1-.97.77H7.92a1 1 0 0 1-.97-.77l-2.65-11A1 1 0 0 1 5.27 14zm8.21 2a.75.75 0 0 0 0 1.5h4.5a.75.75 0 1 0 0-1.5h-4.5z"/>
      """.stripMargin
  }
  object Signature extends IconProduct {
    val bgColor: String = "#9190E5"
    protected val paths: String =
      """
        |<path fill="#FFF" d="M24.98 9.1l-.01 7.12a4 4 0 0 1-1.53 3.14L18 23.65c-.7.55-1.73.44-2.29-.25a1.6 1.6 0 0 1-.18-.29l-3.09-6.13a4 4 0 0 1-.04-3.5l3.04-6.46c-.38-.28 10.02 1.99 9.55 2.08z"/>
        |<path fill="#7171D2" d="M15.03 5.76l.66-2.8a1 1 0 0 1 1.18-.76L26 4.12a1 1 0 0 1 .77 1.2l-.65 2.77c-.11.47-.5.81-.96.91L15.5 6.97a1.16 1.16 0 0 1-.47-1.21z"/>
        |<path fill="#C8C6F8" d="M5.18 17.89a31.63 31.63 0 0 0-2.19 8.23 1 1 0 0 1-1.98-.24 33.62 33.62 0 0 1 2.33-8.78c1.3-3.04 2.63-4.7 4.18-3.9.9.47.95 1.37.65 2.98-.16.87-.31 1.5-.92 3.74l-.1.36a27.35 27.35 0 0 0-.87 3.96c-.03.29-.03.52-.02.7.4-.19.8-.79 1.26-1.92l.6-1.55c.5-1.14.93-1.72 1.87-1.68 1.03.04 1.35.82 1.32 1.82-.02.45-.08.83-.27 1.85l-.02.12c-.1.51-.15.88-.19 1.16l.3-.32.34-.4c.5-.57.97-.88 1.64-.88.43 0 .69.2.9.47.08.1.15.21.23.36l.32.65c.08.17.16.3.22.38H16a1 1 0 1 1 0 2h-1.44c-.85 0-1.29-.5-1.77-1.45a29.83 29.83 0 0 0-.15.17c-.73.85-1.35 1.28-2.33 1.28-1.06 0-1.49-.79-1.5-1.8V25c-.71 1.31-1.55 2-2.84 2-1.47 0-1.86-1.21-1.68-2.97.11-1.03.38-2.22.93-4.26l.1-.37c.57-2.1.73-2.74.87-3.5-.32.5-.67 1.19-1.01 1.99z"/>
        |<path stroke="#9190E5" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M17 24l2-9"/>
        |<circle cx="19" cy="15" r="2" fill="#9190E5"/>
      """.stripMargin
  }
  object Wiring extends IconProduct {
    val bgColor: String = "#30A594"
    protected val paths: String =
      """
        |<path fill="#FFF" d="M7 23h19a1 1 0 0 1 1 1v1a1 1 0 0 1-1 1H5.5a.5.5 0 0 1-.5-.5v-2a.5.5 0 0 1 .5-.5H7zm19.5-11h-21a.5.5 0 0 1-.5-.5V9.35a.5.5 0 0 1 .33-.47L16 5l10.67 3.88a.5.5 0 0 1 .33.47v2.15a.5.5 0 0 1-.5.5z"/>
        |<path fill="#79E6D5" d="M16 10a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm-9 2h2v11H7zm4 1h2v9h-2zm4 0h2v9h-2zm4 0h2v9h-2zm4-1h2v11h-2z"/>
      """.stripMargin
  }
  object Closing extends IconProduct {
    val bgColor: String = "#0AAB68"
    protected val paths: String =
      """
        |<path fill="#FFF" d="M7 8.66l16.42-4.4a1 1 0 0 1 1.23.7l2.84 10.63-17.38 4.66L7 8.65z"/>
        |<path fill="#BEE6D5" d="M4 9v16a1 1 0 0 0 1 1h22a1 1 0 0 0 1-1V12a1 1 0 0 0-1-1h-9.91L14 8H5a1 1 0 0 0-1 1z"/>
      """.stripMargin
  }
}
// scalastyle:on line.size.limit
