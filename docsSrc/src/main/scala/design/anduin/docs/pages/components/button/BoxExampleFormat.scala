// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.docs.pages.components.button

import design.anduin.components.button.Button
import design.anduin.components.icon.Icon
import design.anduin.components.menu.VerticalDivider
import design.anduin.docs.components.ExampleSimple
import design.anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class BoxExampleFormat(
  ) {
  def apply(): VdomElement = BoxExampleFormat.component(this)
}

object BoxExampleFormat {

  private type Props = BoxExampleFormat

  private case class State(isBold: Boolean, isItalic: Boolean)

  private class Backend(scope: BackendScope[Props, State]) {

    def render(props: Props, state: State): VdomElement = {
      ExampleSimple()({
        <.div(
          Style.flexbox.flex.flexbox.itemsCenter,
          Button(
            style = Button.Style.Ghost(isSelected = state.isBold, icon = Some(Icon.Glyph.Bold)),
            onClick = scope.modState(_.copy(isBold = !state.isBold))
          )(),
          <.div(Style.margin.left4),
          Button(
            style = Button.Style.Ghost(isSelected = state.isItalic, icon = Some(Icon.Glyph.Italic)),
            onClick = scope.modState(_.copy(isItalic = !state.isItalic))
          )(),
          <.div(Style.margin.left16),
          VerticalDivider()(),
          <.div(Style.margin.left16),
          <.p(
            Style.fontSize.px17,
            TagMod.when(state.isBold)(Style.fontWeight.bold),
            TagMod.when(state.isItalic)(Style.fontStyle.italic),
            "Sample text"
          )
        )
      })
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State(isBold = false, isItalic = false))
    .renderBackend[Backend]
    .build
}
