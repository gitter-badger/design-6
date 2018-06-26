// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[component] class TableBody[A] {

  case class Props(
    rows: List[A],
    align: Table.Align,
    getKey: A => String,
    footer: VdomNode,
    // ==
    columns: List[Table.Column[A]],
    style: Table.Style,
    // ==
    sortColumn: Option[Int],
    sortIsAsc: Boolean
  ) {
    def apply(): VdomElement = component(this)
  }

  def apply(): Props.type = Props

  private def getSortedRows(props: Props): List[A] = {
    props.sortColumn.fold(props.rows) { columnIndex =>
      val column = props.columns(columnIndex)
      // This is bad, super bad. See the note in sortBy definition
      val sorted = (column.sortByString, column.sortByDouble) match {
        case (Some(sortBy), _) => props.rows.sortBy(sortBy)
        case (_, Some(sortBy)) => props.rows.sortBy(sortBy)
        case _                 => props.rows
      }
      if (props.sortIsAsc) sorted else sorted.reverse
    }
  }

  private def render(props: Props): VdomElement = {
    val rows = getSortedRows(props).toVdomArray(row => {
      val columns = props.columns.zipWithIndex.toVdomArray(columnTuple => {
        val (column, index) = columnTuple
        val cell = column.render(row)
        if (cell.isNone) { EmptyVdom } else {
          <.td(
            Style.padding.all12,
            cell.align.styles,
            ^.key := index,
            ^.rowSpan := cell.rowSpan,
            ^.colSpan := cell.colSpan,
            cell.content
          )
        }
      })
      val rowKey = ^.key := props.getKey(row)
      <.tr(rowKey, Style.hover.backgroundGray1, props.style.tr, columns)
    })
    val footer = TagMod.when(props.footer != EmptyVdom) {
      val span = ^.colSpan := props.columns.size
      val cell = <.td(Style.padding.all12, props.footer, span)
      <.tr(props.style.tr, cell)
    }
    <.tbody(props.align.styles, rows, footer)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
