// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.table
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

sealed trait TableStyle {
  def table: TagMod = TagMod.empty
  def td: TagMod = TagMod.empty
  def tdNotFirst: TagMod = TagMod.empty
  def th: TagMod = TagMod.empty
  def thNotFirst: TagMod = TagMod.empty
}

object TableStyle {

  private val border: TagMod = Style.borderWidth.px1.borderColor.gray3

  trait Full extends TableStyle {
    override val table: TagMod = TagMod(border, Style.border.top.border.left.border.right)
    override val td: TagMod = TagMod(border, Style.border.bottom)
    override val th: TagMod = TagMod(border, Style.border.bottom.backgroundColor.gray1)
    override val thNotFirst: TagMod = TagMod(Style.border.left)
  }

  trait Minimal extends TableStyle {
    override val td: TagMod = TagMod(border, Style.border.bottom)
    override val th: TagMod = TagMod(border, Style.border.bottom)
  }
}
