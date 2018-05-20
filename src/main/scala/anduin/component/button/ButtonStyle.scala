// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.button

import japgolly.scalajs.react.vdom.TagMod

import anduin.style.Style

object ButtonStyle {

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
  // ColorAccentBase provides the common styles for Accent Color: from Primary to Danger
  private case object ColorAccentBase {
    val link: TagMod = Style.hover.underline
    val minimal: TagMod = Style.hover.colorWhite.active.colorWhite
    val full: TagMod = Style.color.white.shadow.blur1Dark
    val selected: TagMod = Style.color.white
  }
  case object ColorPrimary extends Color {
    private val interact = Style.hover.backgroundPrimary3.active.backgroundPrimary5
    private val selfMinimal = Style.color.primary4.hover.shadowBorderPrimary5s.active.shadowBorderPrimary5s

    val link: TagMod = TagMod(ColorAccentBase.link, Style.color.primary4)
    val minimal: TagMod = TagMod(interact, ColorAccentBase.minimal, selfMinimal)
    val full: TagMod = TagMod(interact, ColorAccentBase.full, Style.backgroundColor.primary4.shadow.borderPrimary5s)
    val selected: TagMod = TagMod(ColorAccentBase.selected, Style.backgroundColor.primary5.shadow.borderPrimary5s)
  }
  case object ColorSuccess extends Color {
    private val interact = Style.hover.backgroundSuccess3.active.backgroundSuccess5
    private val selfMinimal = Style.color.success4.hover.shadowBorderSuccess5s.active.shadowBorderSuccess5s

    val link: TagMod = TagMod(ColorAccentBase.link, Style.color.success4)
    val minimal: TagMod = TagMod(interact, ColorAccentBase.minimal, selfMinimal)
    val full: TagMod = TagMod(interact, ColorAccentBase.full, Style.backgroundColor.success4.shadow.borderSuccess5s)
    val selected: TagMod = TagMod(ColorAccentBase.selected, Style.backgroundColor.success5.shadow.borderSuccess5s)
  }
  case object ColorWarning extends Color {
    private val interact = Style.hover.backgroundWarning3.active.backgroundWarning5
    private val selfMinimal = Style.color.warning4.hover.shadowBorderWarning5s.active.shadowBorderWarning5s

    val link: TagMod = TagMod(ColorAccentBase.link, Style.color.warning4)
    val minimal: TagMod = TagMod(interact, ColorAccentBase.minimal, selfMinimal)
    val full: TagMod = TagMod(interact, ColorAccentBase.full, Style.backgroundColor.warning4.shadow.borderWarning5s)
    val selected: TagMod = TagMod(ColorAccentBase.selected, Style.backgroundColor.warning5.shadow.borderWarning5s)
  }
  case object ColorDanger extends Color {
    private val interact = Style.hover.backgroundDanger3.active.backgroundDanger5
    private val selfMinimal = Style.color.danger4.hover.shadowBorderDanger5s.active.shadowBorderDanger5s

    val link: TagMod = TagMod(ColorAccentBase.link, Style.color.danger4)
    val minimal: TagMod = TagMod(interact, ColorAccentBase.minimal, selfMinimal)
    val full: TagMod = TagMod(interact, ColorAccentBase.full, Style.backgroundColor.danger4.shadow.borderDanger5s)
    val selected: TagMod = TagMod(ColorAccentBase.selected, Style.backgroundColor.danger5.shadow.borderDanger5s)
  }

  sealed trait Size { val style: TagMod }
  case object SizeLarge extends Size { val style: TagMod = Style.padding.ver12.padding.hor16.fontSize.px16 }
  case object SizeMedium extends Size { val style: TagMod = Style.padding.ver8.padding.hor12.fontSize.px14 }
  case object SizeSmall extends Size { val style: TagMod = Style.padding.ver4.padding.hor8.fontSize.px12 }
  case object SizeIcon extends Size { val style: TagMod = Style.padding.all8.fontSize.px14 }

  sealed trait Style
  case object StyleFull extends Style
  case object StyleLink extends Style
  case object StyleMinimal extends Style

  // This should be used for Library Engineering only, thus under `component`.
  // It should not be accessible for Product Engineering or any consumer of
  // `anduin.component`.
  private[component] def getStyles(
    color: Color,
    size: Size,
    style: Style,
    isSelected: Boolean,
    isFullWidth: Boolean
  ): TagMod = TagMod(
    // Common styles for StyleLink, StyleFull and StyleMinimal
    Style.disabled.colorGray6.whiteSpace.noWrap,
    // - "a" has something like min-width 100% by browser's default.
    // - Meanwhile "button" has content-based width.
    // Use this to enforce always 100% width for more consistency
    TagMod.when(isFullWidth) { Style.width.pc100 },
    if (isSelected) { color.selected } else {
      style match {
        case StyleFull    => color.full
        case StyleMinimal => color.minimal
        case StyleLink    => color.link
      }
    },
    // Specific styles for each Style
    TagMod.when(style == StyleLink) { Style.flexbox.inlineFlex },
    TagMod.when(style == StyleFull) { Style.disabled.backgroundGray2.disabled.shadowBorderGray4 },
    TagMod.when(style == StyleMinimal || style == StyleFull)(
      TagMod( // This TagMod wrapper is only for grouping purpose
        Style.lineHeight.px16.fontWeight.medium.borderRadius.px2,
        Style.flexbox.flex.flexbox.justifyCenter,
        Style.focus.outline.transition.allWithOutline,
        size.style
      )
    )
  )
}
