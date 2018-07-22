// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

import org.scalajs.dom.html

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[component] class TableBody[A] {

  case class Props(
    rows: List[A],
    // key, cells, row
    renderRow: TableBody.RenderRow[A],
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

  private def renderCell(row: A)(
    columnTuple: (Table.Column[A], Int)
  ): VdomNode = {
    val (column, index) = columnTuple
    val cell = column.render(row)
    if (cell.isNone) {
      EmptyVdom
    } else {
      <.td(
        Style.padding.all12,
        cell.align.styles,
        ^.key := index,
        ^.rowSpan := cell.rowSpan,
        ^.colSpan := cell.colSpan,
        cell.content
      )
    }
  }

  private def render(props: Props): VdomElement = {
    val rows = getSortedRows(props).toVdomArray(row => {
      val cells = props.columns.zipWithIndex.toVdomArray(renderCell(row))
      val key = props.getKey(row)
      props.renderRow(props.style.tr, key, cells, row)
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

private[component] object TableBody {
  type Tr = vdom.TagOf[html.TableRow]
  type RenderRow[A] = (TagMod, String, VdomArray, A) => Tr
  def defaultRenderRow[A](style: TagMod, key: String, cells: VdomArray, row: A): Tr = {
    // the default render does not need the row data. However, we still want
    // to keep this method's signature
    val _ = row

    <.tr(^.key := key, Style.hover.backgroundGray1, style, cells)
  }
}
