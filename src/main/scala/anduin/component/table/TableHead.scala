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
    style: TableStyle,
    isSticky: Option[Table.Sticky],
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
      val icon = if (props.sortIsAsc) Icon.Glyph.CaretDown else Icon.Glyph.CaretUp
      (Style.color.primary4, icon)
    } else {
      // Not sorting by this column
      (Style.color.gray4, Icon.Glyph.CaretVertical)
    }
    val negativeMargin = ^.marginRight := "-4px" // visual touch
    <.span(negativeMargin, color, Icon(name = icon)())
  }

  private val titleStyles = TagMod(
    Style.fontSize.px11.fontWeight.semiBold.textAlign.left.color.gray6,
    Style.padding.ver8.padding.hor12.flexbox.flex.flexbox.itemsCenter.width.pc100
  )

  private def renderTitleSortable(props: Props, column: Table.Column[A], index: Int) = {
    <.button(
      Style.color.activePrimary5.outline.focusLight.transition.allWithOutline,
      TagMod.when(props.sortColumn.contains(index))(Style.color.gray8),
      titleStyles,
      ^.tpe := "button",
      ^.onClick --> props.sort(index),
      <.span(Style.flexbox.fill, column.head),
      renderSortIcon(index, props)
    )
  }

  private def renderTitle(props: Props, column: Table.Column[A], index: Int) = {
    column.sortBy match {
      case _: Table.ColumnOrderingHasOrder[A] => renderTitleSortable(props, column, index)
      case _: Table.ColumnOrderingEmpty[A]    => <.div(titleStyles, column.head)
    }
  }

  private def renderHead(props: Props)(colWithIndex: (Table.Column[A], Int)) = {
    val (column, index) = colWithIndex
    <.th(
      ^.key := index,
      props.isSticky.fold(TagMod.empty) { sticky =>
        TagMod(Style.position.sticky.zIndex.idx1, ^.top := s"${sticky.offset}px")
      },
      Style.padding.all0, // because of current CSS
      TagMod.when(column.width.nonEmpty)(^.width := column.width),
      props.style.th,
      TagMod.when(index != 0)(props.style.thNotFirst),
      renderTitle(props, column, index)
    )
  }

  // TableHead's anatomy:
  // - <.thead (render)
  //   - <.tr
  //     - <.th (renderHead) <~ btw, sticky happens here
  //       - <.button (renderTitle)
  private def render(props: Props): VdomElement = {
    val columns = props.columns.zipWithIndex.toVdomArray(renderHead(props))
    <.thead(
      ComponentUtils.testId(Table, "Head"),
      Style.whiteSpace.noWrap,
      <.tr(columns)
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
