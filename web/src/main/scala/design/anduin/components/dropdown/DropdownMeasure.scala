// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.dropdown

import design.anduin.style.Style
import org.scalajs.dom

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.ext._
// scalastyle:on underscore.import

private[dropdown] class DropdownMeasure[A] {

  private type Props = Dropdown[A]#Props
  private type Result = Dropdown.Measurement[A]
  private val OptionRender = (new DropdownOpt[A])()

  private val emptyResult = Dropdown.Measurement[A](None, None)
  // scalastyle:off var.field
  private var lastOptionsLength: Int = -1
  private var lastResult: Result = emptyResult
  // scalastyle:on var.field

  def get(props: Props): Result = {
    val isSame = lastOptionsLength == props.options.length
    if (isSame) lastResult else calculateAndCache(props)
  }

  private def calculateAndCache(props: Props): Result = {
    val result = Dropdown.Measurement[A](
      optionHeight = getOptionHeight(props),
      biggestWidthOption = getBiggestWidthOption(props)
    )
    lastOptionsLength = props.options.length
    lastResult = result
    result
  }

  // ===

  private def getOptionHeight(props: Props): Option[Int] = {
    val div = dom.document.createElement("div")
    props.options.headOption.map(option => {
      val plain = <.div(OptionRender(option, 0, props)())
      div.innerHTML = ReactDOMServer.renderToStaticMarkup(plain)
      dom.document.body.appendChild(div)
      val height = div.clientHeight
      dom.document.body.removeChild(div)
      height
    })
  }

  private def getBiggestWidthOption(props: Props): Option[Dropdown.Opt[A]] = {
    // It's faster to renderToStatic all options at once
    val container = dom.document.createElement("div")
    container.innerHTML = ReactDOMServer.renderToStaticMarkup({
      val children = props.options.toTagMod(renderValue(props))
      <.div(Style.position.absolute, children)
    })
    // append to actual DOM so clientWidth is correct
    dom.document.body.appendChild(container)
    val nodes = container.children(0).children.toVector.zipWithIndex
    val longestNode = nodes.reduceLeftOption((node1, node2) => {
      if (node1._1.clientWidth > node2._1.clientWidth) node1 else node2
    })
    // clean up
    dom.document.body.removeChild(container)
    longestNode.map(_._2).map(props.options)
  }

  private def renderValue(props: Props)(option: Dropdown.Opt[A]): VdomElement = {
    val v = option.value
    val node = props.renderValue.fold[VdomNode](props.getValueString(v))(_(v))
    <.div(Style.width.maxContent, node)
  }
}
