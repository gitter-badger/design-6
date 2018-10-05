// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.textmask
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

object TextMaskAddons {

  class NumberConfig(
    val prefix: js.UndefOr[String] = js.undefined,
    val suffix: js.UndefOr[String] = js.undefined,
    val includeThousandsSeparator: js.UndefOr[Boolean] = js.undefined,
    val thousandsSeparatorSymbol: js.UndefOr[String] = js.undefined,
    val allowDecimal: js.UndefOr[Boolean] = js.undefined,
    val decimalSymbol: js.UndefOr[String] = js.undefined,
    val decimalLimit: js.UndefOr[Number] = js.undefined,
    val integerLimit: js.UndefOr[Number] = js.undefined,
    val requireDecimal: js.UndefOr[Boolean] = js.undefined,
    val allowNegative: js.UndefOr[Boolean] = js.undefined,
    val allowLeadingZeroes: js.UndefOr[Boolean] = js.undefined
  ) extends js.Object

  @JSImport("text-mask-addons/dist/createNumberMask", JSImport.Default)
  @js.native
  object Number extends js.Object {
    def apply(
      config: js.UndefOr[NumberConfig] = js.undefined
    ): TextMask.Raw = js.native
  }

  @JSImport("text-mask-addons/dist/emailMask", JSImport.Default)
  @js.native
  object Email extends js.Object
}
