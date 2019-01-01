// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.datefns

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.{Date, |}

@JSImport("date-fns/parse", JSImport.Default)
@js.native
object ParseDate extends js.Object {
  def apply(date: Date | String, pattern: String): Date = js.native
}
