// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.button

import anduin.component.loader.InlineLoader

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

/**
  * Create new component instance
  *
  * @param status The status, can be any of Status trait
  * @param labels Define the button label based on given status
  * For example:
  * {{{
  * ProgressButton(
  *   status = ProgressButton.Status.Default,
  *   labels = {
  *     case ProgressButton.Status.Loading => "Completing"
  *     case ProgressButton.Status.Success => "Done"
  *     case ProgressButton.Status.Default | _=> "Complete"
  *   },
  *   onClick = onSaveButtonClick()
  * )()
  * }}}
  * @param classMap Define the button class based on given status
  * @param extraClasses The additional CSS classes that will be added to the button
  * @param onClick The handler of click event
  * @param extraTagMods Some other elements before the button text, like icons
  */
// scalastyle:off multiple.string.literals
final case class ProgressButton(
  status: ProgressButton.Status = ProgressButton.Status.Default,
  labels: ProgressButton.Status => String,
  classMap: ProgressButton.Status => String = {
    case ProgressButton.Status.Default  => "-primary"
    case ProgressButton.Status.Loading  => "-loading"
    case ProgressButton.Status.Disabled => "disabled"
    case ProgressButton.Status.Success  => "-success"
    case ProgressButton.Status.Error    => "-danger"
  },
  extraClasses: String = "",
  submitButton: Boolean = false,
  onClick: Callback = Callback.empty,
  extraTagMods: Seq[TagMod] = Seq.empty
) {

  def apply(children: VdomNode*): VdomElement = ProgressButton.component(this)(children: _*)
}
// scalastyle:on multiple.string.literals

object ProgressButton {

  private val ComponentName = this.getClass.getSimpleName

  // Available status for button
  sealed trait Status
  object Status {
    case object Default extends Status
    case object Loading extends Status
    case object Disabled extends Status
    case object Success extends Status
    case object Error extends Status
  }

  private case class State(status: Status, extraClasses: String)

  private case class Backend(scope: BackendScope[ProgressButton, State]) {

    private def onClick(e: ReactEventFromHtml) = {
      for {
        _ <- e.preventDefaultCB
        _ <- e.stopPropagationCB
        props <- scope.props
        _ <- props.onClick
      } yield ()
    }

    // scalastyle:off multiple.string.literals
    def render(props: ProgressButton, state: State, children: PropsChildren): VdomElement = {
      val buttonType = if (props.submitButton) "submit" else "button"

      <.button(
        ^.tpe := buttonType,
        ^.classSet(
          "btn progress-button" -> true,
          props.classMap(props.status) -> true,
          state.extraClasses -> state.extraClasses.nonEmpty
        ),
        ^.onClick ==> onClick,
        ^.disabled := ((props.status == Status.Loading) || (props.status == Status.Disabled) || (props.status == Status.Success)),
        TagMod.when(props.status == Status.Loading)(
          InlineLoader()
        ),
        TagMod(props.extraTagMods: _*),
        props.labels(props.status),
        children
      )
    }
    // scalastyle:on multiple.string.literals
  }

  private val component = ScalaComponent
    .builder[ProgressButton](ComponentName)
    .initialStateFromProps { props =>
      State(status = props.status, extraClasses = props.extraClasses)
    }
    .renderBackendWithChildren[Backend]
    .componentWillReceiveProps { params =>
      Callback.when(params.currentProps.extraClasses != params.nextProps.extraClasses) {
        params.modState(_.copy(extraClasses = params.nextProps.extraClasses))
      }
    }
    .build
}
