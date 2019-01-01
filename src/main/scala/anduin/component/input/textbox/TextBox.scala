// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.textbox

import anduin.scalajs.textmask.{ReactTextMask, TextMask}
import anduin.scalajs.util.Util
import anduin.style.Style
import org.scalajs.dom.html.Input

import scala.scalajs.js

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class TextBox(
  value: String,
  onChange: String => Callback = _ => Callback.empty,
  onFocus: Callback = Callback.empty,
  onBlur: String => Callback = _ => Callback.empty,
  onKeyDown: ReactKeyboardEventFromInput => Callback = _ => Callback.empty,
  onKeyUp: ReactKeyboardEventFromInput => Callback = _ => Callback.empty,
  placeholder: String = "",
  context: Option[VdomNode] = None,
  // ===
  id: Option[String] = None,
  tpe: TextBoxType = TextBox.TpeText,
  status: TextBox.Status = TextBox.StatusNone,
  size: TextBoxSize = TextBox.Size32,
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

  object TpeText extends TextBoxType.Text
  object TpePassword extends TextBoxType.Password
  case class TpeArea(rows: Int = 3) extends TextBoxType.Area

  object Size32 extends TextBoxSize.Px32
  object Size40 extends TextBoxSize.Px40

  sealed abstract class Status
  case object StatusNone extends Status
  case object StatusBusy extends Status
  case object StatusValid extends Status
  case object StatusInvalid extends Status

  sealed abstract class Mask(private[TextBox] val placeholder: String)
  case object MaskEmail extends Mask("@")
  case object MaskCurrency extends Mask("$")
  case object MaskPercentage extends Mask("%")
  case object MaskNumber extends Mask("")
  case object MaskFloat extends Mask("")
  case class MaskCustomArray(value: List[TextMask.Item]) extends Mask("")
  case class MaskCustomFunc(value: String => TextMask.Array) extends Mask("")

  // only onChange and onBlur should be defined here because they are
  // controlled by TextMask

  private def onChange(props: Props)(e: ReactEventFromInput): Unit = {
    val value = e.target.value
    props.onChange(value).runNow()
  }

  private def onBlur(props: Props)(e: ReactEventFromInput): Unit = {
    val value = e.target.value
    props.onBlur(value).runNow()
  }

  // ===

  private def getPlaceholder(props: Props): String = {
    if (props.placeholder.nonEmpty) {
      props.placeholder
    } else {
      props.mask.fold("")(_.placeholder)
    }
  }

  private def renderTextMask(props: Props)(
    maskRef: raw.React.RefFn[Input],
    maskProps: js.Dictionary[js.Any]
  ): raw.React.Element = {
    val attrs = TagMod(
      TextBoxStyle.getInput(props),
      // These mods and ref will control `onChange`, `onBlur` and `value`
      Util.getModsFromProps(maskProps),
      VdomAttr("ref") := maskRef,
      // When "mask" is not defined TextMask won't update the "value" for us
      // so we need to bind it directly ourselves
      TagMod.when(props.mask.isEmpty)(^.value := props.value),
      // All other props
      ^.id :=? props.id,
      ^.placeholder := getPlaceholder(props),
      ^.onFocus --> props.onFocus,
      ^.onKeyDown ==> props.onKeyDown,
      ^.onKeyUp ==> props.onKeyUp,
      ^.disabled := props.isDisabled,
      ^.readOnly := props.isReadOnly,
      ^.autoFocus := props.isAutoFocus
    )
    props.tpe.tag(attrs).rawElement
  }

  private def renderInput(props: Props): VdomElement = {
    val maskProps = new ReactTextMask.Props(
      mask = props.mask.map(TextBoxMask.get),
      value = props.value,
      onChange = js.defined(onChange(props)),
      onBlur = js.defined(onBlur(props)),
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
