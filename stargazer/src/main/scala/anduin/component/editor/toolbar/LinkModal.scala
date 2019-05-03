// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.toolbar

import anduin.component.button.Button
import anduin.component.editor.LinkNode
import anduin.component.editor.utils.DataUtil
import anduin.component.input.checkbox.Checkbox
import anduin.component.modal.{ModalBody, ModalFooterWCancel}
import anduin.scalajs.slate.Slate.Value
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[toolbar] final case class LinkModal(
  value: Value,
  onAddLink: (String, Boolean) => Callback,
  onClose: Callback
) {
  def apply(): VdomElement = LinkModal.component(this)
}

private[toolbar] object LinkModal {

  private type Props = LinkModal

  private case class State(link: String = "", openInNewTab: Boolean = true)

  private class Backend(scope: BackendScope[Props, State]) {

    def render(props: Props, state: State): VdomElement = {
      React.Fragment(
        ModalBody()(
          <.input(
            ^.autoFocus := true,
            ^.cls := "text-field",
            ^.tpe := "text",
            ^.placeholder := "Insert the link",
            ^.value := state.link,
            ^.onChange ==> { e: ReactEventFromInput =>
              e.extract(_.target.value) { value =>
                scope.modState(_.copy(link = value))
              }
            }
          ),
          <.div(
            Style.margin.top8,
            Checkbox(
              isChecked = state.openInNewTab,
              onChange = checked => scope.modState(_.copy(openInNewTab = checked))
            )("The link will be opened in a new tab")
          )
        ),
        ModalFooterWCancel(cancel = props.onClose)(
          <.div(
            Button(
              style = Button.Style.Full(color = Button.Color.Primary),
              isDisabled = state.link.isEmpty,
              onClick = {
                for {
                  _ <- props.onClose
                  _ <- props.onAddLink(state.link, state.openInNewTab)
                } yield ()
              }
            )("Add")
          )
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps { props =>
      val (href, openInNewTab) = props.value.inlines
        .find(_.inlineType == LinkNode.nodeType)
        .fold {
          ("", true)
        } { inline =>
          val href = DataUtil.value(inline.data, "href")
          val openInNewTab = DataUtil.value(inline.data, "target") == "_blank"
          (href, openInNewTab)
        }
      State(href, openInNewTab)
    }
    .renderBackend[Backend]
    .build
}
