// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.textbox

import anduin.scalajs.textmask.TextMask
import anduin.scalajs.textmask.TextMaskAddons
import anduin.scalajs.textmask.TextMaskAddons.NumberConfig

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

sealed trait TextBoxType {
  private[textbox] def tag: TextBoxType.Tag = <.input.text
  private[textbox] def placeholder: TextBoxType.Placeholder = None
  private[textbox] def mask: Option[TextBoxType.Mask] = None
  private[textbox] def isMultiLine: Boolean = false
}

object TextBoxType {

  private type Tag = VdomTag
  private[textbox] case class Mask(
    value: TextMask,
    keepCharPositions: Boolean = false,
    pipe: Option[TextMask.Pipe] = None
  )
  private type Placeholder = Option[String]

  // 1. <textarea rows="..." />

  trait Area extends TextBoxType {
    def rows: Int
    private[textbox] final override def tag: Tag = <.textarea(^.rows := rows)
    private[textbox] final override def isMultiLine: Boolean = true
  }

  // 2. <input type="..." /> (no TextMask)

  trait Password extends TextBoxType {
    private[textbox] final override def tag: Tag = <.input.password
  }

  trait DateNative extends TextBoxType {
    private[textbox] final override def tag: Tag = <.input.date
  }

  trait EmailNative extends TextBoxType {
    private[textbox] final override def placeholder: Placeholder = Some("@")
    private[textbox] final override def tag: Tag = <.input.email
  }

  trait Text extends TextBoxType

  // 3. <input type="text" /> with TextMask

  // These "createMask" are expensive. Don't call them on render. Instead, they
  // should be defined once here and use in respond to TextBox.Mask instances
  private val emailMask = Mask(TextMask.FromJS(TextMaskAddons.Email))
  trait EmailMask extends TextBoxType {
    private[textbox] final override def placeholder: Placeholder = Some("@")
    private[textbox] final override def mask: Option[Mask] = Some(emailMask)
  }

  private val dateMask = {
    val d = TextMask.RegExp("\\d")
    val s = TextMask.Char("/")
    val value = TextMask.Array(List(d, d, s, d, d, s, d, d, d, d))
    val pipe = Some(TextMaskAddons.AutoCorrectedDatePipe("mm/dd/yyyy"))
    Mask(value, keepCharPositions = true, pipe)
  }
  trait DateMask extends TextBoxType {
    private[textbox] final override def placeholder: Placeholder = Some("mm/dd/yyyy")
    private[textbox] final override def mask: Option[Mask] = Some(dateMask)
  }

  private val currencyMask = {
    val config = new NumberConfig(prefix = "$", allowDecimal = true, decimalLimit = 2)
    Mask(TextMask.FromJS(TextMaskAddons.Number(config)))
  }
  trait Currency extends TextBoxType {
    private[textbox] final override def placeholder: Placeholder = Some("$")
    private[textbox] final override def mask: Option[Mask] = Some(currencyMask)
  }

  private val percentageMask = {
    val config = new NumberConfig(prefix = "", suffix = "%", allowDecimal = true, decimalLimit = 3)
    Mask(TextMask.FromJS(TextMaskAddons.Number(config)))
  }
  trait Percentage extends TextBoxType {
    private[textbox] final override def placeholder: Placeholder = Some("%")
    private[textbox] final override def mask: Option[Mask] = Some(percentageMask)
  }

  private val numberIntMask = {
    val config = new NumberConfig(prefix = "")
    Mask(TextMask.FromJS(TextMaskAddons.Number(config)))
  }
  trait NumberInt extends TextBoxType {
    private[textbox] final override def mask: Option[Mask] = Some(numberIntMask)
  }

  private val numberFloatMask = {
    val config = new NumberConfig(prefix = "", allowDecimal = true, decimalLimit = 3)
    Mask(TextMask.FromJS(TextMaskAddons.Number(config)))
  }
  trait NumberFloat extends TextBoxType {
    private[textbox] final override def mask: Option[Mask] = Some(numberFloatMask)
  }

  trait Array extends TextBoxType {
    def items: List[TextMask.Item]
    private[textbox] final override def mask: Option[Mask] = Some(Mask(TextMask.Array(items)))
  }

  trait Func extends TextBoxType {
    def func: String => List[TextMask.Item]
    private def funcRaw: String => TextMask.Array = s => TextMask.Array(func(s))
    private[textbox] final override def mask: Option[Mask] = Some(Mask(TextMask.Func(funcRaw)))
  }
}
