// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.guide.pages.components.checkbox

import anduin.component.input.checkbox.Checkbox
import anduin.guide.components.ExampleSimple
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[checkbox] final case class ChecklistExample(
  items: List[(String, Boolean)],
  parent: Option[String]
) {
  def apply(): VdomElement = ChecklistExample.component(this)
}

private[checkbox] object ChecklistExample {

  private type Props = ChecklistExample

  case class State(items: List[(String, Boolean)])

  private class Backend(scope: BackendScope[Props, State]) {

    private def onItemChange(target: (String, Boolean))(
      isChecked: Boolean
    ): Callback = scope.modState { state =>
      val items = state.items.map { current =>
        if (current._1 == target._1) current.copy(_2 = isChecked) else current
      }
      state.copy(items = items)
    }

    private def renderItem(state: State)(item: (String, Boolean)): VdomElement = {
      <.div(
        TagMod(^.key := item._1, Style.margin.bottom8),
        Checkbox(isChecked = item._2, onChange = onItemChange(item))(item._1),
      )
    }

    private def renderParent(title: String, state: State): VdomElement = {
      Checkbox(
        isChecked = state.items.forall(_._2),
        isIndeterminate = state.items.exists(item => {
          state.items.exists(_._2 != item._2)
        }),
        onChange = isChecked => {
          scope.modState { state =>
            state.copy(items = state.items.map(_.copy(_2 = isChecked)))
          }
        }
      )(title)
    }

    def render(props: Props, state: State): VdomElement = {
      val content = <.div(
        props.parent.map { title =>
          <.div(Style.margin.bottom8, renderParent(title, state))
        },
        <.div(
          TagMod.when(props.parent.isDefined)(Style.padding.left24),
          ^.marginBottom := "-8px",
          state.items.toVdomArray(renderItem(state))
        )
      )
      ExampleSimple()(content)
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateCallbackFromProps(props => CallbackTo(State(items = props.items)))
    .renderBackend[Backend]
    .build
}
