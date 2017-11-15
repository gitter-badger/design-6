// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.editor

import japgolly.scalajs.react.vdom.TagOf
import org.scalajs.dom.html.Span

import anduin.component.util.JavaScriptUtils
import anduin.facade.draftjs.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class StyleButton(
  style: Style,
  label: String = "",
  tip: String = "",
  active: Boolean = false,
  onToggle: Style => Callback = _ => Callback.empty
) {
  def apply(children: VdomNode*): ScalaComponent.Unmounted[_, _, _] = {
    val key = s"${StyleButton.ComponentName}-${style.style.toLowerCase}"
    StyleButton.component.withKey(key)(this)(children: _*)
  }
}

private[editor] object StyleButton {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[StyleButton, _]) {

    def render(props: StyleButton, children: PropsChildren): TagOf[Span] = {
      <.span(
        ^.classSet(
          "RichEditor-styleButton" -> true,
          "tooltip -top" -> props.tip.nonEmpty
        ),
        TagMod.when(props.tip.nonEmpty)(VdomAttr("data-tip") := props.tip),
        <.a(
          ^.classSet(
            "btn -plain -icon-only" -> true,
            "-selected" -> props.active
          ),
          ^.href := JavaScriptUtils.VoidMethod,
          ^.onClick ==> { (e: ReactEvent) ⇒
            e.preventDefault()
            props.onToggle(props.style)
          },
          props.label,
          children
        )
      )
    }
  }

  private val component = ScalaComponent.builder[StyleButton](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
