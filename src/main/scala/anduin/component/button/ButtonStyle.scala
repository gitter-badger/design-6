// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.button

import anduin.component.icon.Icon
import anduin.component.progressindicators.CircleIndicator
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

sealed trait ButtonStyle {
  def container: TagMod
  def body: TagMod
  def overlay: TagMod
  // icon
  def iconInfo: Option[Icon]
  def iconColorNormal: TagMod
  def iconColorDisabled: TagMod
  // these are separated because isDisabled is
  // not a property of Style but Button
  def colorDisabled: TagMod
  def colorNormal: TagMod
  // these are separated because children is
  // not a property of Style but Button
  def sizeSquare: TagMod // Icon with no children
  def sizeRect: TagMod // Icon with children
}

object ButtonStyle {

  sealed trait Height {
    def square: TagMod
    def rect: TagMod
    def iconSize: Icon.Size
  }
  object Height {
    trait Fix24 extends Height {
      private def common = Style.height.px24.fontSize.px11
      final def square: TagMod = TagMod(common, ^.width := "24px")
      final def rect: TagMod = TagMod(common, Style.padding.hor8)
      final def iconSize: Icon.Size = Icon.Size.Custom(12)
    }
    trait Fix32 extends Height {
      private def common = Style.height.px32.fontSize.px13
      final def square: TagMod = TagMod(common, Style.width.px32)
      final def rect: TagMod = TagMod(common, Style.padding.hor12)
      final def iconSize: Icon.Size = Icon.Size.Px16
    }
    trait Fix40 extends Height {
      private def common = Style.height.px40.fontSize.px15
      final def square: TagMod = TagMod(common, ^.width := "40px")
      final def rect: TagMod = TagMod(common, Style.padding.hor16)
      final def iconSize: Icon.Size = Icon.Size.Custom(20)
    }
    trait Free extends Height {
      final def square: TagMod = TagMod.empty
      final def rect: TagMod = TagMod.empty
      final def iconSize: Icon.Size = Icon.Size.Px16
    }
  }

  sealed trait Color
  object Color {
    trait White extends Color
    trait Black extends Color
    trait Blue extends Color
    trait Green extends Color
    trait Red extends Color
  }

  // convenient shortcut
  private val bgc = Style.background

  // ============================
  // Common styles (for Public traits below)
  // ============================

  // "Box" buttons have traditional button-like appearance, like having
  // width, height and support icon. In other words, they are not "Link".
  sealed trait Box {
    def height: Height
    def icon: Option[Icon.Name]
    def isFullWidth: Boolean
    def isBusy: Boolean
  }
  object Box {

    // Icon
    def getIconInfo(box: Box): Option[Icon] = {
      box.icon.map(name => Icon(name = name, size = box.height.iconSize))
    }

    // Size
    def getSizeSquare(box: Box): TagMod = box.height.square
    def getSizeRect(box: Box): TagMod = TagMod(
      if (box.isFullWidth) Style.width.pc100 else Style.width.maxContent,
      box.height.rect
    )

    // Body, Container & Overlay
    def getContainer(box: Box): TagMod = TagMod(
      Style.lineHeight.px16.fontWeight.medium.whiteSpace.noWrap,
      Style.outline.focusLight.transition.allWithOutline.borderRadius.px2,
      Style.display.block.position.relative,
      TagMod.when(box.isBusy)(Style.pointerEvents.none),
      // To ensure Button tpe Link has same style with other types
      Style.textDecoration.hoverNone
    )
    def getBody(box: Box): TagMod = TagMod(
      Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter,
      // Necessary to use Parent's height. Without this the vertical
      // alignment will not work in case of "a" tag (e.g. Button tpe Link)
      Style.height.pc100,
      TagMod.when(box.isBusy)(Style.opacity.pc0)
    )
    def getOverlay(box: Box): TagMod = TagMod.when(box.isBusy) {
      <.span(
        Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter,
        Style.position.absolute.position.pinAll,
        CircleIndicator()()
      )
    }

    // Color
    val colorDisabled: TagMod = Style.color.gray4
    val iconColorDisabled: TagMod = Style.color.gray3
  }

  // "BoxNoBg" buttons don't have background defined at normal state. It
  // is for "Ghost" and "Minimal".
  sealed trait BoxNoBg {
    def isSelected: Boolean
    def isBusy: Boolean
    def color: Color
  }
  object BoxNoBg {

    // scalastyle:off cyclomatic.complexity
    private def getBgNormal(boxNoBg: BoxNoBg): TagMod = {
      // We see "isBusy" as a special state of "isSelected"
      val isSelected: Boolean = boxNoBg.isSelected || boxNoBg.isBusy
      boxNoBg.color match {
        case _: Color.White => if (isSelected) bgc.gray8 else Style.background.hoverGray7.background.activeGray8
        case _: Color.Black => if (isSelected) bgc.gray4 else Style.background.hoverGray3.background.activeGray4
        case _: Color.Blue  => if (isSelected) bgc.blue2 else Style.background.hoverBlue1.background.activeBlue2
        case _: Color.Red   => if (isSelected) bgc.red2 else Style.background.hoverRed1.background.activeRed2
        case _: Color.Green => if (isSelected) bgc.green2 else Style.background.hoverGreen1.background.activeGreen2
      }
    }
    // scalastyle:on cyclomatic.complexity

    private def getTextNormal(boxNoBg: BoxNoBg): TagMod = boxNoBg.color match {
      case _: Color.White => Style.color.gray4
      case _: Color.Black => Style.color.gray7
      case _: Color.Blue  => Style.color.blue5
      case _: Color.Green => Style.color.green5
      case _: Color.Red   => Style.color.red5
    }

    def getColorNormal(boxNoBg: BoxNoBg): TagMod = TagMod(getTextNormal(boxNoBg), getBgNormal(boxNoBg))

    def getIconColorNormal(boxNoBg: BoxNoBg): TagMod = boxNoBg.color match {
      case _: Color.White => Style.color.gray5
      case _: Color.Black => Style.color.gray6
      case _: Color.Blue  => Style.color.blue4
      case _: Color.Green => Style.color.green4
      case _: Color.Red   => Style.color.red4
    }
  }

  // "Border" buttons, well, have border. This is Full and Ghost
  // - It is pretty simple here actually, because the border color is different
  //   for each style
  object Border {
    val container: TagMod = Style.border.all
    val colorDisabled: TagMod = Style.borderColor.gray3
  }

  // ============================
  // Public traits (for Button.scala)
  // - These can actually be final case class for external use. However, we
  //   want to keep their implement detail here in this file so they are
  //   defined as traits and the final case class at "Button.scala" will
  //   extend them
  // ============================

  trait Text extends ButtonStyle {
    def color: Color
    def isBlock: Boolean

    final def container: TagMod = TagMod(Style.textDecoration.hoverUnderline, TagMod.when(isBlock)(Style.display.block))
    final def body: TagMod = TagMod.empty
    final def overlay: TagMod = TagMod.empty

    final def iconInfo: Option[Icon] = None
    final def iconColorNormal: TagMod = TagMod.empty
    final def iconColorDisabled: TagMod = TagMod.empty

    final def colorDisabled: TagMod = Style.color.gray5
    final def colorNormal: TagMod = Text.getColorNormal(this)

    final def sizeSquare: TagMod = TagMod.empty
    final def sizeRect: TagMod = TagMod.empty
  }
  object Text {
    def getColorNormal(text: Text): TagMod = text.color match {
      case _: Color.White => Style.color.white
      case _: Color.Black => Style.color.gray8
      case _: Color.Blue  => Style.color.blue4
      case _: Color.Green => Style.color.green4
      case _: Color.Red   => Style.color.red4
    }
  }

  trait Full extends ButtonStyle with Box {
    def color: Color
    def isSelected: Boolean

    final def container: TagMod = TagMod(Border.container, Box.getContainer(this))
    final def body: TagMod = Box.getBody(this)
    final def overlay: TagMod = Box.getOverlay(this)

    final def iconInfo: Option[Icon] = Box.getIconInfo(this)
    final def iconColorNormal: TagMod = Full.getIconColorNormal(this)
    final def iconColorDisabled: TagMod = Box.iconColorDisabled

    final def colorDisabled: TagMod = TagMod(Box.colorDisabled, Border.colorDisabled, Style.background.gray1)
    final def colorNormal: TagMod = Full.getColorNormal(this)

    final def sizeSquare: TagMod = Box.getSizeSquare(this)
    final def sizeRect: TagMod = Box.getSizeRect(this)
  }
  object Full {
    def getIconColorNormal(full: Full): TagMod = full.color match {
      case _: Color.White => Style.color.gray7
      case _              => Style.color.white
    }

    // scalastyle:off cyclomatic.complexity
    private def getBgNormal(full: Full): TagMod = {
      val isSelected: Boolean = full.isSelected || full.isBusy
      full.color match {
        case _: Color.White => if (isSelected) bgc.gray2 else bgc.gray1.background.hoverWhite.background.activeGray2
        case _: Color.Black => if (isSelected) bgc.gray8 else bgc.gray7.background.hoverGray6.background.activeGray8
        case _: Color.Blue  => if (isSelected) bgc.blue5 else bgc.blue4.background.hoverBlue3.background.activeBlue5
        case _: Color.Red   => if (isSelected) bgc.red5 else bgc.red4.background.hoverRed3.background.activeRed5
        case _: Color.Green => if (isSelected) bgc.green5 else bgc.green4.background.hoverGreen3.background.activeGreen5
      }
    }
    // scalastyle:on cyclomatic.complexity

    private def getBorderNormal(full: Full): TagMod = full.color match {
      case _: Color.White => Style.borderColor.gray4
      case _: Color.Black => Style.borderColor.gray8
      case _: Color.Blue  => Style.borderColor.blue5
      case _: Color.Red   => Style.borderColor.red5
      case _: Color.Green => Style.borderColor.green5
    }

    def getColorNormal(full: Full): TagMod = {
      val textAndShadow: TagMod = full.color match {
        case _: Color.White => Style.color.gray8.shadow.px1Light
        case _              => Style.color.white.shadow.px1Dark
      }
      TagMod(textAndShadow, getBgNormal(full), getBorderNormal(full))
    }
  }

  trait Ghost extends ButtonStyle with Box with BoxNoBg {
    final def container: TagMod = TagMod(Border.container, Box.getContainer(this))
    final def body: TagMod = Box.getBody(this)
    final def overlay: TagMod = Box.getOverlay(this)

    final def iconInfo: Option[Icon] = Box.getIconInfo(this)
    final def iconColorNormal: TagMod = BoxNoBg.getIconColorNormal(this)
    final def iconColorDisabled: TagMod = Box.iconColorDisabled

    final def colorNormal: TagMod = TagMod(BoxNoBg.getColorNormal(this), Ghost.getBorderNormal(this))
    final def colorDisabled: TagMod = TagMod(Border.colorDisabled, Box.colorDisabled)

    final def sizeSquare: TagMod = Box.getSizeSquare(this)
    final def sizeRect: TagMod = Box.getSizeRect(this)
  }
  object Ghost {
    def getBorderNormal(ghost: Ghost): TagMod = ghost.color match {
      case _: Color.White => Style.borderColor.gray7
      case _: Color.Black => Style.borderColor.gray4
      case _: Color.Blue  => Style.borderColor.blue3
      case _: Color.Red   => Style.borderColor.red3
      case _: Color.Green => Style.borderColor.green3
    }
  }

  trait Minimal extends ButtonStyle with Box with BoxNoBg {
    // We want the width of a button is the same whether it's Minimal or
    // Ghost so here we have a fake border. Note that we will NOT color this
    // border at all
    final def container: TagMod = TagMod(Border.container, Box.getContainer(this))
    final def body: TagMod = Box.getBody(this)
    final def overlay: TagMod = Box.getOverlay(this)

    final def iconInfo: Option[Icon] = Box.getIconInfo(this)
    final def iconColorNormal: TagMod = BoxNoBg.getIconColorNormal(this)
    final def iconColorDisabled: TagMod = Box.iconColorDisabled

    final def colorNormal: TagMod = TagMod(BoxNoBg.getColorNormal(this), Style.borderColor.transparent)
    final def colorDisabled: TagMod = TagMod(Box.colorDisabled, Style.borderColor.transparent)

    final def sizeSquare: TagMod = Box.getSizeSquare(this)
    final def sizeRect: TagMod = Box.getSizeRect(this)
  }

}
