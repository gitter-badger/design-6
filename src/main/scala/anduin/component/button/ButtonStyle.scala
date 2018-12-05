// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.button

import anduin.component.icon.Icon
import anduin.component.progressindicators.CircleIndicator
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[button] object ButtonStyle {

  object Size {
    trait Fix24 extends Button.Size {
      private def common = Style.height.px24.fontSize.px12
      final def square: TagMod = TagMod(common, ^.width := "24px")
      final def rect: TagMod = TagMod(common, Style.padding.hor8)
    }
    trait Fix32 extends Button.Size {
      private def common = Style.height.px32.fontSize.px13
      final def square: TagMod = TagMod(common, Style.width.px32)
      final def rect: TagMod = TagMod(common, Style.padding.hor12)
    }
    trait Fix40 extends Button.Size {
      private def common = Style.height.px40.fontSize.px16
      final def square: TagMod = TagMod(common, ^.width := "40px")
      final def rect: TagMod = TagMod(common, Style.padding.hor16)
    }
    trait Free extends Button.Size {
      final def square: TagMod = TagMod.empty
      final def rect: TagMod = TagMod.empty
    }
  }

  object Color {
    trait White extends Button.Color
    trait Black extends Button.Color
    trait Blue extends Button.Color
    trait Green extends Button.Color
    trait Red extends Button.Color
  }

  object BStyle {

    // convenient shortcut
    private val bgc = Style.backgroundColor
    private val bc = Style.borderColor

    // Private traits

    sealed trait LightText {
      def color: Button.Color

      final def lightTextNormal: TagMod = color match {
        case _: Color.White => Style.color.white
        case _: Color.Black => Style.color.gray8
        case _: Color.Blue  => Style.color.primary4
        case _: Color.Green => Style.color.success4
        case _: Color.Red   => Style.color.danger4
      }
    }

    sealed trait Light extends LightText {
      def isSelected: Boolean
      def isBusy: Boolean

      // scalastyle:off cyclomatic.complexity
      private def is: Boolean = isSelected || isBusy
      private def bg: TagMod = color match {
        case _: Color.White => if (is) bgc.gray6 else Style.hover.backgroundGray7.active.backgroundGray6
        case _: Color.Black => if (is) bgc.gray6 else Style.hover.backgroundGray3.active.backgroundGray6
        case _: Color.Blue  => if (is) bgc.primary4 else Style.hover.backgroundPrimary1.active.backgroundPrimary4
        case _: Color.Red   => if (is) bgc.danger4 else Style.hover.backgroundDanger1.active.backgroundDanger4
        case _: Color.Green => if (is) bgc.success4 else Style.hover.backgroundSuccess1.active.backgroundSuccess4
      }
      // scalastyle:on cyclomatic.complexity
      private def text: TagMod = if (is) Style.color.white else TagMod(lightTextNormal, Style.active.colorWhite)
      final def lightNormal: TagMod = TagMod(text, bg)
    }

    sealed trait NonLink {
      def color: Button.Color
      def size: Button.Size
      def icon: Option[Icon.Name]
      def isFullWidth: Boolean
      def isBusy: Boolean
      def isSelected: Boolean

      // Size
      final def sizeSquare: TagMod = size.square
      private def sizeWidth = if (isFullWidth) Style.width.pc100 else Style.width.maxContent
      final def sizeRect: TagMod = TagMod(sizeWidth, size.rect)

      // Body & Container
      final def nonLinkContainer: TagMod = TagMod(
        Style.lineHeight.px16.fontWeight.medium.whiteSpace.noWrap,
        Style.focus.outline.transition.allWithOutline.borderRadius.px2,
        Style.display.block.position.relative,
        TagMod.when(isBusy)(Style.pointerEvents.none)
      )
      final def body: TagMod = TagMod(
        Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter,
        TagMod.when(isBusy)(Style.opacity.pc0)
      )

      // Overlay
      final def overlay: VdomNode = if (isBusy) NonLink.indicator else EmptyVdom

      final def nonLinkDisabled: TagMod = Style.color.gray5.backgroundColor.gray1
    }

    object NonLink {
      private val indicator = <.span(
        Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter,
        Style.position.absolute.coordinate.fill,
        CircleIndicator()()
      )
    }

    sealed trait Border {
      final def borderContainer: TagMod = Style.border.all
      final def borderDisabled: TagMod = Style.borderColor.gray3
    }

    // Public traits (for Button.scala)

    trait Link extends Button.Style with LightText {
      def isBlock: Boolean

      final def icon: Option[Icon.Name] = None
      final def container: TagMod = TagMod(
        Style.hover.underline,
        TagMod.when(isBlock)(Style.display.block)
      )
      final def body: TagMod = TagMod.empty
      final def overlay: VdomNode = EmptyVdom

      final def colorDisabled: TagMod = Style.color.gray5
      final def colorNormal: TagMod = lightTextNormal
      // There should be no size in Link
      final def sizeSquare: TagMod = TagMod.empty
      final def sizeRect: TagMod = TagMod.empty
    }

    trait Full extends Button.Style with NonLink with Border {
      final def container: TagMod = TagMod(borderContainer, nonLinkContainer)
      final def colorDisabled: TagMod = TagMod(borderDisabled, nonLinkDisabled)

      // Styles that are different between White and other colors
      private def text: TagMod = color match {
        case _: Color.White => Style.color.gray8
        case _              => Style.color.white
      }
      private def shadow: TagMod = color match {
        case _: Color.White => Style.shadow.blur1Light
        case _              => Style.shadow.blur1Dark
      }
      // Styles that are different between colors
      // scalastyle:off cyclomatic.complexity
      private def is: Boolean = isSelected || isBusy
      private def bg: TagMod = color match {
        case _: Color.White => if (is) bgc.gray2 else bgc.gray1.hover.backgroundWhite.active.backgroundGray2
        case _: Color.Black => if (is) bgc.gray8 else bgc.gray7.hover.backgroundGray6.active.backgroundGray8
        case _: Color.Blue  => if (is) bgc.primary5 else bgc.primary4.hover.backgroundPrimary3.active.backgroundPrimary5
        case _: Color.Red   => if (is) bgc.danger5 else bgc.danger4.hover.backgroundDanger3.active.backgroundDanger5
        case _: Color.Green => if (is) bgc.success5 else bgc.success4.hover.backgroundSuccess3.active.backgroundSuccess5
      }
      // scalastyle:on cyclomatic.complexity
      private def border: TagMod = color match {
        case _: Color.White => bc.gray4
        case _: Color.Black => bc.gray8
        case _: Color.Blue  => bc.primary5
        case _: Color.Red   => bc.danger5
        case _: Color.Green => bc.success5
      }
      final def colorNormal: TagMod = TagMod(text, shadow, bg, border)
    }

    trait Ghost extends Button.Style with NonLink with Light with Border {
      final def container: TagMod = TagMod(borderContainer, nonLinkContainer)
      final def colorDisabled: TagMod = TagMod(borderDisabled, nonLinkDisabled)

      // scalastyle:off cyclomatic.complexity
      private def is: Boolean = isSelected || isBusy
      private def border: TagMod = color match {
        case _: Color.White => if (is) bc.gray5 else bc.gray7.active.borderGray5
        case _: Color.Black => if (is) bc.gray7 else bc.gray4.active.borderGray7
        case _: Color.Blue  => if (is) bc.primary5 else bc.primary4.active.borderPrimary5
        case _: Color.Red   => if (is) bc.danger5 else bc.danger4.active.borderDanger5
        case _: Color.Green => if (is) bc.success5 else bc.success4.active.borderSuccess5
      }
      // scalastyle:on cyclomatic.complexity
      final def colorNormal: TagMod = TagMod(lightNormal, border)
    }

    trait Minimal extends Button.Style with NonLink with Light {
      // We want the width of a button is the same whether it's Minimal or
      // Ghost so here we have a fake border
      final def container: TagMod = TagMod(Style.border.all, nonLinkContainer)
      private def colorBorder = Style.borderColor.transparent
      final def colorDisabled: TagMod = TagMod(colorBorder, nonLinkDisabled)
      final def colorNormal: TagMod = TagMod(colorBorder, lightNormal)
    }
  }
}
