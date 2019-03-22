// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.textbox

import anduin.scalajs.textmask.TextMask
import anduin.style.{Style => SStyle}

import anduin.component.icon.Icon

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// This component is only the container/wrapper. The actual control (e.g.
// `input` or `textarea`) is defined inside TextBoxBody. This component simply
// wraps it with overlay stuffs (e.g. icon and status) as well as exposes the
// full API
final case class TextBox(
  value: String,
  onChange: String => Callback = _ => Callback.empty,
  // Type
  tpe: TextBoxTpe = TextBox.Tpe.Text,
  placeholder: String = "",
  // Appearance
  style: TextBoxStyle = TextBox.Style.Full,
  icon: Option[Icon.Name] = None,
  status: TextBoxStatus = TextBox.Status.None,
  size: TextBoxSize = TextBox.Size.Px32,
  // Behaviour
  id: Option[String] = None,
  isDisabled: Boolean = false,
  isRequired: Boolean = false,
  isReadOnly: Boolean = false,
  isAutoFocus: Boolean = false,
  // Event listeners
  onFocus: Callback = Callback.empty,
  onBlur: String => Callback = _ => Callback.empty,
  onKeyDown: ReactKeyboardEventFromInput => Callback = _ => Callback.empty,
  onKeyUp: ReactKeyboardEventFromInput => Callback = _ => Callback.empty
) {
  def apply(): VdomElement = TextBox.component(this)
}

object TextBox {

  private type Props = TextBox

  object Tpe {
    // <textarea rows="..." />
    case class Area(rows: Int) extends TextBoxTpe.Area
    // <input type="..." />
    object Password extends TextBoxTpe.Password
    object DateNative extends TextBoxTpe.DateNative
    object EmailNative extends TextBoxTpe.EmailNative
    object Text extends TextBoxTpe.Text
    // <input type="text" /> with TextMask
    object EmailMask extends TextBoxTpe.EmailMask
    object DateMask extends TextBoxTpe.DateMask
    object Currency extends TextBoxTpe.Currency
    object Percentage extends TextBoxTpe.Percentage
    object NumberInt extends TextBoxTpe.NumberInt
    object NumberFloat extends TextBoxTpe.NumberFloat
    case class Array(items: List[TextMask.Item]) extends TextBoxTpe.Array
    case class Func(func: String => List[TextMask.Item]) extends TextBoxTpe.Func
  }

  object Style {
    object Full extends TextBoxStyle.Full
    object Minimal extends TextBoxStyle.Minimal
  }

  object Size {
    object Px32 extends TextBoxSize.Px32
    object Px40 extends TextBoxSize.Px40
  }

  object Status {
    object Busy extends TextBoxStatus.Busy
    object Valid extends TextBoxStatus.Valid
    object Invalid extends TextBoxStatus.Invalid
    object None extends TextBoxStatus.None
  }

  private def renderOverlay(props: Props, horPin: TagMod)(
    node: VdomNode
  ): VdomElement = {
    <.div(
      TagMod(SStyle.position.absolute.position.pinTop, horPin),
      TagMod(SStyle.height.pc100, ^.width := s"${props.size.height}px"),
      SStyle.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter
    )(node)
  }

  private def render(props: Props): VdomElement = {
    <.div(
      SStyle.position.relative,
      props.icon
        .map(name => <.div(SStyle.color.gray4, Icon(name)()))
        .map(renderOverlay(props, SStyle.position.pinLeft)),
      TextBoxBody(props = props)(),
      props.status.node.map(renderOverlay(props, SStyle.position.pinRight))
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
