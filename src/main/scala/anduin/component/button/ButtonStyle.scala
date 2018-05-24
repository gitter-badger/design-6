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
    private val interact = TagMod( // for minimal and full
      Style.color.gray8.hover.colorPrimary4.active.colorPrimary4,
      Style.hover.backgroundWhite.active.backgroundGray2
    )

    val link: TagMod = Style.color.primary4
    val minimal = TagMod(interact, Style.hover.borderGray4.hover.shadow1Light.active.borderGray4.active.shadow1Light)
    val full = TagMod(interact, Style.shadow.blur1Light.borderColor.gray4.backgroundColor.gray1)
    val selected: TagMod = Style.borderColor.gray4.color.primary4.backgroundColor.gray2
  }
  // ColorAccentBase provides the common styles for Accent Color: from Primary to Danger
  private case object ColorAccentBase {
    val minimal: TagMod = Style.hover.colorWhite.hover.shadow1Dark.active.colorWhite.active.shadow1Dark
    val full: TagMod = Style.color.white.shadow.blur1Dark
    val selected: TagMod = Style.color.white
  }
  case object ColorPrimary extends Color {
    private val interact = Style.hover.backgroundPrimary3.active.backgroundPrimary5
    private val selfMinimal = Style.color.primary4.hover.borderPrimary5.active.borderPrimary5

    val link: TagMod = Style.color.primary4
    val minimal: TagMod = TagMod(interact, ColorAccentBase.minimal, selfMinimal)
    val full: TagMod = TagMod(interact, ColorAccentBase.full, Style.backgroundColor.primary4.borderColor.primary5)
    val selected: TagMod = TagMod(ColorAccentBase.selected, Style.backgroundColor.primary5.borderColor.primary5)
  }
  case object ColorSuccess extends Color {
    private val interact = Style.hover.backgroundSuccess3.active.backgroundSuccess5
    private val selfMinimal = Style.color.success4.hover.borderSuccess5.active.borderSuccess5

    val link: TagMod = Style.color.success4
    val minimal: TagMod = TagMod(interact, ColorAccentBase.minimal, selfMinimal)
    val full: TagMod = TagMod(interact, ColorAccentBase.full, Style.backgroundColor.success4.borderColor.success5)
    val selected: TagMod = TagMod(ColorAccentBase.selected, Style.backgroundColor.success5.borderColor.success5)
  }
  case object ColorWarning extends Color {
    private val interact = Style.hover.backgroundWarning3.active.backgroundWarning5
    private val selfMinimal = Style.color.warning4.hover.borderWarning5.active.borderWarning5

    val link: TagMod = Style.color.warning4
    val minimal: TagMod = TagMod(interact, ColorAccentBase.minimal, selfMinimal)
    val full: TagMod = TagMod(interact, ColorAccentBase.full, Style.backgroundColor.warning4.borderColor.warning5)
    val selected: TagMod = TagMod(ColorAccentBase.selected, Style.backgroundColor.warning5.borderColor.warning5)
  }
  case object ColorDanger extends Color {
    private val interact = Style.hover.backgroundDanger3.active.backgroundDanger5
    private val selfMinimal = Style.color.danger4.hover.borderDanger5.active.borderDanger5

    val link: TagMod = Style.color.danger4
    val minimal: TagMod = TagMod(interact, ColorAccentBase.minimal, selfMinimal)
    val full: TagMod = TagMod(interact, ColorAccentBase.full, Style.backgroundColor.danger4.borderColor.danger5)
    val selected: TagMod = TagMod(ColorAccentBase.selected, Style.backgroundColor.danger5.borderColor.danger5)
  }

  sealed trait Size { val style: TagMod }
  case object SizeLarge extends Size { val style: TagMod = Style.height.px40.padding.hor16.fontSize.px16 }
  case object SizeMedium extends Size { val style: TagMod = Style.height.px32.padding.hor12.fontSize.px14 }
  case object SizeSmall extends Size { val style: TagMod = Style.height.px24.padding.hor8.fontSize.px12 }
  case object SizeIcon extends Size { val style: TagMod = Style.height.px32.width.px32.fontSize.px14 }

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
    if (isSelected) { TagMod(Style.border.all, color.selected) } else {
      style match {
        case StyleFull    => color.full
        case StyleMinimal => color.minimal
        case StyleLink    => color.link
      }
    },
    // Specific styles for each Style
    TagMod.when(style == StyleLink) { Style.flexbox.inlineFlex.hover.underline },
    TagMod.when(style == StyleFull) {
      Style.border.all.disabled.backgroundGray2.disabled.borderGray4.disabled.shadowNone
    },
    TagMod.when(style == StyleMinimal) { Style.border.all.borderColor.transparent },
    TagMod(
      Style.lineHeight.px16.fontWeight.medium.borderRadius.px2,
      Style.flexbox.flex.flexbox.justifyCenter.flexbox.itemsCenter,
      Style.focus.outline.transition.allWithOutline,
      size.style
    ).when(style == StyleMinimal || style == StyleFull)
  )
}
