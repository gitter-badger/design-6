// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor
import anduin.component.modal.ModalBody
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class ShortcutModal(isMac: Boolean) {
  def apply(): VdomElement = ShortcutModal.component(this)
}

private[editor] object ShortcutModal {

  // scalastyle:off multiple.string.literals
  private def render(props: ShortcutModal): VdomElement = {
    val cmdKey = if (props.isMac) <.span(^.cls := "key", "⌘") else <.span(^.cls := "key", "Ctrl")
    val operator = <.span(^.cls := "operator", Style.margin.hor4, "+")

    val shortcuts = List(
      ("B", "Bold"),
      ("I", "Italic"),
      ("U", "Underline"),
      ("Z", "Undo"),
      ("Y", "Redo")
    )

    ModalBody()(
      <.ul(
        Style.flexbox.flex.listStyle.none,
        ^.cls := "os-specific-keys",
        shortcuts.toVdomArray {
          case (key, description) =>
            <.li(
              Style.flexbox.flex.flexbox.itemsCenter,
              ^.cls := "item",
              ^.key := s"key-$key",
              cmdKey,
              operator,
              <.span(^.cls := "key", key),
              <.span(Style.margin.left12, description)
            )
        }
      ),
      <.ul(
        Style.listStyle.none,
        ^.cls := "universal-keys",
        <.li(
          Style.flexbox.flex.flexbox.itemsCenter,
          ^.cls := "item",
          <.span(Style.padding.hor8, ^.cls := "key", "Enter / Return"),
          <.span(Style.margin.left12, "New Line")
        )
      )
    )
  }
  // scalastyle:on multiple.string.literals

  private val component = ScalaFnComponent[ShortcutModal](render)
}
