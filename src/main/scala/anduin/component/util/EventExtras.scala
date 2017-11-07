// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.util

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

import org.scalajs.dom

object EventExtras {

  final class EventInit(
    val bubbles: js.UndefOr[Boolean] = js.undefined,
    val cancelable: js.UndefOr[Boolean] = js.undefined,
    val scoped: js.UndefOr[Boolean] = js.undefined,
    val composed: js.UndefOr[Boolean] = js.undefined
  ) extends js.Object

  @js.native
  @JSGlobal("Event")
  class EventWithType(val typeArg: String, val init: js.UndefOr[EventInit] = js.undefined) extends dom.Event
}
