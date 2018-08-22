// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Tooltip(
  // Portal common props (see Portal for detail)
  renderTarget: VdomNode,
  renderContent: () => VdomNode,
  // PortalPopper common props (see PortalPopper for detail)
  position: Position = PositionTop,
  targetTag: HtmlTag = <.div,
  // Tooltip specific props
  isDisabled: Boolean = false
) {
  def apply(): VdomElement = { Tooltip.component(this) }
}

object Tooltip {

  private type Props = Tooltip

  private def renderTarget(props: Props)(popper: PortalPopper.TargetProps) = {
    props.targetTag.withRef(popper.ref)(
      ^.onMouseEnter --> Callback.when(!popper.isOpened)(popper.toggle), // open
      ^.onMouseLeave --> Callback.when(popper.isOpened)(popper.toggle), // close
      props.renderTarget
    )
  }

  private val contentStyles = TagMod(
    Style.backgroundColor.gray9.color.white.shadow.blur8,
    Style.padding.ver4.padding.hor8.borderRadius.px4
  )

  private val arrow = <.div(
    VdomAttr("x-arrow") := "", // Popper's default arrow selector
    Style.position.absolute.backgroundColor.gray9,
    TagMod(^.width := "8px", ^.height := "8px"),
    ^.transform := "rotate(45deg)"
  )

  private def renderContent(props: Props)(popper: PortalPopper.ContentProps): VdomElement = {
    val body = props.renderContent()
    <.div.withRef(popper.ref)(popper.styles, contentStyles, body, arrow)
  }

  private def render(props: Tooltip): VdomNode = {
    if (props.isDisabled) {
      // it is intentional to render targetTag wrapper here to keep the
      // HTML result consistent with the other case
      props.targetTag(props.renderTarget)
    } else {
      PortalPopper(
        renderTarget = renderTarget(props),
        renderContent = renderContent(props),
        // ===
        position = props.position,
        offsetHor = 0,
        offsetVer = 8 // for arrow
      )()
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
