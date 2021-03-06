// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.table

import design.anduin.components.util.ComponentUtils
import design.anduin.style.{Style => SStyle}
import org.scalajs.dom.html

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

  type Tr = vdom.TagOf[html.TableRow]
  type RenderRow[A] = (String, VdomArray, A) => Tr
  def defaultRenderRow[A](key: String, cells: VdomArray, row: A): Tr = {
    // the default render does not need the row data. However, we still want
    // to keep this method's signature
    val _ = row
    <.tr(^.key := key, SStyle.background.hoverGray1, cells)
  }
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
    renderRow: Table.RenderRow[A] = Table.defaultRenderRow[A],
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
        SStyle.width.pc100.background.gray0,
        // This is the best we can do now: @TODO: Should revise if have time
        TagMod(^.borderSpacing := "0px", ^.borderCollapse.separate),
        TagMod.when(props.columns.count(_.width.isEmpty) < 2)(^.tableLayout.fixed),
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
