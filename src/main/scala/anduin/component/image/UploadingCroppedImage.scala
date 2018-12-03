// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.image

import anduin.component.progressindicators.InlineLoader
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[image] final case class UploadingCroppedImage(
  src: String,
  onDidUpload: String => Callback
) {
  def apply(): VdomElement = UploadingCroppedImage.component(this)
}

private[image] object UploadingCroppedImage {

  private type Props = UploadingCroppedImage

  private case class Backend(scope: BackendScope[Props, _]) {

    def startUploading: Callback = {
      scope.props.flatMap { p =>
        p.onDidUpload(p.src)
      }
    }

    def render(): VdomElement = {
      <.div(
        Style.flexbox.flex.flexbox.itemsCenter,
        InlineLoader()
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .componentDidMount(_.backend.startUploading)
    .build
}
