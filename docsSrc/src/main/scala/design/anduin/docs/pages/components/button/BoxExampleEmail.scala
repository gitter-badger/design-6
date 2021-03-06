// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.docs.pages.components.button

import design.anduin.components.button.Button
import design.anduin.components.icon.Icon
import design.anduin.components.input.textbox.TextBox
import design.anduin.docs.components.{DemoState, ExampleSimple}
import design.anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[button] final case class BoxExampleEmail() {
  def apply(): VdomElement = BoxExampleEmail.component(this)
}

private[button] object BoxExampleEmail {

  private type Props = BoxExampleEmail

  private def renderToolbarButton(icon: Icon.Name, isDisabled: Boolean = false): VdomElement = {
    Button(style = Button.Style.Minimal(icon = Some(icon)), isDisabled = isDisabled)()
  }

  private val toolbarSep =
    <.div(Style.margin.left8.padding.left8.border.left.borderColor.gray3)

  val toolbar: VdomElement = {
    <.div(
      Style.flexbox.flex,
      renderToolbarButton(Icon.Glyph.AlignLeft),
      renderToolbarButton(Icon.Glyph.AlignCenter),
      renderToolbarButton(Icon.Glyph.AlignRight),
      renderToolbarButton(Icon.Glyph.AlignJustify),
      toolbarSep,
      renderToolbarButton(Icon.Glyph.Bold),
      renderToolbarButton(Icon.Glyph.Italic),
      renderToolbarButton(Icon.Glyph.Underline),
      renderToolbarButton(Icon.Glyph.StrikeThrough),
      toolbarSep,
      renderToolbarButton(Icon.Glyph.Paragraph, isDisabled = true),
      renderToolbarButton(Icon.Glyph.ListBullet),
      renderToolbarButton(Icon.Glyph.ListNumber),
      renderToolbarButton(Icon.Glyph.Table)
    )

  }

  private val editor: VdomElement = {
    DemoState.Str("Dear John,", (value, onChange) => {
      TextBox(value, onChange, TextBox.Tpe.Area(rows = 4))()
    })()
  }

  private val footer: VdomElement = {
    <.div(
      Style.flexbox.flex.flexbox.justifyEnd,
      <.div(Style.margin.right8, Button(isDisabled = true)("Use as template")),
      <.div(Style.margin.right8, Button()("Discard")),
      <.div(Style.margin.right8, Button()("Save")),
      Button(
        style = Button.Style.Full(color = Button.Color.Primary, icon = Some(Icon.Glyph.EnvelopeSend))
      )("Send")
    )
  }

  private val render: VdomElement = {
    val content = <.div(
      <.div(Style.margin.bottom8, toolbar),
      <.div(Style.margin.bottom8, editor),
      <.div(footer)
    )
    ExampleSimple(
      """
        |For example, in the above email editor:
        |- Text format actions (eg. "Bold" and "Italic") uses
        |`Style.Minimal` (low emphasis)
        |- Main actions (eg. "Save" or "Send") uses `Style.Full` (high
        |emphasis)
      """.stripMargin
    )(content)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderStatic(render)
    .build
}
