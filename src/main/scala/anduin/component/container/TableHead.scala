// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

import anduin.component.icon.Icon
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[component] class TableHead[A] {

  case class Props(
    columns: List[Table.Column[A]],
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
    val isSortingColumn = props.sortColumn.contains(index)
    val icon = if (props.sortIsAsc) Icon.NameCaretDown else Icon.NameCaretUp
    val spacing = Style.margin.left4

    if (isSortingColumn) {
      <.span(spacing, Style.color.primary4, Icon(name = icon)())
    } else {
      <.span(spacing, Style.color.gray4, Icon(name = Icon.NameCaretVertical)())
    }
  }

  private val titleStyles = TagMod(
    Style.fontSize.px12.fontWeight.semiBold,
    Style.textAlign.left.color.gray6
  )

  private val buttonStyles = TagMod(
    titleStyles,
    Style.flexbox.flex.flexbox.itemsCenter.width.pc100,
    Style.active.colorPrimary5.focus.outline.transition.allWithOutline
  )

  private def renderColumn(props: Props)(
    colWithIndex: (Table.Column[A], Int)
  ) = {
    val (column, index) = colWithIndex
    val isSortBy = props.sortColumn == Option(index)
    // Note: This is bad, super bad. See the note at the definition of sortByInt
    val sortable = column.sortByDouble.isDefined || column.sortByString.isDefined
    val button = if (sortable) {
      <.button(
        buttonStyles,
        Style.padding.ver8.padding.right8.padding.left12,
        ^.tpe := "button",
        ^.onClick --> props.sort(index),
        <.div(
          Style.flexbox.fixed,
          TagMod.when(isSortBy) { Style.color.gray8 },
          column.head
        ),
        renderSortIcon(index, props)
      )
    } else {
      <.div(
        titleStyles,
        Style.padding.ver8.padding.hor12,
        column.head
      )
    }
    <.th(
      Style.padding.all0, // because of current CSS
      ^.key := index,
      TagMod.unless(column.width.isEmpty) { ^.width := column.width },
      props.style.th,
      button
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
