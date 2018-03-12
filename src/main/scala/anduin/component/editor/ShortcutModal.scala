// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import japgolly.scalajs.react.component.ScalaFn.Unmounted

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class ShortcutModal(isMac: Boolean) {
  def apply(): Unmounted[ShortcutModal] = ShortcutModal.component(this)
}

private[editor] object ShortcutModal {

  // scalastyle:off multiple.string.literals
  private def render(props: ShortcutModal): VdomElement = {
    val cmdKey = if (props.isMac) <.span(^.cls := "key", "⌘") else <.span(^.cls := "key", "Ctrl")
    val operator = <.span(^.cls := "operator mh1", "+")

    val shortcuts = List(
      ("B", "Bold"),
      ("I", "Italic"),
      ("U", "Underline"),
      ("Z", "Undo"),
      ("Y", "Redo")
    )

    <.div(
      ^.cls := "modal-body",
      <.ul(
        ^.cls := "list os-specific-keys flex",
        shortcuts.toVdomArray {
          case (key, description) =>
            <.li(
              ^.cls := "item flex items-center",
              ^.key := s"key-$key",
              cmdKey,
              operator,
              <.span(^.cls := "key", key),
              <.span(^.cls := "ml3", description)
            )
        }
      ),
      <.ul(
        ^.cls := "list universal-keys",
        <.li(
          ^.cls := "item flex items-center",
          <.span(^.cls := "key ph2", "Enter / Return"),
          <.span(^.cls := "ml3", "New Line")
        )
      )
    )
  }
  // scalastyle:on multiple.string.literals

  private val component = ScalaFnComponent[ShortcutModal](render)
}
