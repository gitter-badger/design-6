// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.editor.email

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ViewOnlyEmail(content: String) {
  def apply(): VdomElement = ViewOnlyEmail.component(this)
}

object ViewOnlyEmail {

  private type Props = ViewOnlyEmail

  private case class Backend(scope: BackendScope[Props, _]) {

    def render(props: Props): VdomElement = {
      EmailFrame(
        content = props.content
      )()
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build
}
