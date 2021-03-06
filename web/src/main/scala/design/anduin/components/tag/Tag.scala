// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.tag

import design.anduin.components.icon.Icon
import design.anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Tag(
  color: TagColor = Tag.Light.Gray,
  icon: Option[Icon.Name] = None,
  target: TagTarget = Tag.Target.None,
  close: Option[Callback] = None,
  maxWidth: Int = 128 // in pixel
) {
  def apply(children: String): VdomElement = {
    Tag.component((this, children))
  }
}

object Tag {

  // The "String" here to represent component's children, since we want to
  // enforce it to always be String instead of the usual VdomNode*
  private type Props = (Tag, String)

  object Bold {
    val Gray: TagColor = TagColor.Bold.Gray
    val Primary: TagColor = TagColor.Bold.Primary
    val Success: TagColor = TagColor.Bold.Success
    val Warning: TagColor = TagColor.Bold.Warning
    val Danger: TagColor = TagColor.Bold.Danger
  }

  object Light {
    val Gray: TagColor = TagColor.Light.Gray
    val Primary: TagColor = TagColor.Light.Primary
    val Success: TagColor = TagColor.Light.Success
    val Warning: TagColor = TagColor.Light.Warning
    val Danger: TagColor = TagColor.Light.Danger
  }

  object Target {
    val None: TagTarget = TagTarget.None
    case class Button(onClick: Callback) extends TagTarget.Button
    case class Link(href: String) extends TagTarget.Link
  }

  private val bodyStaticStyles = TagMod(
    Style.fontWeight.semiBold.fontSize.px11,
    Style.flexbox.flex.flexbox.itemsCenter,
    Style.height.px20.padding.hor4.borderRadius.px2,
    Style.width.maxContent // auto truncate
  )

  private val textStaticStyles =
    Style.flexbox.fill.typography.truncate // auto truncate

  private val interactiveStaticStyles =
    Style.outline.focusLight.transition.allWithOutline

  private def renderIcon(props: Props)(name: Icon.Name): VdomElement = {
    <.span(
      Style.flexbox.none.margin.right4,
      props._1.color.text.secondary,
      Icon(name, size = Icon.Size.Custom(12))()
    )
  }

  private val closeStaticStyles = TagMod(
    Style.flexbox.none.width.px20.height.px20,
    Style.borderRadius.px2.borderRadius.right,
    Style.flexbox.flex.flexbox.itemsCenter.flexbox.justifyCenter
  )

  private def renderClose(props: Props)(close: Callback): VdomElement = {
    <.button(
      props._1.color.bg.rest,
      props._1.color.bg.interactive,
      props._1.color.text.secondary,
      closeStaticStyles,
      interactiveStaticStyles,
      ^.onClick --> close,
      Icon(name = Icon.Glyph.CrossSmall)()
    )
  }

  private def getInteractive(props: Props) =
    TagMod(props._1.color.bg.interactive, interactiveStaticStyles)

  def render(props: Props): VdomElement = {
    <.div(
      Style.flexbox.flex.width.maxContent,
      // - body
      // -- behaviour is defined inside target.tag
      props._1.target.tag(
        // -- appearance
        props._1.color.bg.rest,
        TagMod.when(props._1.target.isInteractive)(getInteractive(props)),
        TagMod.when(props._1.close.isDefined)(Style.borderRadius.left),
        bodyStaticStyles,
        ^.maxWidth := s"${props._1.maxWidth}px", // for truncate
        // -- content
        props._1.icon.map(renderIcon(props)),
        <.span(props._1.color.text.primary, textStaticStyles, props._2),
        ^.title := props._2
      ),
      // - close button
      props._1.close.map(renderClose(props))
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
