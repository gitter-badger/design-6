// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.image

import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.raw.{HTMLCanvasElement, HTMLElement}

import anduin.component.button.{Button, ButtonStyle}
import anduin.component.portal.{ModalBody, ModalFooter}
import anduin.scalajs.reactimagecrop.Crop
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[image] final case class CropImageModal(
  src: String,
  cropWidthPercent: Double,
  cropHeightPercent: Double,
  aspectRatio: Double,
  onCancel: Callback,
  onStartUploading: String => Callback
) {
  val originalCrop = new Crop(0, 0, cropWidthPercent, cropHeightPercent, aspectRatio)
  def apply(): VdomElement = CropImageModal.component(this)
}

private[image] object CropImageModal {

  private case class State(
    src: String,
    crop: Crop,
    originalWidth: Int = 0,
    originalHeight: Int = 0
  )

  private type Props = CropImageModal

  private case class Backend(scope: BackendScope[Props, State]) {

    private val imageRef = Ref[HTMLElement]

    private def onDidLoadImage(e: ReactEventFrom[dom.html.Image]) = {
      e.extract(_.target) { t =>
        scope.modState(_.copy(originalHeight = t.clientHeight, originalWidth = t.clientWidth))
      }
    }

    private def crop(props: Props, state: State) = {
      for {
        image <- imageRef.get
        _ <- {
          val crop = state.crop
          val x = crop.x * state.originalWidth / 100
          val y = crop.y * state.originalHeight / 100
          val width = crop.widthPercent * state.originalWidth / 100
          // Keep the ratio
          val height = width * props.aspectRatio

          @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
          val canvas = dom.document.createElement("canvas").asInstanceOf[HTMLCanvasElement]
          canvas.width = width.toInt
          canvas.height = height.toInt

          @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
          val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
          ctx.drawImage(image, x, y, width, height, 0, 0, width, height)

          val dataUrl = canvas.toDataURL("image/jpeg")
          scope.modState(_.copy(src = dataUrl), props.onStartUploading(dataUrl))
        }
      } yield ()
    }

    def render(props: Props, state: State): VdomElement = {
      <.div(
        Style.position.relative,
        <.div(
          Style.position.absolute.coordinate.left0.coordinate.top0,
          ^.visibility.hidden,
          <.img.withRef(imageRef)(
            ^.src := props.src,
            ^.onLoad ==> onDidLoadImage
          )
        ),
        <.div(
          ModalBody()(
            <.div(
              Style.flexbox.flex.flexbox.justifyCenter.border.all.borderRadius.px2.borderColor.gray4.padding.all20,
              ImageCrop(
                src = state.src,
                crop = state.crop,
                onChange = c => scope.modState(_.copy(crop = c))
              )()
            )
          ),
          ModalFooter()(
            <.div(
              Style.flexbox.flex.flexbox.justifyEnd,
              <.div(
                Style.margin.right8,
                Button(
                  color = ButtonStyle.ColorPrimary,
                  onClick = crop(props, state)
                )("Crop and Upload")
              ),
              Button(onClick = props.onCancel)("Cancel")
            )
          )
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .initialStateFromProps { props =>
      State(
        src = props.src,
        crop = props.originalCrop
      )
    }
    .renderBackend[Backend]
    .build
}
