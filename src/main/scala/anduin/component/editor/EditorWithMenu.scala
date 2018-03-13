// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import org.scalajs.dom.raw.HTMLElement
import org.scalajs.dom.window

import anduin.stylesheet.tachyons.Tachyons

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.scalajs.slate.Slate._
// scalastyle:on underscore.import

final case class EditorWithMenu(
  placeholder: String,
  value: Value,
  onChange: Change => Callback
) {
  def apply(): VdomElement = EditorWithMenu.component(this)
}

object EditorWithMenu {

  private final val ComponentName = this.getClass.getSimpleName

  private case class State(hrefOpt: Option[String] = None, top: Double = -9999, left: Double = 0)

  private case class Backend(scope: BackendScope[EditorWithMenu, State]) {

    private val containerRef = Ref[HTMLElement]

    private def hide(props: EditorWithMenu, change: Change) = {
      scope.modState(_.copy(hrefOpt = None), props.onChange(change))
    }

    private def onChange(props: EditorWithMenu)(change: Change) = {
      change.value
        .inlines
        .filter(_.inlineType == LinkNode.nodeType)
        .first()
        .toOption
        .fold {
          hide(props, change)
        } { inline =>
          val href = DataUtil.value(inline.data, "href")
          if (href.isEmpty) {
            hide(props, change)
          } else {
            val selection = window.getSelection()

            // Do not show the menu if user select a text
            if (selection.rangeCount == 0 || scalajs.js.isUndefined(containerRef) || containerRef == null) { // scalastyle:ignore null
              hide(props, change)
            } else {
              // Get the bounding area of current selection
              val rect = selection.getRangeAt(0).getBoundingClientRect()

              for {
                containerRect <- containerRef.map(_.getBoundingClientRect()).get
                top = rect.top - containerRect.top
                left = rect.left - containerRect.left + 0.5 * rect.width
                newState <- scope.modState(_.copy(hrefOpt = Some(href), top = top, left = left), props.onChange(change))
              } yield newState
            }
          }
        }
    }

    def render(props: EditorWithMenu, state: State): VdomElement = {
      <.div.withRef(containerRef)(
        Tachyons.position.relative,

        ClickOutside(
          offsetBottom = 30,
          onClickOutside = Callback.when(state.hrefOpt.nonEmpty) {
            scope.modState(_.copy(hrefOpt = None))
          }
        )(
          RichEditor(
            placeholder = props.placeholder,
            value = props.value,
            onChange = onChange(props),
            readOnly = false
          )()
        ),

        <.div(
          ^.classSet(
            Tachyons
              .position.absolute.zIndex.z9999
              .color.white.backgroundColor.black
              .borderRadius.r3
              .padding.vertical1.padding.horizontal3
              .value -> true,
            Tachyons.opacity.zero.value -> state.hrefOpt.isEmpty,
            Tachyons.opacity.opacity100.value -> state.hrefOpt.nonEmpty
          ),
          ^.top := s"${state.top}px",
          ^.left := s"${state.left}px",
          ^.transform := "translate(-50%, 100%)",
          ^.transition := "opacity 0.75s",
          <.a(
            Tachyons.color.white.link.link,
            ^.href := state.hrefOpt.getOrElse(""),
            ^.target := "_blank",
            "Open link"
          )
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[EditorWithMenu](ComponentName)
    .initialState(State())
    .renderBackend[Backend]
    .build
}
