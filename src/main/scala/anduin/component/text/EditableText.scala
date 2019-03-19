// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.text

import anduin.component.button.Button
import anduin.component.button.Button.Color
import anduin.component.icon.Icon
import anduin.component.portal.PortalPosition
import anduin.component.tooltip.Tooltip
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class EditableText(
  value: String,
  textWhenValueIsEmpty: String,
  placeholderOpt: Option[String],
  tip: Option[String],
  editable: Boolean,
  onUpdate: String => Callback,
  wrapperComponent: Option[(Callback, Boolean) => VdomElement] = None,
  customInput: Option[(String => Callback, String) => VdomElement] = None,
  displayComponent: Option[String => VdomElement] = None
) {
  def apply(): ScalaComponent.Unmounted[_, _, _] = {
    EditableText.component(this)
  }
}

object EditableText {

  private val ComponentName = this.getClass.getSimpleName

  private type Props = EditableText
  private case class State(value: String, editing: Boolean)

  private class Backend(scope: BackendScope[Props, State]) {

    private def onChange(e: ReactEventFromInput) = {
      e.extract(_.target.value) { value =>
        scope.modState(_.copy(value = value))
      }
    }

    private def onSave() = {
      for {
        state <- scope.state
        props <- scope.props
        _ <- scope.modState(_.copy(editing = false))
        _ <- props.onUpdate(state.value)
      } yield ()
    }

    private def renderEditting(props: Props, state: State): VdomElement = {
      <.div(
        Style.flexbox.flex.flexbox.itemsCenter,
        <.div(
          <.label(),
          props.customInput.fold[VdomElement] {
            <.input(
              ^.value := state.value,
              ^.minWidth := "100px",
              props.placeholderOpt.whenDefined(ph => ^.placeholder := ph),
              ^.tpe := "text",
              ^.cls := "text-field",
              ^.onChange ==> onChange
            )
          } { customInput =>
            customInput(value => scope.modState(_.copy(value = value)), state.value)
          }
        ),
        <.div(
          Style.flexbox.flex,
          <.span(
            Style.margin.left8,
            Button(
              style = Button.Style.Minimal(icon = Some(Icon.Glyph.Check), color = Color.Blue),
              onClick = onSave
            )()
          ),
          <.span(
            Style.margin.left8,
            Button(
              style = Button.Style.Minimal(icon = Some(Icon.Glyph.Cross), color = Color.Red),
              onClick = scope.modState(_.copy(value = props.value, editing = false))
            )()
          )
        )
      )
    }

    private def renderView(props: Props, state: State): VdomElement = {
      val icon = Button(
        style = Button.Style.Minimal(icon = Some(Icon.Glyph.Edit), color = Color.Blue),
        onClick = scope.modState(_.copy(editing = true))
      )()
      <.div(
        Style.flexbox.flex.flexbox.itemsCenter,
        props.displayComponent.fold[VdomElement](
          <.span(
            Style.overflowWrap.breakWord.margin.hor8,
            if (state.value.trim.nonEmpty) {
              state.value
            } else {
              props.textWhenValueIsEmpty
            }
          )
        )(_.apply(state.value)),
        TagMod.when(props.editable)(
          props.tip.fold(TagMod(icon)) { tip =>
            Tooltip(position = PortalPosition.RightCenter, renderTarget = icon, renderContent = () => tip)()
          }
        )
      )
    }

    // scalastyle:off multiple.string.literals
    def render(props: Props, state: State): VdomElement = {
      if (state.editing) {
        renderEditting(props, state)
      } else {
        props.wrapperComponent.fold[VdomElement] {
          renderView(props, state)
        } { wrapperComponent =>
          wrapperComponent(scope.modState(_.copy(editing = true)), props.editable)
        }
      }
    }
  }

  private val component = ScalaComponent
    .builder[Props](ComponentName)
    .initialStateFromProps { props =>
      State(value = props.value, editing = false)
    }
    .renderBackend[Backend]
    .componentWillReceiveProps { scope =>
      scope.modState(_.copy(value = scope.nextProps.value))
    }
    .build
}
