// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.editor

import org.scalajs.dom.html.Div
import org.scalajs.dom.raw.HTMLInputElement

import anduin.facade.draftjs.EditorState

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[editor] final case class LinkModal(
  editorState: EditorState,
  onAddLink: String => Callback,
  onClose: Callback
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = LinkModal.component(this)
}

private[editor] object LinkModal {

  private val ComponentName = this.getClass.getSimpleName

  private case class State(link: String = "")

  private case class Backend(scope: BackendScope[LinkModal, State]) {

    private var linkRef: HTMLInputElement = _ // scalastyle:ignore

    def focus: Callback = {
      for {
        props <- scope.props
        editorState = props.editorState
        selection = editorState.getSelection()
        // Try to get the current link
        _ <- Callback.when(!selection.isCollapsed()) {
          val contentState = editorState.getCurrentContent()
          val startKey = selection.getStartKey()
          val startOffset = selection.getStartOffset()
          val linkKey = contentState.getBlockForKey(startKey).getEntityAt(startOffset)
          Callback.when(linkKey != null) { // scalastyle:ignore null
            val linkInstance = contentState.getEntity(linkKey)
            scope.modState(_.copy(link = linkInstance.getData().url.asInstanceOf[String]))
          }
        }
        _ <- Callback {
          linkRef.focus()
        }
      } yield ()
    }

    def render(props: LinkModal, state: State): VdomTagOf[Div] = {
      <.div(
        <.div(^.cls := "modal-body",
          <.input.ref(linkRef = _)(
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

        <.div(^.cls := "modal-footer flex items-center justify-between",
          <.div(^.cls := "btn-group margin-left-auto",
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

  private val component = ScalaComponent.builder[LinkModal](ComponentName)
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(_.backend.focus)
    .build
}
