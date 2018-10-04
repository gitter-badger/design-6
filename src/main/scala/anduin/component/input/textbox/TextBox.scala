// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input.textbox

import anduin.style.Style

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

  // ===

  sealed abstract class Status
  case object StatusNone extends Status
  case object StatusBusy extends Status
  case object StatusValid extends Status
  case object StatusInvalid extends Status

  // ===

  private def onChange(props: Props)(e: ReactEventFromInput): Callback = {
    props.onChange(e.target.value)
  }

  private def renderInput(props: Props): VdomElement = {
    val common = TagMod(
      TextBoxStyle.getInput(props),
      // ---
      ^.value := props.value,
      ^.onChange ==> onChange(props),
      // ---
      ^.disabled := props.isDisabled,
      ^.readOnly := props.isReadOnly,
      ^.onFocus --> props.onFocus,
      ^.placeholder := props.placeholder,
      ^.autoFocus := props.isAutoFocus
    )
    props.tpe match {
      case TpeSingle     => <.input(^.tpe := "text", common)
      case area: TpeArea => <.textarea(^.rows := area.rows, common)
    }
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
