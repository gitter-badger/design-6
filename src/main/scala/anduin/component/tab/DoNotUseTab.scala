// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.tab

import anduin.component.util.JavaScriptUtils
import org.scalajs.dom.html.Div

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object DoNotUseTab {

  private val ComponentName = this.getClass.getSimpleName

  type ClickPanelHandler = Int => Callback

  final case class Header(children: TagMod*)
  final case class Content(children: TagMod*)

  final case class Panel(
    header: Header,
    active: Boolean = false,
    headerClass: String = "",
    content: Content = Content(),
    target: String = JavaScriptUtils.voidMethod,
    onClick: Option[ClickPanelHandler] = None,
    enabled: Boolean = true,
    lazyloadContent: Boolean = false,
    hide: Boolean = false
  )

  private case class Props(
    panels: Seq[Panel],
    activeIndex: Int,
    isVertical: Boolean,
    additionalTabHeaderClasses: String = "",
    additionalTabContentClasses: String = ""
  )
  private case class State(activeIndex: Int, isVertical: Boolean = false)

  private class Backend(t: BackendScope[Props, State]) {

    def onClickTab(panel: Panel, index: Int)(evt: ReactEventFromHtml): Callback = {
      for {
        _ <- evt.preventDefaultCB
        _ <- t.modState(_.copy(activeIndex = index))
        _ <- Callback.traverseOption(panel.onClick) { onClick =>
          onClick(index)
        }
      } yield ()
    }

    def render(props: Props, state: State): VdomTagOf[Div] = {
      val verticalTab = if (state.isVertical) {
        "tab-vertical -left"
      } else {
        ""
      }

      <.div(
        ^.cls := "tab-wrapper w-100",
        <.ul(
          ^.cls := s"tab-nav $verticalTab ${props.additionalTabHeaderClasses}",
          props.panels.zipWithIndex.toTagMod {
            case (item, index) =>
              TagMod.unless(item.hide) {
                <.li(
                  ^.key := index,
                  ^.classSet(
                    s"tab-item cursor-pointer ${item.headerClass}" -> true,
                    "-active" -> (index == state.activeIndex),
                    "disabled" -> !item.enabled
                  ),
                  TagMod.when(item.target == JavaScriptUtils.voidMethod && item.enabled) {
                    ^.onClick ==> onClickTab(item, index)
                  },
                  <.a(
                    ^.cls := "link",
                    ^.href := item.target,
                    TagMod(item.header.children: _*)
                  )
                )
              }
          }
        ),
        TagMod.when(props.panels.nonEmpty)(
          <.div(
            ^.cls := s"tab-content ${props.additionalTabContentClasses}",
            props.panels.zipWithIndex.toTagMod {
              case (item, index) =>
                <.div(
                  ^.key := index,
                  ^.classSet(
                    "tab-panel" -> true,
                    "active" -> (index == state.activeIndex)
                  ),
                  TagMod.when(!item.lazyloadContent || index == state.activeIndex)(TagMod(item.content.children: _*))
                )
            }
          )
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](ComponentName)
    .initialStateFromProps { props =>
      // Determine the active tab index if it is set
      val index = props.panels.indexWhere(_.active)
      val activeIndex = if (index < 0) Math.max(props.activeIndex, 0) else index
      State(activeIndex, props.isVertical)
    }
    .renderBackend[Backend]
    .componentWillReceiveProps { scope =>
      val nextActiveIndex = scope.nextProps.panels.indexWhere(_.active)
      if (nextActiveIndex >= 0 && nextActiveIndex != scope.state.activeIndex) {
        // nextActiveIndex is a valid value
        scope.modState(_.copy(activeIndex = nextActiveIndex))
      } else {
        // If the current active index is out of range for the new list of panels, reset to 0
        Callback.when(scope.state.activeIndex >= scope.nextProps.panels.size) {
          scope.modState(_.copy(activeIndex = 0))
        }
      }
    }
    .build

  def apply(
    activeIndex: Int = 0,
    isVertical: Boolean = false,
    additionalTabHeaderClasses: String = "",
    additionalTabContentClasses: String = ""
  )(panels: Panel*): ScalaComponent.Unmounted[_, _, _] = {
    component(Props(panels, activeIndex, isVertical, additionalTabHeaderClasses, additionalTabContentClasses))
  }
}
