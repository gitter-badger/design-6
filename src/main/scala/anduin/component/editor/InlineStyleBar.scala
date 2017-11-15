// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.editor

import japgolly.scalajs.react.vdom.TagOf
import org.scalajs.dom.html.Div

import anduin.component.icon.Iconv2

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.facade.draftjs._
// scalastyle:on underscore.import

private[editor] final case class InlineStyleBar(
  editorState: EditorState,
  onToggle: EditorState => Callback
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = InlineStyleBar.component(this)
}

private[editor] object InlineStyleBar {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[InlineStyleBar, _]) {

    def render(props: InlineStyleBar): TagOf[Div] = {
      <.div(
        List(
          (BoldInlineStyle, Iconv2.bold(), "Bold"),
          (ItalicInlineStyle, Iconv2.italic(), "Italic"),
          (UnderlineInlineStyle, Iconv2.underline(), "Underline"),
          (StrikeThroughInlineStyle, Iconv2.strikethrough(), "Strikethrough")
        ).toVdomArray { case (inlineStyle, icon, tip) =>
          StyleButton(
            style = inlineStyle,
            tip = tip,
            active = props.editorState.getCurrentInlineStyle().has(inlineStyle.style),
            onToggle = inlineStyle => props.onToggle(RichUtils.toggleInlineStyle(props.editorState, inlineStyle.style))
          )(icon)
        }
      )
    }
  }

  private val component = ScalaComponent.builder[InlineStyleBar](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}
