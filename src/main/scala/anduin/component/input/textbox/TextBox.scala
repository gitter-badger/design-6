// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input.textbox

import anduin.scalajs.textmask.{ReactTextMask, TextMask}
import anduin.scalajs.util.Util
import anduin.style.Style
import org.scalajs.dom.html

import scala.scalajs.js

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class TextBox(
  value: String,
  onChange: String => Callback = _ => Callback.empty,
  onFocus: Callback = Callback.empty,
  placeholder: String = "",
  context: Option[VdomNode] = None,
  // ===
  tpe: TextBox.Tpe = TextBox.TpeSingle,
  status: TextBox.Status = TextBox.StatusNone,
  size: TextBox.Size = TextBox.Size32,
  mask: Option[TextBox.Mask] = None,
  // ===
  isDisabled: Boolean = false,
  isRequired: Boolean = false,
  isReadOnly: Boolean = false,
  isAutoFocus: Boolean = false
) {
  def apply(): VdomElement = TextBox.component(this)
}

object TextBox {

  private type Props = TextBox

  // Options

  sealed abstract class Tpe
  case object TpeSingle extends Tpe
  case class TpeArea(rows: Int = 3) extends Tpe

  sealed abstract class Size
  case object Size32 extends Size
  case object Size40 extends Size

  sealed abstract class Status
  case object StatusNone extends Status
  case object StatusBusy extends Status
  case object StatusValid extends Status
  case object StatusInvalid extends Status

  sealed abstract class Mask
  case object MaskEmail extends Mask
  case object MaskCurrency extends Mask
  case object MaskPercentage extends Mask
  case object MaskNumber extends Mask
  case class MaskArray(value: List[TextMask.Item]) extends Mask
  case class MaskFunc(value: String => TextMask.Array) extends Mask

  // ===

  private def onChange(props: Props)(e: ReactEventFromInput): Unit = {
    val value = e.target.value
    props.onChange(value).runNow()
  }

  private def renderTextMask(props: Props)(
    maskRef: raw.React.RefFn[html.Input],
    maskProps: js.Dictionary[js.Any]
  ): raw.React.Element = {
    val common = TagMod(
      TextBoxStyle.getInput(props),
      // These mods and ref will control `onChange`, `onBlur` and `value`
      Util.getModsFromProps(maskProps),
      VdomAttr("ref") := maskRef,
      // All other props
      ^.placeholder := props.placeholder,
      ^.onFocus --> props.onFocus,
      ^.disabled := props.isDisabled,
      ^.readOnly := props.isReadOnly,
      ^.autoFocus := props.isAutoFocus
    )
    val element = props.tpe match {
      case TpeSingle     => <.input.text(common)
      case area: TpeArea => <.textarea(^.rows := area.rows, common)
    }
    element.rawElement
  }

  private def renderInput(props: Props): VdomElement = {
    val maskProps = new ReactTextMask.Props(
      mask = props.mask.map(TextBoxMask.get),
      value = props.value,
      onChange = js.defined(onChange(props)),
      render = js.defined(renderTextMask(props))
    )
    ReactTextMask.component(maskProps)
  }

  private def render(props: Props): VdomElement = {
    <.div(
      Style.position.relative.flexbox.flex,
      props.context.map(node => <.div(TextBoxStyle.getContext(props), node)),
      renderInput(props),
      TextBoxStyle.getIcon(props)
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
