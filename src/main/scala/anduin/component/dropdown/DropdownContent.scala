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

  def apply(): OuterProps.type = OuterProps

  case class OuterProps(props: Props) {
    def apply(): VdomElement = component(this)
  }

  private type Props = Dropdown[A]#InnerProps

  private val OptionRender = (new DropdownOption[A])()
  private val Filter = (new DropdownFilter[A])()

  private type RvProps = ReactVirtualizedList.RowRenderProps
  private def renderRVOption(props: Props)(rvProps: RvProps): raw.React.Node = {
    val option = props.outer.options(rvProps.index)
    val downshift = Some(props.downshift)
    val node = OptionRender(props.outer, downshift, option, rvProps.index)()
    <.div(^.key := rvProps.key, ^.style := rvProps.style, node).rawNode
  }

  private type AsProps = ReactVirtualizedAutoSizer.RenderProps
  private def renderRVOptions(props: Props)(asProps: AsProps): raw.React.Node = {
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
      rowRenderer = renderRVOption(props),
      width = asProps.width
    )
    ReactVirtualizedList.component(rvProps).rawNode
  }

  // Figure a way for ReactVirtualized to work with dynamic height FAST
  // and we can get rid of this ugly fallback
  private def renderRawOptions(props: Props): VdomElement = {
    val nodes = props.outer.options.zipWithIndex.toVdomArray { tuple =>
      val (option, index) = tuple
      val key = ^.key := props.outer.getValueString(option.value)
      val downshift = Some(props.downshift)
      val node = OptionRender(props.outer, downshift, option, index)()
      <.div(key, node)
    }
    <.div(Style.overflow.autoY, ^.maxHeight := "40vh", nodes)
  }

  private def renderOptions(raw: Props): VdomElement = {
    // Filter - From now on we only care the filtered props
    val options = raw.outer.options.filter(DropdownFilter.byValue(raw))
    val props = raw.copy(outer = raw.outer.copy(options = options))
    // Render
    // We don't need ReactVirtualized for short list. Also, ReactVirtualized
    // won't work when options's heights are not the same so it's better to
    // avoid if not worth it
    if (props.outer.options.length < 20) {
      renderRawOptions(props)
    } else {
      val asProps = new ReactVirtualizedAutoSizer.Props(
        disableHeight = true,
        children = renderRVOptions(props)
      )
      ReactVirtualizedAutoSizer.component(asProps)
    }
  }

  private def renderGhostOption(props: Props): Option[VdomElement] = {
    props.measurement.biggestWidthOption.map(option => {
      <.div(
        // The right padding is necessary in case there is scrollbar
        TagMod(Style.overflow.hidden.padding.right24, ^.height := "0"),
        OptionRender(props.outer, None, option, 0)()
      )
    })
  }

  private val boxStyles = TagMod(
    Style.backgroundColor.gray1.padding.ver8.padding.hor12,
    Style.borderWidth.px1.borderColor.gray3
  )

  private def render(outerProps: OuterProps): VdomElement = {
    val props = outerProps.props
    <.div(
      Util.getModsFromProps(props.downshift.getMenuProps()),
      props.outer.header.map(<.div(Style.border.bottom, boxStyles, _)),
      <.div(
        Style.padding.ver8,
        Filter(props)(),
        renderGhostOption(props),
        renderOptions(props)
      ),
      props.outer.footer.map(<.div(Style.border.top, boxStyles, _))
    )
  }

  private val component = ScalaComponent
    .builder[OuterProps](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
