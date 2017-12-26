// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

import anduin.component.util.JavaScriptUtils

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[slate] final case class ToolbarButton(
  nodeType: NodeType,
  tip: String,
  active: Boolean,
  onClick: NodeType => Callback
) {
  def apply(children: VdomNode*): ScalaComponent.Unmounted[_, _, _] = {
    val key = s"${ToolbarButton.ComponentName}-${nodeType.nodeType}"
    ToolbarButton.component.withKey(key)(this)(children: _*)
  }
}

private[slate] object ToolbarButton {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[ToolbarButton, _]) {

    def render(props: ToolbarButton, children: PropsChildren): VdomElement = {
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
            props.onClick(props.nodeType)
          },
          children
        )
      )
    }
  }

  private val component = ScalaComponent.builder[ToolbarButton](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
