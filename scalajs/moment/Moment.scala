// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.moment

// https://github.com/widok/scala-js-momentjs

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("moment-timezone", JSImport.Namespace, "Moment")
@js.native
object Moment extends js.Object {
  /* Long has different semantics than JavaScript's numbers, therefore Double
   * must be used.
   */
  def apply(value: String, format: String): Date = js.native
}
