// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.textbox

import anduin.scalajs.textmask.TextMask
import anduin.scalajs.textmask.TextMaskAddons
import anduin.scalajs.textmask.TextMaskAddons.NumberConfig

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

sealed trait TextBoxTpe {
  private[textbox] def tag: VdomTag = <.input.text
  private[textbox] def placeholder: Option[String] = None
  private[textbox] def mask: Option[TextBoxTpe.Mask] = None
  private[textbox] def isMultiLine: Boolean = false
}

object TextBoxTpe {

  private[textbox] case class Mask(
    value: TextMask,
    keepCharPositions: Boolean = false,
    pipe: Option[TextMask.Pipe] = None
  )

  // 1. <textarea rows="..." />

  trait Area extends TextBoxTpe {
    def rows: Int
    private[textbox] final override def tag: VdomTag = <.textarea(^.rows := rows)
    private[textbox] final override def isMultiLine: Boolean = true
  }

  // 2. <input type="..." /> (no TextMask)

  trait Password extends TextBoxTpe {
    private[textbox] final override def tag: VdomTag = <.input.password
  }

  trait DateNative extends TextBoxTpe {
    private[textbox] final override def tag: VdomTag = <.input.date
  }

  trait EmailNative extends TextBoxTpe {
    private[textbox] final override def placeholder: Option[String] = Some("@")
    private[textbox] final override def tag: VdomTag = <.input.email
  }

  trait Text extends TextBoxTpe

  // 3. <input type="text" /> with TextMask

  // These "createMask" are expensive. Don't call them on render. Instead, they
  // should be defined once here and use in respond to TextBox.Mask instances
  private val emailMask = Mask(TextMask.FromJS(TextMaskAddons.Email))
  trait EmailMask extends TextBoxTpe {
    private[textbox] final override def placeholder: Option[String] = Some("@")
    private[textbox] final override def mask: Option[Mask] = Some(emailMask)
  }

  private val dateMask = {
    val d = TextMask.RegExp("\\d")
    val s = TextMask.Char("/")
    val value = TextMask.Array(List(d, d, s, d, d, s, d, d, d, d))
    val pipe = Some(TextMaskAddons.AutoCorrectedDatePipe("mm/dd/yyyy"))
    Mask(value, keepCharPositions = true, pipe)
  }
  trait DateMask extends TextBoxTpe {
    private[textbox] final override def placeholder: Option[String] = Some("mm/dd/yyyy")
    private[textbox] final override def mask: Option[Mask] = Some(dateMask)
  }

  private val currencyMask = {
    val config = new NumberConfig(prefix = "$", allowDecimal = true, decimalLimit = 2)
    Mask(TextMask.FromJS(TextMaskAddons.Number(config)))
  }
  trait Currency extends TextBoxTpe {
    private[textbox] final override def placeholder: Option[String] = Some("$")
    private[textbox] final override def mask: Option[Mask] = Some(currencyMask)
  }

  private val percentageMask = {
    val config = new NumberConfig(prefix = "", suffix = "%", allowDecimal = true, decimalLimit = 3)
    Mask(TextMask.FromJS(TextMaskAddons.Number(config)))
  }
  trait Percentage extends TextBoxTpe {
    private[textbox] final override def placeholder: Option[String] = Some("%")
    private[textbox] final override def mask: Option[Mask] = Some(percentageMask)
  }

  private val numberIntMask = {
    val config = new NumberConfig(prefix = "")
    Mask(TextMask.FromJS(TextMaskAddons.Number(config)))
  }
  trait NumberInt extends TextBoxTpe {
    private[textbox] final override def mask: Option[Mask] = Some(numberIntMask)
  }

  private val numberFloatMask = {
    val config = new NumberConfig(prefix = "", allowDecimal = true, decimalLimit = 3)
    Mask(TextMask.FromJS(TextMaskAddons.Number(config)))
  }
  trait NumberFloat extends TextBoxTpe {
    private[textbox] final override def mask: Option[Mask] = Some(numberFloatMask)
  }

  trait Array extends TextBoxTpe {
    def items: List[TextMask.Item]
    private[textbox] final override def mask: Option[Mask] = Some(Mask(TextMask.Array(items)))
  }

  trait Func extends TextBoxTpe {
    def func: String => List[TextMask.Item]
    private def funcRaw: String => TextMask.Array = s => TextMask.Array(func(s))
    private[textbox] final override def mask: Option[Mask] = Some(Mask(TextMask.Func(funcRaw)))
  }
}
