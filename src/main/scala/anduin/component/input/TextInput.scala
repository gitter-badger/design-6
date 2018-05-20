// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input

import anduin.component.icon.IconAcl
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class TextInput(
  value: String,
  onChange: String => Callback,
  placeholder: String = "",
  context: VdomNode = EmptyVdom,
  // ===
  tpe: TextInput.Tpe = TextInput.TpeSingle,
  status: TextInput.Status = TextInput.StatusNone,
  size: TextInput.Size = TextInput.SizeMedium,
  // ===
  isDisabled: Boolean = false,
  isRequired: Boolean = false,
  isReadOnly: Boolean = false
) {
  def apply(): VdomElement = TextInput.component(this)
}

object TextInput {

  trait Tpe { val styles: TagMod }
  case object TpeSingle extends Tpe { val styles: TagMod = Style.lineHeight.px16 }
  case object TpeArea extends Tpe { val styles: TagMod = Style.lineHeight.ratio1p5.padding.ver8 }

  // ===

  trait Size { val height: TagMod; val font: TagMod }
  case object SizeMedium extends Size { val height: TagMod = Style.height.px32; val font: TagMod = Style.fontSize.px14 }
  case object SizeLarge extends Size { val height: TagMod = Style.height.px40; val font: TagMod = Style.fontSize.px16 }

  // ===

  trait Status {
    val styles: Style = Style.borderColor.gray4
    val icon: Option[VdomNode] = None
  }
  private def renderIcon(name: IconAcl.Name, color: TagMod) = {
    val styles = TagMod(
      Style.position.absolute.coordinate.top0.coordinate.bottom0,
      Style.backgroundColor.white.margin.verAuto,
      TagMod(^.right := "8px", ^.height := "16px")
    )
    <.div(styles, color, IconAcl(name = name)())
  }
  case object StatusNone extends Status
  case object StatusBusy extends Status
  case object StatusValid extends Status {
    override val styles: Style = Style.borderColor.success4
    override val icon: Option[VdomNode] = Option(renderIcon(IconAcl.NameCheck, Style.color.success4))
  }
  case object StatusInvalid extends Status {
    override val styles: Style = Style.borderColor.danger4
    override val icon: Option[VdomNode] = Option(renderIcon(IconAcl.NameCross, Style.color.danger4))
  }

  // === Predefined styles
  // These styles will be used in Backend's render, however they are all
  // static values and quite large so we brought them here.

  private val borderStyles = Style.border.all.borderWidth.px1.borderRadius.px2

  private val inputStyles = TagMod(
    Style.display.block.width.pc100.padding.hor12.backgroundColor.white,
    Style.focus.shadow.focus.border.transition.allWithShadow,
    Style.disabled.backgroundGray2.disabled.colorGray6,
    borderStyles
  )

  private val contextStyles = TagMod(
    Style.padding.hor12.backgroundColor.gray2.color.gray6,
    Style.borderWidth.right0.borderRadius.left,
    Style.flexbox.flex.flexbox.itemsCenter,
    StatusNone.styles,
    borderStyles
  )

  // ===

  private class Backend(scope: BackendScope[TextInput, _]) {

    private def onChange(e: ReactEventFromInput) = {
      for {
        props <- scope.props
        _ <- props.onChange(e.target.value)
      } yield ()
    }

    def render(props: TextInput): VdomElement = {
      val height = TagMod.when(props.tpe != TpeArea) { props.size.height }
      val commonStyles = TagMod(props.size.font, props.tpe.styles, height)
      val contextIsDefined = props.context != EmptyVdom
      val tag: VdomTag = if (props.tpe == TpeArea) <.textarea else <.input

      val input = tag(
        // styles
        TagMod(commonStyles, inputStyles, props.status.styles),
        TagMod.when(contextIsDefined) { Style.borderRadius.right },
        TagMod.when(props.isReadOnly) { Style.backgroundColor.gray1 },
        // behaviours
        TagMod.when(props.tpe != TpeArea) { ^.tpe := "text" },
        ^.disabled := props.isDisabled,
        ^.readOnly := props.isReadOnly,
        ^.onChange ==> onChange,
        ^.value := props.value,
        ^.placeholder := props.placeholder
      )
      val context = TagMod.when(contextIsDefined) { <.div(commonStyles, contextStyles, props.context) }
      val icon = props.status.icon.whenDefined

      <.div(Style.position.relative.flexbox.flex, context, input, icon)
    }
  }

  private val component = ScalaComponent
    .builder[TextInput](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build
}
