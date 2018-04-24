// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.button

import anduin.component.loader.InlineLoader
import anduin.style.Style

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
    case ProgressButton.Status.Default => Style.backgroundColor.primary4.shadow.borderPrimary5s.toString
    case ProgressButton.Status.Loading =>
      Style.backgroundColor.primary3.shadow.borderPrimary5s.flexbox.flex.flexbox.itemsCenter.toString
    case ProgressButton.Status.Disabled => Style.disabled.backgroundGray2.disabled.shadowBorderGray4.toString
    case ProgressButton.Status.Success  => Style.backgroundColor.success4.shadow.borderSuccess5s.toString
    case ProgressButton.Status.Error    => Style.backgroundColor.danger4.shadow.borderDanger5s.toString
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
          Style.padding.ver8.padding.hor16.fontSize.px14.toString -> true,
          Style.lineHeight.px16.fontWeight.medium.borderRadius.px2.toString -> true,
          Style.flexbox.flex.focus.outline.transition.allWithOutline.toString -> true,
          Style.color.white.shadow.blur1Dark.disabled.colorGray6.toString -> true,
          "progress-button" -> true,
          props.classMap(props.status) -> true,
          state.extraClasses -> state.extraClasses.nonEmpty
        ),
        ^.onClick ==> onClick,
        ^.disabled := ((props.status == Status.Loading) || (props.status == Status.Disabled) || (props.status == Status.Success)),
        TagMod.when(props.status == Status.Loading)(
          InlineLoader(Style.margin.right4.value)
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
