// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.datefns

import scala.scalajs.js
import scala.scalajs.js.{Date, |}
import scala.scalajs.js.annotation.JSImport

@JSImport("date-fns/difference_in_seconds", JSImport.Default)
@js.native
object DifferenceInSeconds extends js.Object {
  def apply(dateLeft: Date | Double, dateRight: Date | Double): Int = js.native
}
