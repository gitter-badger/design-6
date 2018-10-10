// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.style.Style
import org.scalajs.dom

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.ext._
// scalastyle:on underscore.import

private[dropdown] class DropdownMeasure[A] {

  private type Props = Dropdown[A]#Props
  private type Result = Dropdown.Measurement[A]

  // scalastyle:off var.field
  private var lastOptionsLength: Int = -1
  private var lastResultOpt: Option[Result] = None
  // scalastyle:on var.field

  def get(props: Props): Option[Result] = {
    lastResultOpt.fold(calculateAndCache(props))(lastResult => {
      val isSame = lastOptionsLength == props.options.length
      if (isSame) Some(lastResult) else calculateAndCache(props)
    })
  }

  private def calculateAndCache(props: Props): Option[Result] = {
    val result = props.options.headOption.map { _ =>
      Dropdown.Measurement[A](
        optionHeight = getOptionHeight(props),
        biggestWidthOption = getBiggestWidthOption(props)
      )
    }
    lastOptionsLength = props.options.length
    lastResultOpt = result
    result
  }

  // ===

  private def getOptionHeight(props: Props): Int = {
    val div = dom.document.createElement("div")
    val plain = props.options.headOption
      .fold(EmptyVdom)(DropdownOption.renderPlain[A](props))
    div.innerHTML = ReactDOMServer.renderToStaticMarkup(<.div(plain))
    dom.document.body.appendChild(div)
    val height = div.clientHeight
    dom.document.body.removeChild(div)
    height
  }

  private def getBiggestWidthOption(props: Props): Dropdown.Opt[A] = {
    // It's faster to renderToStatic all options at once
    val container = dom.document.createElement("div")
    container.innerHTML = ReactDOMServer.renderToStaticMarkup({
      val children = props.options.toTagMod(renderValue(props))
      <.div(Style.flexbox.flex.position.absolute, children)
    })
    // append to actual DOM so clientWidth is correct
    dom.document.body.appendChild(container)
    val nodes = container.children(0).children.toVector.zipWithIndex
    val longestIndex = nodes.maxBy(_._1.clientWidth)._2
    // clean up
    dom.document.body.removeChild(container)
    props.options(longestIndex)
  }

  private def renderValue(props: Props)(option: Dropdown.Opt[A]): VdomElement = {
    <.div(props.renderValue(option.value))
  }
}
