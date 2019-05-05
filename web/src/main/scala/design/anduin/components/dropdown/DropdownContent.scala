// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.dropdown

import design.anduin.components.util.ComponentUtils
import design.anduin.facades.downshift.DownshiftRenderProps
import design.anduin.facades.reactvirtualized.{ReactVirtualizedAutoSizer, ReactVirtualizedList}
import design.anduin.facades.util.ScalaJSUtils
import design.anduin.style.Style
import org.scalajs.dom.document.documentElement

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[dropdown] class DropdownContent[A] {

  def apply(): Props.type = Props

  case class Props(
    dropdown: Dropdown[A]#Props,
    downshift: DownshiftRenderProps[A],
    measurement: Dropdown.Measurement[A]
  ) {
    def apply(): VdomElement = component(this)
  }

  private val Opt = (new DropdownOpt[A])()
  private val Filter = (new DropdownFilter[A])()

  private def renderRVOption(props: Props)(
    rvProps: ReactVirtualizedList.RowRenderProps
  ): raw.React.Node = {
    <.div(
      ^.key := rvProps.key,
      ^.style := rvProps.style,
      Opt(
        dropdown = props.dropdown,
        downshift = Some(props.downshift),
        option = props.dropdown.options(rvProps.index),
        index = rvProps.index
      )()
    ).rawNode
  }

  private def renderRVOptions(props: Props)(
    asProps: ReactVirtualizedAutoSizer.RenderProps
  ): raw.React.Node = {
    val optionCount = props.dropdown.options.length
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
    val nodes = props.dropdown.options.zipWithIndex.toVdomArray { tuple =>
      val (option, index) = tuple
      <.div(
        ^.key := props.dropdown.getValueString(option.value),
        Opt(option, index, props.dropdown, Some(props.downshift))()
      )
    }
    <.div(Style.overflow.autoY, ^.maxHeight := "40vh", nodes)
  }

  private def renderOptions(originalProps: Props): VdomElement = {
    // Filter - From now on we only care the filtered props
    val options = originalProps.dropdown.options.filter(
      DropdownFilter.byValue(originalProps.dropdown, originalProps.downshift)
    )
    val props = originalProps.copy(
      dropdown = originalProps.dropdown.copy(options = options)
    )
    // Render
    // We don't need ReactVirtualized for short list. Also, ReactVirtualized
    // won't work when options's heights are not the same so it's better to
    // avoid if not worth it
    if (props.dropdown.options.length < 20) {
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
        Opt(option, 0, props.dropdown)()
      )
    })
  }

  private val boxStyles = TagMod(
    Style.background.gray1.padding.ver8.padding.hor12,
    Style.borderWidth.px1.borderColor.gray3
  )

  private def render(props: Props): VdomElement = {
    <.div(
      ComponentUtils.testId(this, "Container"),
      ScalaJSUtils.jsPropsToTagMod(props.downshift.getMenuProps()),
      props.dropdown.header.map(<.div(Style.border.bottom, boxStyles, _)),
      TagMod.when(props.dropdown.options.nonEmpty) {
        <.div(
          Style.padding.ver8,
          Filter(props.dropdown, props.downshift)(),
          renderGhostOption(props),
          renderOptions(props)
        )
      },
      props.dropdown.footer.map(<.div(Style.border.top, boxStyles, _))
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
