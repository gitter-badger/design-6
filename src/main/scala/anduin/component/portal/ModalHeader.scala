// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import anduin.component.button.{Button, ButtonStyle}
import anduin.component.icon.IconAcl
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
  def apply(): VdomElement = {
    ModalHeader.component(this)
  }
}

object ModalHeader {

  private val ComponentName = this.getClass.getSimpleName

  private class Backend() {
    def render(props: ModalHeader): VdomElement = {
      <.div(
        Style.backgroundColor.white.padding.ver16.position.relative,
        Style.border.bottom.borderColor.gray3.borderWidth.px1,
        <.h3(Style.fontWeight.medium.textAlign.center.color.gray7.margin.all4, props.title),
        TagMod.when(props.isClosable) {
          <.div(
            Style.position.absolute.coordinate.top0.backgroundColor.white,
            Style.flexbox.flex.flexbox.itemsCenter.height.pc100, // center vertical
            ^.right := "12px",
            Button(
              style = ButtonStyle.StyleMinimal,
              size = ButtonStyle.SizeIcon,
              onClick = props.close
            )(IconAcl(name = IconAcl.NameCross)())
          )
        }
      )
    }
  }

  private val component = ScalaComponent
    .builder[ModalHeader](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build

}
