// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.modal

import anduin.component.button.Button
import anduin.component.icon.Icon
import anduin.component.truncate.TruncateMarkup
import anduin.component.util.ComponentUtils
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ModalHeader(
  title: String,
  isClosable: Boolean = true,
  close: Callback
) {
  def apply(): VdomElement = ModalHeader.component(this)
}

object ModalHeader {

  private type Props = ModalHeader

  private val containerStyles = TagMod(
    Style.background.white.padding.ver16.position.relative,
    Style.border.bottom.borderColor.gray3.borderWidth.px1
  )

  private val titleStyles = TagMod(
    Style.fontWeight.medium.textAlign.center.color.gray7.margin.all4,
    ^.paddingLeft := "45px",
    ^.paddingRight := "45px"
  )

  private val closeStyles = TagMod(
    Style.position.absolute.position.pinTop.background.white,
    Style.flexbox.flex.flexbox.itemsCenter.height.pc100, // center vertical
    ^.right := "12px"
  )

  private def render(props: Props): VdomElement = {
    <.div(
      ComponentUtils.testId(this, "Container"),
      containerStyles,
      <.h3(
        ComponentUtils.testId(this, "Title"),
        titleStyles,
        ^.title := props.title,
        TruncateMarkup(
          renderTarget = props.title
        )()
      ),
      TagMod.when(props.isClosable) {
        val button = Button(
          style = Button.Style.Minimal(icon = Some(Icon.Glyph.Cross)),
          tpe = Button.Tpe.Plain(isAutoFocus = true),
          onClick = props.close
        )()
        <.div(closeStyles, button)
      }
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
