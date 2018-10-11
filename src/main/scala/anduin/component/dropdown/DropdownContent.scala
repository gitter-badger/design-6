// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.dropdown

import org.scalajs.dom.document.documentElement

import anduin.scalajs.reactvirtualized.{ReactVirtualizedAutoSizer, ReactVirtualizedList}
import anduin.scalajs.util.Util
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[dropdown] class DropdownContent[A] {

  private val OptionRender = (new DropdownOption[A])()
  private val Filter = new DropdownFilter[A]

  private type Props = DropdownInnerProps[A]

  private def renderOption(props: Props)(
    rvProps: ReactVirtualizedList.RowRenderProps
  ): raw.React.Node = {
    val option = props.outer.options(rvProps.index)
    val node = OptionRender(props.outer, props.downshift, option, rvProps.index)()
    <.div(^.key := rvProps.key, ^.style := rvProps.style, node).rawNode
  }

  private def renderOptionsWithSize(props: Props)(
    asProps: ReactVirtualizedAutoSizer.RenderProps
  ): raw.React.Node = {
    val optionCount = props.outer.options.length
    val optionHeight = props.measurement.optionHeight.getOrElse(0)
    val height = Math.min(
      Math.ceil(documentElement.clientHeight * 0.4).toInt,
      optionCount * optionHeight
    )
    val rvProps = new ReactVirtualizedList.Props(
      height = height,
      rowCount = optionCount,
      rowHeight = optionHeight,
      rowRenderer = renderOption(props),
      width = asProps.width
    )
    ReactVirtualizedList.component(rvProps).rawNode
  }

  private def renderOptions(raw: Props): VdomElement = {
    // Filter - From now on we only care the filtered props
    val options = raw.outer.options.filter(DropdownFilter.byValue(raw))
    val props = raw.copy(outer = raw.outer.copy(options = options))
    // Render
    val asProps = new ReactVirtualizedAutoSizer.Props(
      disableHeight = true,
      children = renderOptionsWithSize(props)
    )
    ReactVirtualizedAutoSizer.component(asProps)
  }

  private def renderGhostOption(props: Props): Option[VdomElement] = {
    props.measurement.biggestWidthOption.map(option => {
      <.div(
        // The right padding is necessary in case there is scrollbar
        TagMod(Style.overflow.hidden.padding.right24, ^.height := "0"),
        DropdownOption.renderPlain(props.outer)(option)
      )
    })
  }

  private def render(props: Props): VdomElement = {
    <.div(
      Util.getModsFromProps(props.downshift.getMenuProps()),
      props.outer.header,
      <.div(
        Style.padding.ver8,
        Filter.component(props),
        renderGhostOption(props),
        renderOptions(props)
      ),
      props.outer.footer
    )
  }

  val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
