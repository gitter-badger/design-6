// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.image

import scala.concurrent.Promise

import monix.execution.Scheduler.Implicits.global
import org.scalajs.dom.raw.{Event, File, FileReader}

import anduin.component.modal.Modal
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class UploadAndCrop(
  uploadButtonLabel: String,
  cropModalTitle: String,
  cropWidthPercent: Double,
  cropHeightPercent: Double,
  minSize: Option[ImageSize],
  maxSize: Option[ImageSize],
  onUpload: UploadAndCrop.OnUpload => Callback
) {
  def apply(): VdomElement = UploadAndCrop.component(this)
}

object UploadAndCrop {

  private type Props = UploadAndCrop

  case class OnUpload(file: File, onSuccess: Callback, onError: Callback)

  private sealed trait Status
  private object FileNotSelected extends Status
  private case class FileSelected(src: String) extends Status
  private case class Uploading(src: String) extends Status
  private case class DidUpload(src: String) extends Status

  private case class State(status: Status = FileNotSelected)

  private case class Backend(scope: BackendScope[Props, State]) {

    private def onDidSelectFile(file: File) = {
      Callback.future {
        val promise = Promise[Callback]()

        val reader = new FileReader()
        reader.addEventListener(
          "load",
          (_: Event) => {
            @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
            val dataUrl = reader.result.asInstanceOf[String]
            promise.success {
              scope.modState(_.copy(status = FileSelected(dataUrl)))
            }
          }
        )
        reader.readAsDataURL(file)

        promise.future
      }
    }

    private def closeModal(closeCropModal: Callback) = {
      scope.modState(_.copy(status = FileNotSelected), closeCropModal)
    }

    private def chooseAnotherFile(file: File) = {
      onDidSelectFile(file)
    }

    private def startUploading(closeCropModal: Callback)(src: String) = {
      scope.modState(_.copy(status = Uploading(src)), closeCropModal)
    }

    private def didUpload(props: Props)(src: String) = {
      val file = FileUtils.base64ToFile(src, "image/png")
      props.onUpload(
        OnUpload(
          file,
          scope.modState(_.copy(status = DidUpload(src))),
          scope.modState(_.copy(status = FileNotSelected))
        )
      )
    }

    def render(props: Props, state: State): VdomElement = {
      state.status match {
        case FileNotSelected =>
          BrowseFile(
            onDidSelect = onDidSelectFile
          )()

        case FileSelected(src) =>
          Modal(
            isOpened = Some(true),
            title = props.cropModalTitle,
            width = Modal.Width600,
            renderTarget = _ => EmptyVdom,
            renderContent = close => {
              CropImageModal(
                src = src,
                cropWidthPercent = props.cropWidthPercent,
                cropHeightPercent = props.cropHeightPercent,
                minSize = props.minSize,
                maxSize = props.maxSize,
                onCancel = closeModal(close),
                onChooseAnotherFile = chooseAnotherFile,
                onStartUploading = startUploading(close)
              )()
            }
          )()

        case Uploading(src) =>
          UploadingCroppedImage(src, didUpload(props))()

        case DidUpload(src) =>
          BrowseFile(
            onDidSelect = onDidSelectFile
          )(<.img(Style.border.none, ^.src := src))
      }
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialState(State())
    .renderBackend[Backend]
    .build
}
