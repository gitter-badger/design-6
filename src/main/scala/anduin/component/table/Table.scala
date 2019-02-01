// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.table

import anduin.component.util.ComponentUtils
import anduin.style.{Style => SStyle}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object Table {
  abstract class Align(private[table] val styles: TagMod)
  case object AlignInherit extends Align(SStyle.verticalAlign.inherit)
  case object AlignTop extends Align(SStyle.verticalAlign.top)
  case object AlignMiddle extends Align(SStyle.verticalAlign.middle)
  case object AlignBottom extends Align(SStyle.verticalAlign.bottom)

  case class Cell(
    content: VdomNode = EmptyVdom,
    rowSpan: Int = 1,
    colSpan: Int = 1,
    isNone: Boolean = false,
    align: Align = AlignInherit
  )

  case class Column[A](
    head: VdomNode = "",
    render: A => Cell,
    sortBy: ColumnOrdering[A] = ColumnOrderingEmpty[A](),
    width: String = ""
  )

  sealed trait ColumnOrdering[-A]
  sealed trait ColumnOrderingHasOrder[A] extends ColumnOrdering[A] with Ordering[A]
  case class ColumnOrderingEmpty[A]() extends ColumnOrdering[A]
  object ColumnOrdering {
    def apply[A, B](f: A => B)(
      implicit ordering: Ordering[B]
    ): ColumnOrdering[A] = new ColumnOrderingHasOrder[A] {
      def compare(x: A, y: A): Int = ordering.on(f).compare(x, y)
    }
  }

  object Style {
    object Full extends TableStyle.Full
    object Minimal extends TableStyle.Minimal
  }

  case class Sticky(offset: Int = 0)
}

class Table[A] {
  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps { p =>
      State(sortColumn = p.sortColumn, sortIsAsc = p.sortIsAsc)
    }
    .renderBackend[Backend]
    .build

  private val Head = (new TableHead[A])()
  private val Body = (new TableBody[A])()

  case class Props(
    // basic
    columns: Seq[Table.Column[A]],
    rows: Seq[A],
    getKey: A => String,
    // ===
    style: TableStyle = Table.Style.Full,
    // head
    sortColumn: Option[Int] = None,
    sortIsAsc: Boolean = true,
    headIsSticky: Option[Table.Sticky] = None,
    // body
    renderRow: TableBody.RenderRow[A] = TableBody.defaultRenderRow[A],
    align: Table.Align = Table.AlignMiddle,
    footer: VdomNode = EmptyVdom
  ) {
    def apply(): VdomElement = component(this)
  }

  def apply(): Props.type = Props

  private case class State(sortColumn: Option[Int], sortIsAsc: Boolean)

  private class Backend(scope: BackendScope[Props, State]) {

    private def sort(index: Int): Callback = {
      scope.modState(state => {
        if (state.sortColumn == Option(index)) {
          // if current column already --> toggle sort direction
          state.copy(sortIsAsc = !state.sortIsAsc)
        } else {
          // else --> set sort column and use default sortIsAsc
          State(sortColumn = Option(index), sortIsAsc = true)
        }
      })
    }

    def render(props: Props, state: State): VdomElement = {

      val styles = TagMod(
        SStyle.width.pc100.background.white,
        ^.cellSpacing := "0",
        TagMod.when(props.columns.count(_.width.isEmpty) < 2)(SStyle.table.fixed),
        props.style.table
      )

      val head = Head(
        columns = props.columns,
        style = props.style,
        isSticky = props.headIsSticky,
        // ==
        sortColumn = state.sortColumn,
        sortIsAsc = state.sortIsAsc,
        sort = sort
      )()

      val body = Body(
        rows = props.rows,
        renderRow = props.renderRow,
        align = props.align,
        getKey = props.getKey,
        footer = props.footer,
        // ==
        columns = props.columns,
        style = props.style,
        // ==
        sortColumn = state.sortColumn,
        sortIsAsc = state.sortIsAsc
      )()

      val id = ComponentUtils.testId(Table, "Container")
      <.table(id, styles, head, body)
    }

  }
}
