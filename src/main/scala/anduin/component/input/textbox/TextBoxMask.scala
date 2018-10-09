// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input.textbox

import anduin.scalajs.textmask.TextMask

// scalastyle:off underscore.import
import anduin.scalajs.textmask.TextMaskAddons._
// scalastyle:on underscore.import

private[textbox] object TextBoxMask {

  // These "createMask" are expensive. Don't call them on render. Instead, they
  // should be defined once here and use in respond to TextBox.Mask instances
  private val email = TextMask.FromJS(Email)
  private val currency = {
    val config = new NumberConfig(prefix = "$", allowDecimal = true, decimalLimit = 2)
    TextMask.FromJS(Number(config))
  }
  private val percentage = {
    val config = new NumberConfig(prefix = "", suffix = "%", allowDecimal = true, decimalLimit = 3)
    TextMask.FromJS(Number(config))
  }
  private val number = {
    val config = new NumberConfig(prefix = "")
    TextMask.FromJS(Number(config))
  }
  private val float = {
    val config = new NumberConfig(prefix = "", allowDecimal = true, decimalLimit = 3)
    TextMask.FromJS(Number(config))
  }

  def get(mask: TextBox.Mask): TextMask = mask match {
    case TextBox.MaskEmail        => email
    case TextBox.MaskCurrency     => currency
    case TextBox.MaskPercentage   => percentage
    case TextBox.MaskNumber       => number
    case TextBox.MaskFloat        => float
    case array: TextBox.MaskArray => TextMask.Array(array.value)
    case func: TextBox.MaskFunc   => TextMask.Func(func.value)
  }
}
