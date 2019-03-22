// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.textbox

import scala.scalajs.js
import org.scalajs.dom.html
import anduin.scalajs.textmask.ReactTextMask
import anduin.scalajs.util.Util
import anduin.style.Style

// scalastyle:off underscore.import
import js.JSConverters._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// This is the actual `input` or `textarea` tag. It does not contain other
// visual content such as overlays. There is only that single tag in this
// component implementation
//
// Note that there is no "TextBoxInput" prop, but instead this component
// uses the same props API with TextBox. It works like a render function
// renderTextBoxBody(props: TextBox): VdomElement
case class TextBoxBody(props: TextBox) {
  def apply(): VdomElement = TextBoxBody.component(this.props)
}

object TextBoxBody {

  private type Props = TextBox

  // ===
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
      props.`type`.placeholder.getOrElse("")
    }
  }

  // "maskProps" defines "onChange", "onBlue" and "defaultValue"
  // - In case of masked, TextMask will update "value" based on
  //   "defaultValue" properly
  // - In case of not masked, TextMask won't, so the "value" of the input
  //   is out of sync
  // The solution here is to always set "value" instead of "defaultValue"
  // See:
  // - https://github.com/text-mask/text-mask/issues/838
  // - https://github.com/text-mask/text-mask/pull/831
  private def getTextMaskMods(maskProps: js.Dictionary[js.Any]): TagMod = {
    val updatedMaskProps = maskProps.map { prop =>
      if (prop._1 == "defaultValue") ("value", prop._2) else prop
    }
    Util.getModsFromProps(updatedMaskProps)
  }

  def getStylesSize(props: Props): TagMod = {
    def getPadding(hasContent: Boolean) =
      if (hasContent) props.size.heightPx else props.size.horPaddingPx
    TagMod(
      props.size.text,
      if (props.`type`.isMultiLine) Style.padding.ver8 else ^.height := props.size.heightPx,
      ^.paddingLeft := getPadding(props.icon.isDefined),
      ^.paddingRight := getPadding(props.status.exists(_.node.isDefined))
    )
  }

  // This simply uses TextBoxStyle
  private def getStyles(props: Props): TagMod = {
    // At the moment we don't really separate these 2 props in appearance
    val isDimmed = props.isDisabled || props.isReadOnly
    TextBoxStyle.getStyles(
      style = props.style,
      customColor = if (isDimmed) Some(Style.color.gray7) else None,
      customBg = if (isDimmed) Some(Style.background.gray2) else None,
      customBorderColor = props.status.map(_.borderColor),
      customSize = Some(getStylesSize(props))
    )
  }

  private def renderWithTextMask(props: Props)(
    maskRef: raw.React.RefFn[html.Input],
    maskProps: js.Dictionary[js.Any]
  ): raw.React.Element = {
    val attrs = TagMod(
      getStyles(props),
      // These mods and ref will control `onChange`, `onBlur` and `value`
      getTextMaskMods(maskProps),
      VdomAttr("ref") := maskRef,
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
    props.`type`.tag(attrs).rawElement
  }

  private def render(props: Props): VdomElement = {
    val maskProps = new ReactTextMask.Props(
      mask = props.`type`.mask.map(_.value),
      pipe = props.`type`.mask.flatMap(_.pipe).orUndefined,
      keepCharPositions = props.`type`.mask.map(_.keepCharPositions).orUndefined,
      value = props.value,
      onChange = js.defined(onChange(props)),
      onBlur = js.defined(onBlur(props)),
      render = js.defined(renderWithTextMask(props))
    )
    ReactTextMask.component(maskProps)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build

}
