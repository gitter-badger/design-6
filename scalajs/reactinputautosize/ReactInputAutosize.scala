// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.reactinputautosize

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import org.scalajs.dom.raw.Event

object ReactInputAutosize {

  @JSImport("react-input-autosize", JSImport.Default, "AutosizeInput")
  @js.native
  object RawComponent extends js.Object

  // See https://github.com/JedWatson/react-input-autosize
  final class Props(
    val value: String = "",
    val onChange: js.UndefOr[js.Function1[Event, Unit]] = js.undefined
  ) extends js.Object
}
