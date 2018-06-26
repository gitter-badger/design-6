// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

import anduin.component.icon.IconAcl
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

  private def renderCaret(isDown: Boolean, isSelected: Boolean): VdomNode = {
    val pos = Style.position.absolute.coordinate.left0
    val shift = if (isDown) ^.bottom else ^.top
    val icon = if (isDown) IconAcl.NameCaretDown else IconAcl.NameCaretUp
    <.div(
      TagMod(pos, shift := "-3px"),
      Style.color.primary4.when(isSelected),
      IconAcl(name = icon)()
    )
  }

  private def renderCaretGroup(index: Int, props: Props): VdomNode = {
    val isSortBy = props.sortColumn == Option(index)
    <.div(
      Style.width.px16.height.px16.position.relative,
      renderCaret(isDown = true, isSortBy && props.sortIsAsc),
      renderCaret(isDown = false, isSortBy && !props.sortIsAsc)
    )
  }

  private val textStyles = TagMod(
    Style.fontSize.px12.fontWeight.medium.textTransform.uppercase.textAlign.left,
    Style.padding.ver8.padding.hor12.color.gray7
  )

  private val buttonStyles = TagMod(
    textStyles,
    Style.flexbox.flex.flexbox.itemsCenter.width.pc100,
    Style.active.colorPrimary5.focus.outline.transition.allWithOutline
  )

  private def renderColumn(props: Props)(colWithIndex: (Table.Column[A], Int)) = {
    val (column, index) = colWithIndex
    // Note: This is bad, super bad. See the note at the definition of sortByInt
    val sortable = column.sortByDouble.isDefined ||
      column.sortByString.isDefined
    val button = if (sortable) {
      <.button(
        buttonStyles,
        ^.tpe := "button",
        ^.onClick --> props.sort(index),
        <.span(column.head),
        <.span(Style.margin.left4, renderCaretGroup(index, props))
      )
    } else {
      <.div(textStyles, column.head)
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
    <.thead(props.style.thead, <.tr(props.style.tr, columns))
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
