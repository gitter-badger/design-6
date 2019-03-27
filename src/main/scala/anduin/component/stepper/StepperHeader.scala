// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.stepper

import anduin.style.{CssVar, Style}
import anduin.component.icon.Icon
import anduin.component.util.ComponentUtils

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class StepperHeader(
  titles: Seq[String],
  current: Int,
  onDarkBackground: Boolean = false
) {
  def apply(): VdomElement = StepperHeader.component(this)
}

object StepperHeader {

  private type Props = StepperHeader

  private sealed trait Status
  private object StatusPast extends Status
  private object StatusPresent extends Status
  private object StatusFuture extends Status
  private def getStatus(props: Props, title: String): Status = {
    val index = props.titles.indexOf(title)
    index match {
      case x: Int if x < props.current  => StatusPast
      case y: Int if y == props.current => StatusPresent
      case z: Int if z > props.current  => StatusFuture
    }
  }

  private object Dot {
    private def getMods(status: Status, onDarkBackground: Boolean) = {
      if (onDarkBackground) {
        status match {
          case StatusPast    ⇒ Style.borderColor.success3.background.success3.color.gray0
          case StatusPresent ⇒ Style.borderColor.primary3.color.primary3
          case StatusFuture  ⇒ Style.borderColor.gray5
        }
      } else {
        status match {
          case StatusPast    ⇒ Style.borderColor.success3.background.success3.color.gray0
          case StatusPresent ⇒ Style.borderColor.primary4.color.primary4
          case StatusFuture  ⇒ Style.borderColor.gray3
        }
      }
    }
    private val static = TagMod(
      Style.flexbox.none.width.px20.height.px20,
      Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter,
      Style.border.all.borderWidth.px2.borderRadius.pill
    )

    private def getIconName(status: Status) = status match {
      case StatusPast    ⇒ Icon.Glyph.CheckBold
      case StatusPresent ⇒ Icon.Glyph.Circle
      case StatusFuture  ⇒ Icon.Glyph.Blank
    }

    def render(props: Props, title: String): VdomElement = {
      val status = getStatus(props, title)
      <.div(
        static,
        getMods(status, props.onDarkBackground),
        Icon(getIconName(status))()
      )
    }
  }

  /*
   * If future => both lines are gray
   * If present => right always gray, left always (green > blue)
   * If past => both lines are green
   */
  private object Line {
    private def gradientGreenToBlue(blueColor: String) =
      ^.background := s"linear-gradient(to right, ${CssVar.Color.success3}, $blueColor)"

    private def getColors(status: Status, onDarkBackground: Boolean): (TagMod, TagMod) = {
      if (onDarkBackground) {
        status match {
          case StatusPast    => (Style.background.success3, Style.background.success3)
          case StatusPresent => (gradientGreenToBlue(CssVar.Color.primary3), Style.background.gray5)
          case StatusFuture  => (Style.background.gray5, Style.background.gray5)
        }
      } else {
        status match {
          case StatusPast    => (Style.background.success3, Style.background.success3)
          case StatusPresent => (gradientGreenToBlue(CssVar.Color.primary4), Style.background.gray3)
          case StatusFuture  => (Style.background.gray3, Style.background.gray3)
        }
      }

    }

    private def renderLine(isInvisible: Boolean, color: TagMod): VdomElement = {
      val opacity = TagMod.when(isInvisible)(Style.opacity.pc0)
      <.div(Style.flexbox.fill, color, opacity, ^.height := "2px")
    }

    def render(props: Props, title: String): (VdomElement, VdomElement) = {
      val (leftLineColor, rightLineColor) = getColors(getStatus(props, title), props.onDarkBackground)
      (
        renderLine(props.titles.headOption.contains(title), leftLineColor),
        renderLine(props.titles.lastOption.contains(title), rightLineColor)
      )
    }
  }

  private def getTitleMods(status: Status, onDarkBackground: Boolean) = {
    if (onDarkBackground) {
      status match {
        case StatusPast ⇒ Style.color.success3
        case StatusPresent ⇒ Style.color.gray0
        case StatusFuture ⇒ Style.color.gray4
      }
    } else {
      status match {
        case StatusPast ⇒ Style.color.success4
        case StatusPresent ⇒ Style.color.gray8
        case StatusFuture ⇒ Style.color.gray6
      }
    }
  }

  private def renderTitle(props: Props, title: String): VdomElement = {
    val status = getStatus(props, title)
    val state = status match {
      case StatusPast ⇒ "Past"
      case StatusPresent ⇒ "Current"
      case StatusFuture ⇒ "Future"
    }

    <.p(
      ComponentUtils.testId(StepperHeader, state),
      Style.fontWeight.medium.padding.hor32,
      Style.whiteSpace.preLine.textAlign.center,
      getTitleMods(status, props.onDarkBackground),
      title
    )
  }

  private val upperStyles =
    Style.margin.bottom8.flexbox.flex.flexbox.itemsCenter

  private def renderChild(props: Props)(title: String): VdomElement = {
    val lines = Line.render(props, title)
    <.div(
      ComponentUtils.testId(StepperHeader, title),
      ^.key := title,
      <.div(upperStyles, lines._1, Dot.render(props, title), lines._2),
      renderTitle(props, title)
    )
  }

  private val styles = TagMod(
    Style.flexbox.flex.flexbox.justifyCenter,
    Style.padding.top8
  )

  private def render(props: Props): VdomElement = {
    <.div(
      ComponentUtils.testId(StepperHeader, "Container"),
      styles,
      props.titles.toVdomArray(renderChild(props))
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
