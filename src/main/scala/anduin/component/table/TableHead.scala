// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.table

import anduin.component.icon.Icon
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[table] class TableHead[A] {

  case class Props(
    columns: Seq[Table.Column[A]],
    style: Table.Style,
    // ==
    sort: Int => Callback,
    sortColumn: Option[Int],
    sortIsAsc: Boolean
  ) {
    def apply(): VdomElement = component(this)
  }

  def apply(): Props.type = Props

  private def renderSortIcon(index: Int, props: Props): VdomNode = {
    val (color, icon) = if (props.sortColumn.contains(index)) {
      // Sorting by this column
      val icon = if (props.sortIsAsc) Icon.NameCaretDown else Icon.NameCaretUp
      (Style.color.primary4, icon)
    } else {
      // Not sorting by this column
      (Style.color.gray4, Icon.NameCaretVertical)
    }
    val negativeMargin = ^.marginRight := "-4px"// visual touch
    <.span(negativeMargin, color, Icon(name = icon)())
  }

  private val titleCommonStyles = TagMod(
    Style.fontSize.px12.fontWeight.semiBold,
    Style.textAlign.left.color.gray6,
    Style.padding.ver8.padding.hor12
  )

  private val titleButtonStyles = TagMod(
    titleCommonStyles,
    Style.flexbox.flex.flexbox.itemsCenter.width.pc100,
    Style.active.colorPrimary5.focus.outline.transition.allWithOutline
  )

  private def renderTitleButton(
    props: Props,
    column: Table.Column[A],
    index: Int
  ) = {
    <.button(
      titleButtonStyles,
      ^.tpe := "button",
      ^.onClick --> props.sort(index),
      <.span(
        Style.flexbox.fixed,
        TagMod.when(props.sortColumn.contains(index)) {
          Style.color.gray8
        },
        column.head
      ),
      renderSortIcon(index, props)
    )
  }

  private def renderColumn(props: Props)(
    colWithIndex: (Table.Column[A], Int)
  ) = {
    val (column, index) = colWithIndex
    // Note: This is bad, super bad. See the note at the definition of
    // sortByInt
    val sortable = column.sortBy match {
        case _: Table.ColumnOrderingHasOrder[A] => true
        case _: Table.ColumnOrderingEmpty[A] => false
    }
    val content = if (sortable) {
      renderTitleButton(props, column, index)
    } else {
      <.div(titleCommonStyles, column.head)
    }
    <.th(
      Style.padding.all0, // because of current CSS
      ^.key := index,
      TagMod.unless(column.width.isEmpty) { ^.width := column.width },
      props.style.th,
      content
    )
  }

  private def render(props: Props): VdomElement = {
    val columns = props.columns.zipWithIndex.toVdomArray(renderColumn(props))
    <.thead(
      Style.whiteSpace.noWrap,
      props.style.thead,
      <.tr(props.style.tr, columns)
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
