// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.caja

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobal, JSGlobalScope, JSImport, JSName}

@js.native
@JSImport("./external/js/caja/html-css-sanitizer.js", JSImport.Namespace)
object HtmlCssSanitizerJs extends js.Object

@js.native
@JSGlobalScope
object HtmlSanitize extends js.Object {

  @JSName("html4")
  val html4: js.Dynamic = js.native

  @JSName("html_sanitize")
  def apply(
    html: String,
    urlTransformer: js.UndefOr[js.Function1[URI, String]] = js.undefined
  ): String = js.native
}

object Caja {

  def htmlSanitize(
    html: String,
    urlTransformer: js.UndefOr[js.Function1[URI, String]] = js.undefined
  ): String = {
    val _ = HtmlCssSanitizerJs
    HtmlSanitize.html4.ATTRIBS.updateDynamic("a::target")(HtmlSanitize.html4.atype.selectDynamic("URI"))
    HtmlSanitize(html, urlTransformer)
  }
}

@js.native
@JSGlobal
class URI extends js.Object
