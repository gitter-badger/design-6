// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.image

import scala.scalajs.js

import anduin.scalajs.reactimagecrop.{Crop, ReactImageCrop}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ImageCrop(
  src: String,
  crop: Crop,
  className: String = "",
  style: Map[String, String] = Map.empty,
  imageStyle: Map[String, String] = Map.empty,
  onChange: Crop => Callback
) {
  def apply(): VdomElement = ImageCrop.component(this)
}

object ImageCrop {

  private type Props = ImageCrop

  private def render(props: Props) = {
    ReactImageCrop.component(
      new ReactImageCrop.Props(
        src = props.src,
        crop = props.crop,
        className = props.className,
        style = js.Dictionary(props.style.toSeq: _*),
        imageStyle = js.Dictionary(props.imageStyle.toSeq: _*),
        onChange = c => props.onChange(c).runNow()
      )
    )
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
