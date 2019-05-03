// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.labelwrapper

import anduin.style.{Style => SStyle}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// This wraps a label around an input
// - This is the common container of Checkbox and Radio
final private[input] case class LabelWrapper(
  inputChildren: List[(Int, VdomNode)],
  isDisabled: Boolean,
  isInteractive: Boolean,
  style: LabelWrapper.Style
) {
  def apply(children: VdomNode*): VdomElement =
    LabelWrapper.component(this)(children: _*)
}

private[input] object LabelWrapper {

  private type Props = LabelWrapper

  sealed trait Style {
    private[labelwrapper] def render: (Props, PropsChildren) => VdomElement
  }

  object Style {

    // Common parts between Style.Full and Style.Minimal
    object Common {
      private def renderText(props: Props, children: PropsChildren): Option[VdomElement] = {
        if (children.isEmpty) {
          None
        } else {
          val color = TagMod.when(props.isDisabled)(SStyle.color.gray5)
          val label = <.span(SStyle.flexbox.fill.margin.left8, color, children)
          Some(label)
        }
      }

      private def getInputChildStyles(size: Int) = TagMod(
        TagMod(^.width := s"${size}px", ^.height := s"${size}px"),
        SStyle.display.block.margin.allAuto,
        SStyle.position.absolute.position.pinAll
      )

      // The size of the box is 16x16 but we want it to align nicely with
      // the label, which line-height is 20px and might have several lines
      // - We also need to define the width here since its children are all
      //   absolutely positioned
      private def renderInput(props: Props): VdomElement = {
        <.span(
          SStyle.display.block.width.px16.height.px20.position.relative,
          props.inputChildren.toReactFragment { input =>
            <.span(getInputChildStyles(input._1), input._2)
          }
        )
      }

      def get(props: Props, children: PropsChildren): TagMod = TagMod(
        TagMod.when(props.isDisabled)(SStyle.pointerEvents.none),
        TagMod.when(props.isInteractive && !props.isDisabled)(SStyle.cursor.pointer),
        renderInput(props),
        renderText(props, children)
      )
    }

    object Minimal extends Style {
      private def renderMinimal(props: Props, children: PropsChildren): VdomElement =
        <.label(
          SStyle.flexbox.flex.flexbox.itemsStart,
          SStyle.position.relative.width.maxContent.maxWidth.pc100,
          Common.get(props, children)
        )
      private[labelwrapper] final def render = renderMinimal
    }

    case class Full(isSelected: Boolean) extends Style {
      private[labelwrapper] final def render = Full.render(this)
    }
    object Full {
      private val staticStyles = TagMod(
        SStyle.flexbox.flex.flexbox.itemsStart.border.all.borderRadius.px2,
        SStyle.width.pc100.padding.ver8.padding.hor12.position.relative,
        SStyle.background.gray0.outline.focusLight.transition.allWithOutline
      )

      def render(full: Style.Full)(props: Props, children: PropsChildren): VdomElement = {
        <.label(
          staticStyles,
          TagMod.when(props.isInteractive)(SStyle.background.activeGray2),
          if (full.isSelected) SStyle.borderColor.primary4 else SStyle.borderColor.gray4,
          Common.get(props, children)
        )
      }
    }
  }

  private def render(props: Props, children: PropsChildren): VdomElement = {
    props.style.render(props, children)
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_PC(render)
    .build

}
