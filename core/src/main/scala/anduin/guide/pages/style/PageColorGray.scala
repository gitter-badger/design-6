// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.guide.pages.style

import anduin.component.input.checkbox.Checkbox
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PageColorGray() {
  def apply(): VdomElement = PageColorGray.component(this)
}

object PageColorGray {

  private type Props = PageColorGray

  private case class State(isTextVisible: Boolean)

  case class Color(value: TagMod, name: String, hex: String, desc: String)

  private val colors = List(
    Color(Style.background.gray9, "gray9", "182A38", ""),
    Color(Style.background.gray4, "gray4", "BACDDB", "Border, Disabled Text"),
    Color(Style.background.gray8, "gray8", "20394D", "Text"),
    Color(Style.background.gray3, "gray3", "DFE9F0", "Border"),
    Color(Style.background.gray7, "gray7", "4F6F89", "Text"),
    Color(Style.background.gray2, "gray2", "EBF2F7", ""),
    Color(Style.background.gray6, "gray6", "809AAD", ""),
    Color(Style.background.gray1, "gray1", "F5F9FC", "Background"),
    Color(Style.background.gray5, "gray5", "9BB0C2", ""),
    Color(Style.background.white, "white", "FFFFFF", "Background"),
  )

  private val contrastTestMod = TagMod(Style.flexbox.none.width.pc50, "Aa")

  private val contrastTest = <.div(
    Style.fontSize.px13.lineHeight.px20.textAlign.center,
    Style.flexbox.flex.flexbox.wrap,
    <.div(Style.color.gray8, contrastTestMod),
    <.div(Style.color.gray7, contrastTestMod),
    <.div(Style.color.gray4, contrastTestMod),
    <.div(Style.color.white, contrastTestMod)
  )

  private def renderPreview(state: State, color: Color): VdomElement = {
    <.div(
      Style.borderRadius.px4.padding.all8,
      TagMod(^.width := "56px", ^.height := "56px"),
      TagMod.when(color.name == "white") {
        ^.boxShadow := "0 0 0 1px var(--color-gray-3) inset"
      },
      color.value,
      TagMod.when(state.isTextVisible)(contrastTest)
    )
  }

  private def renderInfo(color: Color): VdomElement = {
    <.div(
      <.p(
        Style.fontFamily.mono,
        <.span(Style.fontWeight.bold, color.name),
        <.span(Style.color.gray7, " ", s"#${color.hex}")
      ),
      <.p(Style.color.gray7, color.desc, " ")
    )
  }

  private def renderColor(state: State)(color: Color): VdomElement = {
    <.div(
      ^.key := color.name,
      Style.flexbox.none.width.pc50,
      Style.flexbox.flex.flexbox.itemsCenter,
      Style.padding.ver24.border.bottom.borderColor.gray3,
      <.div(Style.flexbox.none.margin.right16, renderPreview(state, color)),
      <.div(Style.flexbox.fill, renderInfo(color))
    )
  }

  private class Backend(scope: BackendScope[Props, State]) {
    def render(props: Props, state: State): VdomElement = {
      <.div(
        Style.padding.ver16,
        <.div(
          Style.fontSize.px17.lineHeight.px20.margin.bottom16,
          Checkbox(
            isChecked = state.isTextVisible,
            onChange = v => scope.setState(State(isTextVisible = v))
          )("Display contrast test")
        ),
        <.div(
          Style.fontSize.px17.lineHeight.px24,
          Style.flexbox.flex.flexbox.wrap,
          colors.toVdomArray(renderColor(state))
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State(isTextVisible = false))
    .renderBackend[Backend]
    .build
}
