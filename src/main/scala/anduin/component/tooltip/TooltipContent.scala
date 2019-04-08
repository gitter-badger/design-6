// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.tooltip

import anduin.component.portal.{PortalPopperContent, PortalPosition}
import anduin.scalajs.popper.{Popper, PopperPlacement}
import anduin.style.Style
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class TooltipContent(
  // To where the content should be anchored to
  targetRef: Ref.Simple[HTMLElement],
  // Same as Tooltip's `position` prop
  position: PortalPosition = PortalPosition.TopCenter,
  text: VdomNode
) {
  def apply(): VdomElement = TooltipContent.component(this)
}

object TooltipContent {

  private type Props = TooltipContent

  private val staticMod: TagMod = TagMod(
    // Common popper content styles
    PortalPopperContent.staticStyles,
    // Specific styles of Tooltip
    Style.background.gray9.color.gray0.shadow.px8,
    Style.maxWidth.px256.padding.ver4.padding.hor8.borderRadius.px4,
    // Note that there is no transition here, not even opacity, because it
    // looks pretty bad in the case of toolbars (adjacent tooltip targets)
    ^.transition := "none",
    // Allow line breaking
    Style.whiteSpace.preWrap,
    // Backward compatible
    // https://github.com/anduintransaction/stargazer/issues/17011
    Style.zIndex.idx999
  )

  // This will be set by Popper. The reason we need this is because:
  // 1. Popper's actual placement is calculated outside of React world
  // 2. We need the actual placement to properly align the arrow in both axises
  //    (Popper only align the arrow in 1 axis)
  // Having this in State allow React to update the arrow properly, and the
  // code is much cleaner than we update the DOM directly
  // - Note that this is NOT an equivalent of props.position, because Popper
  //   might flip the position if there is not enough space
  case class State(placement: PopperPlacement)

  class Backend(scope: BackendScope[Props, State]) {

    private val ref = Ref[HTMLElement]
    private var popperOpt: Option[Popper] = None // scalastyle:ignore var.field

    // Rendering

    private def renderContent(props: Props, state: State): VdomElement = {
      val arrow = TooltipArrow(placement = state.placement)()
      <.div.withRef(ref)(staticMod, props.text, arrow)
    }

    def render(props: Props, state: State): VdomNode = {
      ReactPortal(renderContent(props, state), dom.document.body)
    }

    // Lifecycle

    private def setPlacement(data: Popper.Data): Unit = {
      val placement = PopperPlacement
        .fromString(data.placement)
        .getOrElse(PopperPlacement.TopMiddle)
      scope.modState(_.copy(placement = placement)).runNow()
    }

    def initPopper: Callback = {
      for {
        props <- scope.props
        target <- props.targetRef.get
        content <- ref.get
        _ <- Callback {
          val options = PortalPopperContent.getOptions(
            onCreated = setPlacement,
            position = props.position
          )
          this.popperOpt = Some(new Popper(target, content, options))
        }
      } yield ()
    }

    def destroyPopper: Callback =
      Callback.traverseOption(popperOpt)(p => Callback(p.destroy()))
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State(placement = PopperPlacement.TopMiddle))
    .renderBackend[Backend]
    .componentDidMount(_.backend.initPopper)
    .componentWillUnmount(_.backend.destroyPopper)
    .build
}
