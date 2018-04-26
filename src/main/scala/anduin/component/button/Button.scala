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
  color: Button.Color = Button.ColorWhite,
  size: Button.Size = Button.SizeMedium,
  onClick: Callback = Callback.empty,
  style: Button.Style = Button.StyleFull,
  isFullWidth: Boolean = false,
  isDisabled: Boolean = false, // if tpe != link
  isSelected: Boolean = false,
  href: String = "" // if tpe == link
) {
  def apply(children: VdomNode*): VdomElement = {
    Button.component(this)(children: _*)
  }
}

object Button {

  private final val ComponentName = ComponentUtils.name(this)

  sealed trait Color {
    val link: TagMod
    val minimal: TagMod
    val full: TagMod
    val selected: TagMod
  }
  case object ColorWhite extends Color {
    private val interactHover = Style.hover.colorPrimary4.hover.backgroundWhite
    private val interactActive = Style.active.colorPrimary4.active.backgroundGray2
    private val interact = TagMod(interactHover, interactActive)

    val link: TagMod = Style.color.primary4.hover.underline
    val minimal: TagMod = TagMod(interact, Style.color.gray8.hover.shadowBorderGray4s.active.shadowBorderGray4s)
    val full: TagMod = TagMod(minimal, Style.backgroundColor.gray1.shadow.borderGray4s)
    val selected: TagMod = Style.color.primary4.backgroundColor.gray2.shadow.borderGray4s
  }
  // ColorBase provides the common styles for Accent Color: from Primary to Danger
  private case object ColorBase {
    val link: TagMod = Style.hover.underline
    val minimal: TagMod = Style.hover.colorWhite.active.colorWhite
    val full: TagMod = Style.color.white.shadow.blur1Dark
    val selected: TagMod = Style.color.white
  }
  case object ColorPrimary extends Color {
    private val interact = Style.hover.backgroundPrimary3.active.backgroundPrimary5
    private val selfMinimal = Style.color.primary4.hover.shadowBorderPrimary5s.active.shadowBorderPrimary5s

    val link: TagMod = TagMod(ColorBase.link, Style.color.primary4)
    val minimal: TagMod = TagMod(interact, ColorBase.minimal, selfMinimal)
    val full: TagMod = TagMod(interact, ColorBase.full, Style.backgroundColor.primary4.shadow.borderPrimary5s)
    val selected: TagMod = TagMod(ColorBase.selected, Style.backgroundColor.primary5.shadow.borderPrimary5s)
  }
  case object ColorSuccess extends Color {
    private val interact = Style.hover.backgroundSuccess3.active.backgroundSuccess5
    private val selfMinimal = Style.color.success4.hover.shadowBorderSuccess5s.active.shadowBorderSuccess5s

    val link: TagMod = TagMod(ColorBase.link, Style.color.success4)
    val minimal: TagMod = TagMod(interact, ColorBase.minimal, selfMinimal)
    val full: TagMod = TagMod(interact, ColorBase.full, Style.backgroundColor.success4.shadow.borderSuccess5s)
    val selected: TagMod = TagMod(ColorBase.selected, Style.backgroundColor.success5.shadow.borderSuccess5s)
  }
  case object ColorWarning extends Color {
    private val interact = Style.hover.backgroundWarning3.active.backgroundWarning5
    private val selfMinimal = Style.color.warning4.hover.shadowBorderWarning5s.active.shadowBorderWarning5s

    val link: TagMod = TagMod(ColorBase.link, Style.color.warning4)
    val minimal: TagMod = TagMod(interact, ColorBase.minimal, selfMinimal)
    val full: TagMod = TagMod(interact, ColorBase.full, Style.backgroundColor.warning4.shadow.borderWarning5s)
    val selected: TagMod = TagMod(ColorBase.selected, Style.backgroundColor.warning5.shadow.borderWarning5s)
  }
  case object ColorDanger extends Color {
    private val interact = Style.hover.backgroundDanger3.active.backgroundDanger5
    private val selfMinimal = Style.color.danger4.hover.shadowBorderDanger5s.active.shadowBorderDanger5s

    val link: TagMod = TagMod(ColorBase.link, Style.color.danger4)
    val minimal: TagMod = TagMod(interact, ColorBase.minimal, selfMinimal)
    val full: TagMod = TagMod(interact, ColorBase.full, Style.backgroundColor.danger4.shadow.borderDanger5s)
    val selected: TagMod = TagMod(ColorBase.selected, Style.backgroundColor.danger5.shadow.borderDanger5s)
  }

  sealed trait Tpe { val value: String }
  case object TpeLink extends Tpe { val value = "link" }
  case object TpeDefault extends Tpe { val value = "button" }
  case object TpeSubmit extends Tpe { val value = "submit" }
  case object TpeReset extends Tpe { val value = "reset" }

  sealed trait Style
  case object StyleFull extends Style
  case object StyleLink extends Style
  case object StyleMinimal extends Style

  sealed trait Size { val style: TagMod }
  case object SizeLarge extends Size { val style: TagMod = Style.padding.ver12.padding.hor16.fontSize.px16 }
  case object SizeMedium extends Size { val style: TagMod = Style.padding.ver8.padding.hor16.fontSize.px14 }
  case object SizeSmall extends Size { val style: TagMod = Style.padding.ver4.padding.hor8.fontSize.px12 }
  case object SizeIcon extends Size { val style: TagMod = Style.padding.all8.fontSize.px14 }

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
      val mods = TagMod(
        // common styles for all Style
        Style.disabled.colorGray6,
        // ===
        TagMod.when(props.style == StyleLink)(
          TagMod(
            Style.flexbox.inlineFlex.flexbox.itemsCenter,
            props.color.link
          )
        ),
        TagMod.when(props.style == StyleFull) {
          Style.disabled.backgroundGray2.disabled.shadowBorderGray4
        },
        // common styles for StyleMinimal and StyleFull
        TagMod.when(props.style == StyleMinimal || props.style == StyleFull)(
          TagMod(
            if (props.isSelected) { props.color.selected } else {
              props.style match {
                case StyleFull    => props.color.full
                case StyleMinimal => props.color.minimal
                case _            => TagMod.empty // No StyleLink here
              }
            },
            Style.lineHeight.px16.fontWeight.medium.borderRadius.px2,
            Style.flexbox.flex.focus.outline.transition.allWithOutline,
            TagMod.when(props.isFullWidth) { Style.width.pc100.flexbox.justifyCenter },
            props.size.style
          )
        ),
        // onClick.isEmpty: allow default behaviour if onClick is not defined
        // eg: TpeSubmit and TpeReset
        TagMod.when(!props.isDisabled && !props.onClick.isEmpty_?) { ^.onClick ==> onClick },
        // content
        children
      )
      props.tpe match {
        // link that looks like a button
        case TpeLink => <.a(^.href := props.href, mods)
        // real button
        case _ => <.button(^.tpe := props.tpe.value, ^.disabled := props.isDisabled, mods)
      }
    }
  }

  private val component = ScalaComponent
    .builder[Button](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
