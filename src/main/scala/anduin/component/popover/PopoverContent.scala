// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.popover

import anduin.component.portal.{PortalPopperContent, PortalPosition, PortalUtils}
import anduin.scalajs.popper.Popper
import anduin.style.Style
import org.scalajs.dom
import org.scalajs.dom.raw

import scala.scalajs.js

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PopoverContent(
  // To where the content should be anchored to
  targetRef: Ref.Simple[raw.HTMLElement],
  // Callback when users click on outside:
  // - None works like overlay.pointerEvents = none
  // - Some(Callback.empty) works like overlay.pointerEvents = auto
  onOverlayClick: Option[Callback],
  // Same as Popover's `position` prop
  position: PortalPosition = PortalPosition.TopCenter,
  // Whether users' focus should be shifted into the popover automatically
  // - Usually this should be true so keyboard navigation and "Esc to close"
  //   would work out of the box
  // - However, this should be turned off when the focus should stay outside
  //   of the popover (e.g. Dropdown's target)
  isAutoFocus: Boolean = true,
  isClosable: Option[PortalUtils.IsClosable] = PortalUtils.defaultIsClosable
) {
  def apply(children: VdomNode*): VdomElement =
    PopoverContent.component(this)(children: _*)
}

object PopoverContent {

  private type Props = PopoverContent

  // Backward compatible
  // https://github.com/anduintransaction/stargazer/issues/17011
  private val overlayStaticMod: TagMod =
    Style.position.absolute.position.pinAll.zIndex.idx999

  private val bodyStaticMod: TagMod = TagMod(
    // Common Popper content styles
    PortalPopperContent.staticStyles,
    // Specific styles of Popover
    Style.background.gray0.borderRadius.px4.overflow.hidden.shadow.px8,
    Style.border.all.borderColor.gray4.borderWidth.px1,
    // Avoid position transitioning. We only want the opacity here
    ^.transition := "opacity 250ms",
    // Reverse overlay's possible pointerEvents none (see its render)
    Style.pointerEvents.auto
  )

  // Don't use Style.transition.allWithOutline here because it will also
  // apply to Popper's transformation, which we don't want
  private def getIsAutoFocusMod(props: Props): TagMod =
    TagMod.when(props.isAutoFocus) {
      TagMod(^.tabIndex := 0, Style.outline.focusLight)
    }

  class Backend(scope: BackendScope[Props, _]) {

    private val ref = Ref[raw.HTMLElement]
    private var popperOpt: Option[Popper] = None // scalastyle:ignore var.field

    // To observe content's HTML to ask Popper to re-position it if necessary
    private val observerOptions = raw.MutationObserverInit(subtree = true, characterData = true, childList = true)
    private def observerCallback(records: js.Array[raw.MutationRecord], instance: raw.MutationObserver): Unit = {
      val _ = (records, instance)
      popperOpt.foreach(_.scheduleUpdate())
    }
    private val observer = new raw.MutationObserver(observerCallback)

    private def renderContent(props: Props, children: PropsChildren): VdomElement = {
      <.div(
        overlayStaticMod,
        props.onOverlayClick.fold[TagMod](Style.pointerEvents.none) { onClick =>
          PortalUtils.getClosableMods(props.isClosable, onClick)
        },
        <.div.withRef(ref)(getIsAutoFocusMod(props), bodyStaticMod, children)
      )
    }

    def render(props: Props, children: PropsChildren): VdomNode = {
      ReactPortal(renderContent(props, children), dom.document.body)
    }

    private def onPopperCreated(props: Props)(data: Popper.Data): Unit = {
      val content = data.instance.popper
      // Watch for content's change to re-position
      observer.observe(content, observerOptions)
      // Only focus if asked and necessary
      val isFocused = content.contains(dom.document.activeElement)
      if (props.isAutoFocus && !isFocused) {
        content.focus()
      }
    }

    def initPopper: Callback = {
      for {
        props <- scope.props
        target <- props.targetRef.get
        content <- ref.get
        _ <- Callback {
          val options = PortalPopperContent.getOptions(
            onCreated = onPopperCreated(props),
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
    .stateless
    .renderBackendWithChildren[Backend]
    .componentDidMount(_.backend.initPopper)
    .componentWillUnmount(_.backend.destroyPopper)
    .build
}
