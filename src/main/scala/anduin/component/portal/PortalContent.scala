// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// This existed in order to trigger callbacks to Portal's content lifecycle
// events. See https://github.com/anduintransaction/stargazer/issues/17418
private[portal] final case class PortalContent(
  onDidMount: Callback, // afterOpen
  onWillUnmount: Callback // beforeClose
) {
  def apply(children: VdomNode*): VdomNode =
    PortalContent.component(this)(children: _*)
}

private[portal] object PortalContent {

  private type Props = PortalContent

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_C(c => c) // Can't use identity here
    .componentDidMount(_.props.onDidMount)
    .componentWillUnmount(_.props.onWillUnmount)
    .build
}
