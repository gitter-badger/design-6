// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import anduin.scalajs.slate.Slate.Value

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class LinkModal(
  value: Value,
  onAddLink: String => Callback,
  onClose: Callback
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = LinkModal.component(this)
}

private[editor] object LinkModal {

  private val ComponentName = this.getClass.getSimpleName

  private case class State(link: String = "")

  private case class Backend(scope: BackendScope[LinkModal, State]) {

    def render(props: LinkModal, state: State): VdomElement = {
      <.div(
        <.div(
          ^.cls := "modal-body",
          <.input(
            ^.autoFocus := true,
            ^.cls := "text-field",
            ^.tpe := "text",
            ^.placeholder := "Insert the link",
            ^.value := state.link,
            ^.onChange ==> { (e: ReactEventFromInput) =>
              e.extract(_.target.value) { value =>
                scope.modState(_.copy(link = value))
              }
            }
          )
        ),
        <.div(
          ^.cls := "modal-footer flex items-center justify-between",
          <.div(
            ^.cls := "btn-group margin-left-auto",
            <.button(
              ^.cls := "btn",
              ^.tpe := "button",
              ^.onClick --> props.onClose,
              "Cancel"
            ),
            <.button(
              ^.cls := "btn -primary",
              ^.tpe := "button",
              ^.disabled := state.link.isEmpty,
              ^.onClick --> {
                for {
                  _ <- props.onClose
                  _ <- props.onAddLink(state.link)
                } yield ()
              },
              "Add"
            )
          )
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[LinkModal](ComponentName)
    .initialStateFromProps { props =>
      val href = props.value
        .inlines
        .filter(_.inlineType == LinkNode.nodeType)
        .first()
        .toOption
        .fold("") { inline =>
          DataUtil.value(inline.data, "href")
        }
      State(href)
    }
    .renderBackend[Backend]
    .build
}
