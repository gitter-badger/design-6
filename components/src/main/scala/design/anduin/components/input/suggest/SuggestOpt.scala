// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.suggest

import anduin.component.menu.MenuItem
import anduin.scalajs.downshift.{DownshiftItemOptions, DownshiftRenderProps}
import anduin.scalajs.util.ScalaJSUtils
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[suggest] class SuggestOpt[A] {

  def apply(): Props.type = Props

  case class Props(
    suggest: Suggest[A]#Props,
    downshift: Option[DownshiftRenderProps[A]],
    opt: Suggest.Opt[A],
    index: Int
  ) {
    def apply(): VdomElement = component(this)
  }

  private def getDownshiftMod(props: Props): TagMod = {
    props.downshift.fold(TagMod.empty) { downshift =>
      val options = new DownshiftItemOptions[A](props.index, props.opt.value, props.opt.isDisabled)
      ScalaJSUtils.jsPropsToTagMod(downshift.getItemProps(options))
    }
  }

  private def getStyles(props: Props): TagMod = TagMod(
    // isHighlighted
    TagMod.when(props.downshift.exists { downshift =>
      ScalaJSUtils.jsNullToOption(downshift.highlightedIndex).contains(props.index)
    })(Style.background.gray2),
    // isDisabled
    TagMod.when(props.opt.isDisabled)(Style.color.gray6)
  )

  private def render(props: Props): VdomElement = {
    <.button(
      MenuItem.buttonStyles,
      getDownshiftMod(props),
      getStyles(props)
    )(props.suggest.opts.render(props.opt.value))
  }

  private val component = ScalaFnComponent(render)
}
