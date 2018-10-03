// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.textmask
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

object TextMaskAddons {

  @JSImport("text-mask-addons/dist/createNumberMask", JSImport.Default)
  @js.native
  object Number extends js.Object {
    // scalastyle:off parameter.number
    @JSName("apply")
    def createMask(
      prefix: js.UndefOr[String] = js.undefined,
      suffix: js.UndefOr[String] = js.undefined,
      includeThousandsSeparator: js.UndefOr[Boolean] = js.undefined,
      thousandsSeparatorSymbol: js.UndefOr[String] = js.undefined,
      allowDecimal: js.UndefOr[Boolean] = js.undefined,
      decimalSymbol: js.UndefOr[String] = js.undefined,
      decimalLimit: js.UndefOr[Number] = js.undefined,
      integerLimit: js.UndefOr[Number] = js.undefined,
      requireDecimal: js.UndefOr[Boolean] = js.undefined,
      allowNegative: js.UndefOr[Boolean] = js.undefined,
      allowLeadingZeroes: js.UndefOr[Boolean] = js.undefined
    ): TextMask.RawFunc = js.native
  }
  // scalastyle:on parameter.number

  @JSImport("text-mask-addons/dist/emailMask", JSImport.Default)
  @js.native
  object Email extends js.Object {
    @JSName("apply")
    def createMask(): TextMask.RawObject = js.native
  }
}
