// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.input.suggest

import design.anduin.components.tag.Tag
import design.anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[suggest] class MultiSuggestSelected[A] {

  def apply(): Props.type = Props

  case class Props(
    multiSuggest: MultiSuggest[A]#Props,
    value: (String, Int),
    focusAddInput: Callback
  ) {
    def apply(): VdomElement = component(this)
  }

  private val Input = (new MultiSuggestInput[A])()

  private case class State(isEdit: Boolean)

  private class Backend(scope: BackendScope[Props, State]) {

    private def update(strings: Seq[String]): Callback =
      for {
        props <- scope.props
        value = props.multiSuggest.value.patch(props.value._2, strings, 1)
        _ <- props.multiSuggest.onChange(value)
        _ <- props.focusAddInput
      } yield ()

    private def submit(strings: Seq[String]): Callback =
      update(strings) >> scope.modState(_.copy(isEdit = false))

    private def renderEdit(props: Props): VdomElement = {
      <.div(
        Style.position.absolute.position.pinAll,
        Input(
          onSelect = submit,
          opts = props.multiSuggest.opts,
          selectKeys = props.multiSuggest.selectKeys,
          initialValue = props.value._1,
          isAutoFocus = true
        )()
      )
    }

    private def renderView(props: Props): VdomElement = {
      val info = props.multiSuggest.valueToSelectedItem(props.value._1)
      Tag(
        maxWidth = 999,
        target = Tag.Target.Button(scope.modState(_.copy(isEdit = true))),
        close = Some(update(List.empty)),
        color = info.color
      )(info.label)
    }

    def render(props: Props, state: State): VdomElement = {
      <.div(
        Style.position.relative,
        renderView(props),
        TagMod.when(state.isEdit)(renderEdit(props))
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State(isEdit = false))
    .renderBackend[Backend]
    .build
}
