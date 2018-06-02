// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[component] class TableBody[T] {

  case class Props(
    rows: List[T],
    getKey: T => String,
    footer: VdomNode,
    // ==
    columns: List[Table.Column[T]],
    style: Table.Style,
    // ==
    sortColumn: Option[Int],
    sortIsAsc: Boolean
  ) {
    def apply(): VdomElement = component(this)
  }

  def apply(): Props.type = Props

  private def getSortedRows(props: Props): List[T] = {
    props.sortColumn.fold(props.rows)(columnIndex => {
      // if sort column does not have "sort" then just leave as is
      props
        .columns(columnIndex)
        .sortBy
        .fold(props.rows)(sortBy => {
          val sorted = props.rows.sortBy(sortBy)
          if (props.sortIsAsc) sorted else sorted.reverse
        })
    })
  }

  private def render(props: Props): VdomElement = {
    val rows = getSortedRows(props).toVdomArray(row => {
      val columns = props.columns.zipWithIndex.toVdomArray(columnTuple => {
        val (column, index) = columnTuple
        val cell = column.render(row)
        if (cell.isEmpty) { EmptyVdom } else {
          val span = TagMod(^.rowSpan := cell.rowSpan, ^.colSpan := cell.colSpan)
          val colKey = ^.key := index
          <.td(Style.padding.all12, cell.align.styles, colKey, span, cell.content)
        }
      })
      val rowKey = ^.key := props.getKey(row)
      <.tr(rowKey, Style.hover.backgroundGray1, props.style.tr, columns)
    })
    val footer = TagMod.when(props.footer != EmptyVdom) {
      val cell = <.td(Style.padding.all12, props.footer, ^.colSpan := props.columns.size)
      <.tr(props.style.tr, cell)
    }
    <.tbody(rows, footer)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
