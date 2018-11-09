// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.stepper

import anduin.style.{CssVar, Style}
import anduin.component.icon.Icon

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class StepperHeader(
  titles: Seq[String],
  current: Int
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
    private def getMods(status: Status) = status match {
      case StatusPast    => (Style.borderColor.success3.backgroundColor.success3.color.white, Icon.NameCheckBold)
      case StatusPresent => (Style.borderColor.primary4.color.primary4, Icon.NameCircle)
      case StatusFuture  => (Style.borderColor.gray3, Icon.NameBlank)
    }
    private val static = TagMod(
      Style.flexbox.none.width.px20.height.px20,
      Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter,
      Style.border.all.borderWidth.px2.borderRadius.pc100
    )

    def render(props: Props, title: String): VdomElement = {
      val mods = getMods(getStatus(props, title))
      <.div(static, mods._1, Icon(mods._2)())
    }
  }

  /*
   * If future => both lines are gray
   * If present => right always gray, left always (green > blue)
   * If past => both lines are green
   */
  private object Line {
    private val gradientSuccessToPrimary =
      ^.background := s"linear-gradient(to right, ${CssVar.Color.success3}, ${CssVar.Color.primary4})"

    private def getColors(status: Status): (TagMod, TagMod) = status match {
      case StatusPast    => (Style.backgroundColor.success3, Style.backgroundColor.success3)
      case StatusPresent => (gradientSuccessToPrimary, Style.backgroundColor.gray3)
      case StatusFuture  => (Style.backgroundColor.gray3, Style.backgroundColor.gray3)
    }

    private def renderLine(isInvisible: Boolean, color: TagMod): VdomElement = {
      val opacity = TagMod.when(isInvisible)(Style.opacity.pc0)
      <.div(Style.flexbox.fixed, color, opacity, ^.height := "2px")
    }

    def render(props: Props, title: String): (VdomElement, VdomElement) = {
      val colors = getColors(getStatus(props, title))
      (
        renderLine(props.titles.headOption.contains(title), colors._1),
        renderLine(props.titles.lastOption.contains(title), colors._2)
      )
    }
  }
  private def renderTitle(props: Props, title: String): VdomElement = {
    <.p(
      Style.fontWeight.medium.padding.hor32,
      Style.whiteSpace.preLine.textAlign.center, {
        val isFuture = props.titles.indexOf(title) > props.current
        TagMod.when(isFuture)(Style.color.gray6)
      },
      title
    )
  }

  private val upperStyles =
    Style.margin.bottom8.flexbox.flex.flexbox.itemsCenter

  private def renderChild(props: Props)(title: String): VdomElement = {
    val lines = Line.render(props, title)
    <.div(
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
    <.div(styles, props.titles.toVdomArray(renderChild(props)))
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
