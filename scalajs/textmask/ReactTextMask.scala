// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.textmask

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

import org.scalajs.dom.html

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:off underscore.import

object ReactTextMask {

  type Render = js.Function2[
    raw.React.RefFn[html.Input],
    js.Dictionary[js.Any],
    raw.React.Element
  ]

  type OnChange = js.Function1[ReactEventFromInput, Unit]

  class Props(
    mask: js.UndefOr[TextMask] = js.undefined,
    val onChange: js.UndefOr[OnChange] = js.undefined,
    val render: js.UndefOr[Render] = js.undefined
  ) extends js.Object {
    // We accept both
    @JSName("mask")
    val maskRaw: js.UndefOr[TextMask.Raw] = mask.map(TextMask.toRaw)
  }

  @JSImport("react-text-mask", JSImport.Default)
  @js.native
  object RawComponent extends js.Object

  val component = JsComponent[Props, Children.None, Null](RawComponent)
}
