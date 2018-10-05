// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input.textbox

import anduin.scalajs.textmask.TextMask

// scalastyle:off underscore.import
import anduin.scalajs.textmask.TextMaskAddons._
// scalastyle:on underscore.import

private[textbox] object TextBoxMask {

  // These "createMask" are expensive. Don't call them on render. Instead, they
  // should be defined once here and use in respond to TextBox.Mask instances
  val email = Email
  val currency = Number()
  val percentage = Number(new NumberConfig(prefix = "", suffix = "%"))
  val number = Number(new NumberConfig(prefix = ""))

  def get(mask: TextBox.Mask): TextMask = mask match {
    case TextBox.MaskEmail        => TextMask.FromJS(email)
    case TextBox.MaskCurrency     => TextMask.FromJS(currency)
    case TextBox.MaskPercentage   => TextMask.FromJS(percentage)
    case TextBox.MaskNumber       => TextMask.FromJS(number)
    case array: TextBox.MaskArray => TextMask.Array(array.value)
    case func: TextBox.MaskFunc   => TextMask.Func(func.value)
  }
}
