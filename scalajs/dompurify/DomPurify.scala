// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.dompurify

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("dompurify", JSImport.Default, "createDOMPurify")
@js.native
object DomPurify extends js.Object {
  def sanitize(value: String): String = js.native
}
