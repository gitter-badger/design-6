// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.suggest

import anduin.component.input.textbox.{TextBox, TextBoxSize}
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[suggest] class MultiSuggestInput[A] {

  def apply(): Props.type = Props

  case class Props(
    // multi suggest
    onSelect: Seq[String] => Callback,
    opts: Suggest.Opts[A],
    selectKeys: Seq[String],
    // text box
    initialValue: String = "",
    isAutoFocus: Boolean = false
  ) {
    def apply(): VdomElement = component(this)
  }

  private val SuggestA = (new Suggest[A])()

  private case class State(inputValue: String)

  private class Backend(scope: BackendScope[Props, State]) {

    private def setInputValue(value: String): Callback =
      scope.modState(_.copy(inputValue = value))

    private def selectValues(values: Seq[String]): Callback =
      for {
        _ <- scope.props >>= (_.onSelect(values.filter(_.nonEmpty)))
        _ <- setInputValue("")
      } yield ()

    private def onInputValueChange(value: String): Callback = scope.props >>= { props =>
      props.selectKeys
        .find(char => value.contains(char))
        .map(value.split)
        .map(_.toSeq)
        .fold(setInputValue(value))(selectValues)
    }

    def render(props: Props, state: State): VdomElement = {
      SuggestA(
        value = state.inputValue,
        onChange = onInputValueChange,
        opts = props.opts,
        selection = Some(
          Suggest.Selection(
            triggerKeys = props.selectKeys,
            onSelect = value => selectValues(List(value))
          )
        ),
        textBox = Suggest.TextBox(
          style = TextBox.Style.Minimal,
          size = MultiSuggestInput.InputTextBoxSize,
          isAutoFocus = props.isAutoFocus
        )
      )()
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps(props => State(inputValue = props.initialValue))
    .renderBackend[Backend]
    .build
}

private object MultiSuggestInput {
  object InputTextBoxSize extends TextBoxSize {
    final def height: Int = 20
    final def horPadding: Int = 8
    final def text: TagMod = Style.fontSize.px13.lineHeight.px20
  }
}
