// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

import anduin.component.util.JavaScriptUtils

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[slate] final case class MarkButton(
  markAction: MarkAction,
  label: String = "",
  tip: String = "",
  active: Boolean = false,
  onToggle: MarkAction => Callback
) {
  def apply(children: VdomNode*): ScalaComponent.Unmounted[_, _, _] = {
    val key = s"${MarkButton.ComponentName}-${markAction.markType}"
    MarkButton.component.withKey(key)(this)(children: _*)
  }
}

private[slate] object MarkButton {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[MarkButton, _]) {

    def render(props: MarkButton, children: PropsChildren): VdomElement = {
      <.span(
        ^.classSet(
          "tooltip -top" -> props.tip.nonEmpty
        ),
        TagMod.when(props.tip.nonEmpty)(VdomAttr("data-tip") := props.tip),
        <.a(
          ^.classSet(
            "btn -plain -icon-only" -> true,
            "-selected" -> props.active
          ),
          ^.href := JavaScriptUtils.voidMethod,
          ^.onClick ==> { (e: ReactEvent) ⇒
            e.preventDefault()
            props.onToggle(props.markAction)
          },
          props.label,
          children
        )
      )
    }
  }

  private val component = ScalaComponent.builder[MarkButton](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
