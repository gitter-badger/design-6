// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.labelwrapper

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// This wraps a label around an input
// - This is the common container of Checkbox and Radio
final private[input] case class LabelWrapper(
  inputChildren: List[(Int, VdomNode)],
  isDisabled: Boolean,
  hasPointer: Boolean
) {
  def apply(children: VdomNode*): VdomElement =
    LabelWrapper.component(this)(children: _*)
}

private[input] object LabelWrapper {

  private type Props = LabelWrapper

  // We'd like to let Radio uses this
  private def renderText(props: Props, children: PropsChildren): Option[VdomElement] = {
    if (children.isEmpty) {
      None
    } else {
      val color = TagMod.when(props.isDisabled)(Style.color.gray5)
      val label = <.span(Style.flexbox.fill.margin.left8, color, children)
      Some(label)
    }
  }

  private def getInputChildStyles(size: Int) = TagMod(
    TagMod(^.width := s"${size}px", ^.height := s"${size}px"),
    Style.display.block.margin.allAuto,
    Style.position.absolute.position.pinAll
  )

  private def renderInput(props: Props): VdomElement = {
    <.span(
      // The size of the box is 16x16 but we want it to align nicely with
      // the label, which line-height is 20px and might have several lines
      // - We also need to define the width here since its children are all
      //   absolutely positioned
      Style.display.block.width.px16.height.px20.position.relative,
      props.inputChildren.toReactFragment { input =>
        <.span(getInputChildStyles(input._1), input._2)
      }
    )
  }

  def render(props: Props, children: PropsChildren): VdomElement = {
    <.label(
      Style.flexbox.flex.flexbox.itemsStart,
      Style.position.relative.width.maxContent.maxWidth.pc100,
      TagMod.when(props.isDisabled)(Style.pointerEvents.none),
      TagMod.when(props.hasPointer && !props.isDisabled)(Style.cursor.pointer),
      renderInput(props),
      renderText(props, children)
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build

}
