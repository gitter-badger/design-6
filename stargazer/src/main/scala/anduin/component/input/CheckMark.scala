// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input

import anduin.component.icon.Icon
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class CheckMark(
  selected: Boolean,
  disabled: Boolean = false,
  onClick: Callback
) {
  def apply(children: VdomNode*): ScalaComponent.Unmounted[_, _, _] = {
    CheckMark.component(this)(children: _*)
  }
}

object CheckMark {

  private val ComponentName = this.getClass.getSimpleName

  private class Backend(backendScope: BackendScope[CheckMark, Unit]) {

    val _ = backendScope

    def render(props: CheckMark, children: PropsChildren): VdomElement = {
      <.div(
        Style.height.pc100,
        ^.classSet(
          "at-check-mark flex items-center" -> true,
          "-disabled" -> props.disabled,
          "-selected" -> props.selected
        ),
        ^.onClick ==> { e: ReactEventFromHtml =>
          for {
            _ ← e.stopPropagationCB
            _ ← props.onClick
          } yield ()
        },
        TagMod.when(props.selected)(
          <.span(
            ^.classSet(
              "at-icon" -> true,
              Style.flexbox.flex.flexbox.justifyCenter.flexbox.itemsCenter.borderRadius.pill.color.gray0.value -> true
            ),
            Icon(name = Icon.Glyph.Check)()
          )
        ),
        children
      )
    }
  }

  private val component = ScalaComponent
    .builder[CheckMark](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
