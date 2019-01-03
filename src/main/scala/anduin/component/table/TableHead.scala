// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.table

import anduin.component.icon.Icon
import anduin.component.util.ComponentUtils
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
    val negativeMargin = ^.marginRight := "-4px" // visual touch
    <.span(negativeMargin, color, Icon(name = icon)())
  }

  private val titleStyles = TagMod(
    Style.fontSize.px12.fontWeight.semiBold.textAlign.left.color.gray6,
    Style.padding.ver8.padding.hor12.flexbox.flex.flexbox.itemsCenter.width.pc100,
    Style.active.colorPrimary5.focus.outline.transition.allWithOutline
  )

  private def renderTitle(props: Props, column: Table.Column[A], index: Int) = {
    val sortable = column.sortBy match {
      case _: Table.ColumnOrderingHasOrder[A] => true
      case _: Table.ColumnOrderingEmpty[A]    => false
    }
    <.button(
      ^.tpe := "button",
      titleStyles,
      ^.onClick --> props.sort(index),
      ^.disabled := !sortable,
      <.span(
        Style.flexbox.fixed,
        TagMod.when(props.sortColumn.contains(index))(Style.color.gray8),
        column.head
      ),
      TagMod.when(sortable)(renderSortIcon(index, props))
    )
  }

  private def renderHead(props: Props)(colWithIndex: (Table.Column[A], Int)) = {
    val (column, index) = colWithIndex
    <.th(
      ^.key := index,
      Style.padding.all0, // because of current CSS
      TagMod.unless(column.width.isEmpty)(^.width := column.width),
      props.style.th,
      renderTitle(props, column, index)
    )
  }

  // TableHead's anatomy:
  // - <.thead (render)
  //   - <.tr
  //     - <.th (renderHead)
  //       - <.button (renderTitle)
  private def render(props: Props): VdomElement = {
    val columns = props.columns.zipWithIndex.toVdomArray(renderHead(props))
    <.thead(
      ComponentUtils.testId(Table, "Head"),
      Style.whiteSpace.noWrap,
      <.tr(props.style.tr, columns)
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
