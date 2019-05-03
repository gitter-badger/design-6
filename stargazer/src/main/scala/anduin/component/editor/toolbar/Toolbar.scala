// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.toolbar

import anduin.component.menu.VerticalDivider
import anduin.component.util.ComponentUtils
import anduin.scalajs.slate.Slate.Value
import anduin.scalajs.slate.SlateReact
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Toolbar(
  value: Value,
  editorRef: () => SlateReact.EditorComponentRef,
  attachmentButton: TagMod
) {
  def apply(children: VdomNode*): VdomElement = {
    Toolbar.component(this)(children: _*)
  }
}

object Toolbar {

  private type Props = Toolbar

  private def render(props: Props, children: PropsChildren) = {
    <.div(
      ComponentUtils.testId(this, "ToolbarContainer"),
      ^.cls := "editor-toolbar",
      Style.flexbox.flex.flexbox.itemsCenter.padding.all4,
      <.div(
        Style.flexbox.flex.flexbox.itemsCenter,
        props.attachmentButton,
        VerticalDivider()(),
        UndoButton(props.value, props.editorRef)(),
        RedoButton(props.value, props.editorRef)(),
        VerticalDivider()(),
        AddLinkButton(props.value, props.editorRef)(),
        RemoveLinkButton(props.value, props.editorRef)(),
        VerticalDivider()(),
        FormatButton(props.value, props.editorRef)()
      ),
      <.div(
        Style.flexbox.flex.margin.leftAuto,
        ShortcutButton()(),
        children
      )
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build
}
