// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.button

import anduin.component.util.ComponentUtils
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Button(
  tpe: Button.Tpe = Button.TpeDefault,
  color: Button.Color = Button.ColorGray,
  size: Button.Size = Button.SizeMedium,
  onClick: Callback = Callback.empty,
  isDisabled: Boolean = false, // if tpe != link
  href: String = "" // if tpe == link
) {
  def apply(children: VdomNode*): VdomElement = {
    Button.component(this)(children: _*)
  }
}

object Button {

  private final val ComponentName = ComponentUtils.name(this)

  sealed trait Color { val style: TagMod }
  case object ColorNone extends Color {
    val base: TagMod =
      Style.color.gray8.hover.backgroundWhite.hover.colorPrimary4.active.backgroundGray2.active.colorPrimary4
    val activeShadow: TagMod = Style.hover.shadowBorderGray4.active.shadowBorderGray4
    val style: TagMod = TagMod(base, activeShadow)
  }
  case object ColorGray extends Color {
    private val standby = Style.backgroundColor.gray1.shadow.borderGray4S
    val style: TagMod = TagMod(ColorNone.base, standby)
  }
  private case object ColorCommon {
    val style: TagMod = Style.color.white.shadow.size2
  }
  case object ColorPrimary extends Color {
    private val standby = Style.backgroundColor.primary4.shadow.borderPrimary5S
    private val active = Style.hover.backgroundPrimary3.active.backgroundPrimary5
    val style: TagMod = TagMod(ColorCommon.style, standby, active)
  }
  case object ColorSuccess extends Color {
    private val standby = Style.backgroundColor.success4.shadow.borderSuccess5S
    private val active = Style.hover.backgroundSuccess3.active.backgroundSuccess5
    val style: TagMod = TagMod(ColorCommon.style, standby, active)
  }
  case object ColorWarning extends Color {
    private val standby = Style.backgroundColor.warning4.shadow.borderWarning5S
    private val active = Style.hover.backgroundWarning3.active.backgroundWarning5
    val style: TagMod = TagMod(ColorCommon.style, standby, active)
  }
  case object ColorDanger extends Color {
    private val standby = Style.backgroundColor.danger4.shadow.borderDanger5S
    private val active = Style.hover.backgroundDanger3.active.backgroundDanger5
    val style: TagMod = TagMod(ColorCommon.style, standby, active)
  }

  sealed trait Tpe { val value: String }
  case object TpeLink extends Tpe { val value = "link" }
  case object TpeDefault extends Tpe { val value = "button" }
  case object TpeSubmit extends Tpe { val value = "submit" }
  case object TpeReset extends Tpe { val value = "reset" }

  sealed trait Size { val style: TagMod }
  case object SizeLarge extends Size { val style: TagMod = Style.padding.vertical3.padding.horizontal4 }
  case object SizeMedium extends Size { val style: TagMod = Style.padding.vertical2.padding.horizontal4 }
  case object SizeSmall extends Size { val style: TagMod = Style.padding.vertical1.padding.horizontal2 }

  private case class Backend(scope: BackendScope[Button, _]) {

    private def onClick(e: ReactEventFromHtml) = {
      for {
        _ <- e.preventDefaultCB
        _ <- e.stopPropagationCB
        props <- scope.props
        _ <- props.onClick
      } yield ()
    }

    def render(props: Button, children: PropsChildren): VdomElement = {
      val commonMods = TagMod(
        // common styles for button
        Style.lineHeight.size1.fontWeight.size500.borderRadius.r1,
        // common disabled styles
        Style.disabled.colorGray6.disabled.backgroundGray2,
        Style.disabled.shadowBorderGray4,
        // help with usage with icon
        Style.flexbox.flex,
        // specific styles
        props.size.style,
        props.color.style,
        // behaviours
        ^.onClick ==> onClick,
        children
      )
      props.tpe match {
        // link that looks like a button
        case TpeLink => <.a(^.href := props.href, commonMods)
        // real button
        case _ =>
          val tpe = props.tpe.value
          <.button(^.tpe := tpe, ^.disabled := props.isDisabled, commonMods)
      }
    }
  }

  private val component = ScalaComponent
    .builder[Button](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
