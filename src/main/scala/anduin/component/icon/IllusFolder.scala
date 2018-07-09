// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.icon

import anduin.style.{CssVar, Style}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.svg_<^._
// scalastyle:on underscore.import

final case class IllusFolder(
  color: IllusFolder.Color = IllusFolder.ColorPrimary,
  icon: Icon.Name = Icon.NameBlank,
  size: IllusFolder.Size = IllusFolder.Size32
) {
  def apply(): VdomElement = IllusFolder.component(this)
}

object IllusFolder {

  private type Props = IllusFolder

  sealed trait Color {
    val id: String
    val stop1: String
    val stop2: String
  }
  case object ColorNeutral extends Color {
    val id = "neutral"
    val stop1: String = CssVar.Color.gray4
    val stop2: String = CssVar.Color.gray5
  }
  case object ColorPrimary extends Color {
    val id = "primary"
    val stop1: String = CssVar.Color.primary3
    val stop2: String = CssVar.Color.primary4
  }
  case object ColorSuccess extends Color {
    val id = "success"
    val stop1: String = CssVar.Color.success3
    val stop2: String = CssVar.Color.success4
  }
  case object ColorWarning extends Color {
    val id = "warning"
    val stop1: String = CssVar.Color.warning3
    val stop2: String = CssVar.Color.warning4
  }
  case object ColorDanger extends Color {
    val id = "danger"
    val stop1: String = CssVar.Color.danger3
    val stop2: String = CssVar.Color.danger4
  }

  sealed trait Size { val value: String }
  case object Size24 extends Size { val value = "24" }
  case object Size32 extends Size { val value = "32" }
  case class SizeDynamic(value: String) extends Size

  private def renderDefs(props: Props): VdomElement = {
    <.defs(
      <.linearGradient(
        ^.id := s"folder-gradient-${props.color.id}",
        ^.gradientTransform := "translate(0.5) rotate(45)",
        <.stop(^.offset := "0", ^.stopColor := props.color.stop1),
        <.stop(^.offset := "100", ^.stopColor := props.color.stop2)
      )
    )
  }

  private val pathMods = TagMod(
    ^.d :=
      """
        |M2 0a2 2 0 0 0-2 2v20c0 1.1.9 2 2 2h24a2 2 0 0 0 2-2V5a2 2 0 0
        |0-2-2H13c-.82 0-2.2-1.39-3-2.27C9.6.28 9.04 0 8.44 0H2z
      """.stripMargin,
    ^.fillRule := "evenodd",
    ^.clipRule := "evenodd",
    ^.transform := "translate(2 4)"
  )

  private def renderPath(props: Props): VdomElement = {
    <.path(pathMods, ^.fill := s"url(#folder-gradient-${props.color.id}")
  }

  private val iconMods = TagMod(
    Style.height.pc100.position.absolute.coordinate.fill, // fill
    Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter, // center
    vdom.html_<^.^.paddingTop := "3px", // visual touch
    Style.color.gray9.opacity.pc50
  )

  private def renderIcon(props: Props): VdomElement = {
    val icon = Icon(name = props.icon, size = Icon.SizeDynamic("12"))()
    vdom.html_<^.<.div(iconMods, icon)
  }

  private val svgMods = TagMod(
    Style.display.block, // https://codepen.io/dvkndn/pen/wmQmbm
    ^.fill := "none",
    ^.xmlns := "http://www.w3.org/2000/svg",
    ^.viewBox := "0 0 32 32"
  )

  private def render(props: Props): VdomElement = {
    vdom.html_<^.<.div(
      Style.position.relative.width.maxContent,
      <.svg(
        svgMods,
        ^.width := props.size.value,
        ^.height := props.size.value,
        renderPath(props),
        renderDefs(props) // @TODO: defs should be defined globally
      ),
      renderIcon(props)
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
