// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.suggest

import anduin.component.menu.Menu
import anduin.component.popover.PopoverContent
import anduin.component.portal.PortalPosition
import anduin.scalajs.downshift.DownshiftRenderProps
import anduin.scalajs.util.ScalaJSUtils
import anduin.style.Style
import org.scalajs.dom.raw.HTMLElement

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[suggest] class SuggestContent[A] {

  def apply(): Props.type = Props

  private val SuggestOpt = (new SuggestOpt[A])()

  case class Props(
    suggest: Suggest[A]#Props,
    downshift: DownshiftRenderProps[A],
    targetRef: Ref.Simple[HTMLElement]
  ) {
    def apply(): VdomElement = component(this)
  }

  private def renderOpt(props: Props)(tuple: (Suggest.Opt[A], Int)) = {
    <.div(
      ^.key := props.suggest.opts.getValueString(tuple._1.value),
      SuggestOpt(props.suggest, Some(props.downshift), tuple._1, tuple._2)()
    )
  }

  private def renderContent(props: Props): VdomElement = {
    <.div(
      ScalaJSUtils.jsPropsToTagMod(props.downshift.getMenuProps()),
      Menu()(
        <.div(
          TagMod(^.maxHeight := "40vh", Style.overflow.autoY),
          props.suggest.opts.items.zipWithIndex.toVdomArray(renderOpt(props))
        )
      )
    )
  }

  private def render(props: Props): VdomElement = {
    PopoverContent(
      targetRef = props.targetRef,
      onOverlayClick = None,
      position = PortalPosition.BottomLeft,
      isAutoFocus = false
    )(renderContent(props))
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
