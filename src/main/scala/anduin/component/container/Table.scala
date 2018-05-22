// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

import anduin.component.button.{Button, ButtonStyle}
import anduin.component.icon.IconAcl
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

class Table[R] {
  private val component = ScalaComponent
    .builder[Table.Props[R]](this.getClass.getSimpleName)
    .initialStateFromProps(p => Table.State(sortColumn = p.sortColumn, sortIsAsc = p.sortIsAsc))
    .renderBackend[Table.Backend[R]]
    .build

  def apply(
    rows: List[R],
    columns: List[Table.Column[R]],
    sortColumn: Option[Int] = None,
    sortIsAsc: Boolean = true,
    headIsSticky: Boolean = false,
    footer: VdomNode = EmptyVdom
  )(): VdomElement = {
    val props = Table.Props(rows, columns, sortColumn, sortIsAsc, headIsSticky, footer)
    component(props)
  }
}

object Table {

  case class Cell(
    content: VdomNode = EmptyVdom,
    rowSpan: Int = 1,
    colSpan: Int = 1,
    isEmpty: Boolean = false,
    align: CellAlign = CellAlignMiddle
  )
  sealed trait CellAlign { val styles: Style }
  case object CellAlignTop extends CellAlign { val styles: Style = Style.verticalAlign.top }
  case object CellAlignMiddle extends CellAlign { val styles: Style = Style.verticalAlign.middle }
  case object CellAlignBottom extends CellAlign { val styles: Style = Style.verticalAlign.bottom }

  case class Sort[R](
    asc: (R, R) => Boolean,
    desc: (R, R) => Boolean
  )
  case class Column[R](
    head: VdomNode = "",
    render: R => Cell,
    sort: Option[Sort[R]] = None,
    width: String = ""
  )
  private case class Props[R](
    rows: List[R],
    columns: List[Table.Column[R]],
    sortColumn: Option[Int],
    sortIsAsc: Boolean,
    headIsSticky: Boolean,
    footer: VdomNode
  )

  private case class State(
    sortColumn: Option[Int],
    sortIsAsc: Boolean
  )

  private val border = Style.border.all.borderWidth.px1.borderColor.gray3

  private class Backend[R](scope: BackendScope[Props[R], State]) {

    private def renderSortButton(iconName: IconAcl.Name, isSelected: Boolean, onClick: Callback): VdomElement = {
      <.span(
        Style.margin.left4,
        Button(
          style = ButtonStyle.StyleMinimal,
          size = ButtonStyle.SizeIcon,
          isSelected = isSelected,
          onClick = onClick
        )(IconAcl(name = iconName)())
      )
    }

    private def renderSortButtons(column: Column[R], index: Int, state: State): VdomNode = {
      if (column.sort.isEmpty) {
        // keep the height
        <.span(^.visibility := "hidden", Button(size = ButtonStyle.SizeIcon)("i"))
      } else {
        val isSortBy = state.sortColumn == Option(index)
        val downButton = renderSortButton(
          iconName = IconAcl.NameCaretDown,
          isSelected = isSortBy && state.sortIsAsc,
          onClick = scope.setState(State(sortColumn = Option(index), sortIsAsc = true))
        )
        val upButton = renderSortButton(
          iconName = IconAcl.NameCaretUp,
          isSelected = isSortBy && !state.sortIsAsc,
          onClick = scope.setState(State(sortColumn = Option(index), sortIsAsc = false))
        )
        ReactFragment(downButton, upButton)
      }
    }

    private def renderHead(props: Props[R], state: State): VdomElement = {
      val columns = props.columns.zipWithIndex.toTagMod {
        case (column, index) =>
          <.th(
            Style.fontSize.px12.fontWeight.medium.textTransform.uppercase,
            Style.padding.right4.padding.left12.padding.ver4.textAlign.left.color.gray7,
            border,
            TagMod.when(!column.width.isEmpty) { ^.width := column.width },
            <.span(
              Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyBetween,
              <.span(Style.flexbox.fixed, column.head),
              renderSortButtons(column, index, state)
            )
          )
      }
      <.thead(Style.backgroundColor.gray1, <.tr(border, columns))
    }

    private def renderBody(props: Props[R], state: State): VdomElement = {
      // sort if required
      val rows: List[R] = state.sortColumn.fold(props.rows)(columnIndex => {
        // if sort column does not have "sort" then just leave as is
        props
          .columns(columnIndex)
          .sort
          .fold(props.rows)(sort => {
            val compareFn = if (state.sortIsAsc) sort.asc else sort.desc
            props.rows.sortWith(compareFn)
          })
      })

      <.tbody(
        rows.toTagMod(row => {
          val columns = props.columns.toTagMod(column => {
            val cell = column.render(row)
            if (cell.isEmpty) { EmptyVdom } else {
              val span = TagMod(^.rowSpan := cell.rowSpan, ^.colSpan := cell.colSpan)
              <.td(Style.padding.all12, cell.align.styles, span, cell.content)
            }
          })
          <.tr(Style.hover.backgroundGray1, border, columns)
        }),
        TagMod.when(props.footer != EmptyVdom) {
          val cell = <.td(Style.padding.all12, props.footer, ^.colSpan := props.columns.size)
          <.tr(border, cell)
        }
      )
    }

    def render(props: Props[R], state: State): VdomElement = {
      val styles = TagMod(Style.width.pc100.backgroundColor.white, ^.borderCollapse := "collapse")
      val head = renderHead(props, state)
      val body = renderBody(props, state)

      if (!props.headIsSticky) {
        <.table(styles, head, body)
      } else {
        // To simplify implementation, sticky head requires all columns to define width
        if (props.columns.exists(_.width.isEmpty)) {
          throw new RuntimeException("Sticky head requires all columns to have width defined")
        }

        // Note that both the body and head of Sticky Table needs permanent scroll for proper alignment
        val stickyBody = <.div(
          Style.overflow.scrollY.height.pc100.overflow.scrollY,
          <.table(^.tableLayout := "fixed", styles, head, body)
        )
        val stickyHead = <.div(
          Style.overflow.scrollY.position.absolute.coordinate.top0.coordinate.left0.width.pc100,
          <.table(^.tableLayout := "fixed", styles, head)
        )
        <.div(Style.position.relative.height.pc100, stickyBody, stickyHead)
      }
    }

  }
}
