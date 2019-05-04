// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.facades.textmask

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

  type EventListener = js.Function1[ReactEventFromInput, Unit]

  class Props(
    mask: Option[TextMask] = None,
    val keepCharPositions: js.UndefOr[Boolean] = js.undefined,
    val pipe: js.UndefOr[TextMask.Pipe] = js.undefined,
    val value: js.UndefOr[String] = js.undefined,
    val onChange: js.UndefOr[EventListener] = js.undefined,
    val onBlur: js.UndefOr[EventListener] = js.undefined,
    val render: js.UndefOr[Render] = js.undefined
  ) extends js.Object {
    // We accept both
    @JSName("mask")
    val maskRaw: js.UndefOr[TextMask.Raw] =
      js.defined(mask.fold[TextMask.Raw](false)(TextMask.toRaw))
  }

  @JSImport("react-text-mask", JSImport.Default)
  @js.native
  object RawComponent extends js.Object

  val component = JsComponent[Props, Children.None, Null](RawComponent)
}
