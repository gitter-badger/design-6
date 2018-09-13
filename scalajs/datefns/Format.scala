// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.datefns

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.{Date, UndefOr}

@JSImport("date-fns/format", JSImport.Default)
@js.native
object Format extends js.Object {
  // See the format tokens at https://date-fns.org/v1.29.0/docs/format
  def apply(date: Date, format: UndefOr[String] = js.undefined): String = js.native
}
