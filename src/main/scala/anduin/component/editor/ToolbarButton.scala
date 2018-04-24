// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import anduin.component.button.Button

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class ToolbarButton(
  key: String,
  tip: String,
  active: Boolean,
  onClick: Callback
) {
  def apply(children: VdomNode*): VdomElement = {
    ToolbarButton.component.withKey(key)(this)(children: _*)
  }
}

private[editor] object ToolbarButton {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[ToolbarButton, _]) {

    def render(props: ToolbarButton, children: PropsChildren): VdomElement = {
      <.span(
        ^.classSet(
          "tooltip -top" -> props.tip.nonEmpty
        ),
        TagMod.when(props.tip.nonEmpty)(VdomAttr("data-tip") := props.tip),
        Button(
          style = Button.StyleMinimal,
          size = Button.SizeIcon,
          isSelected = props.active,
          onClick = props.onClick
        )(
          children
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[ToolbarButton](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
