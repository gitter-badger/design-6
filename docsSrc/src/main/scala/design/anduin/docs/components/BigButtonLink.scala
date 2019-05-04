package design.anduin.docs.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import design.anduin.components.button.Button
import design.anduin.components.icon.Icon
import design.anduin.docs.app.main.Pages
import design.anduin.style.Style

final case class BigButtonLink(
  ctl: Pages.Ctl,
  page: Pages.Page,
  label: String
) {
  def apply(): VdomElement = BigButtonLink.component(this)
}

object BigButtonLink {

  private type Props = BigButtonLink

  private def render(props: Props): VdomElement = {
    <.div(
      Style.fontFamily.sans.padding.ver12,
      Button(
        tpe = Button.Tpe.Link(props.ctl.urlFor(props.page).value),
        style = Button.Style.Full(color = Button.Color.Primary, height = Button.Height.Fix40)
      )(
        <.span(Style.margin.right12, props.label),
        Icon(name = Icon.Glyph.ArrowRight)()
      )
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
