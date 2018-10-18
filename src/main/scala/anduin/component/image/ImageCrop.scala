// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.image

import anduin.scalajs.reactimagecrop.{Crop, ReactImageCrop}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object ImageCrop {

  private val component = JsComponent[ReactImageCrop.Props, Children.None, Null](ReactImageCrop.RawComponent)

  def apply(
    src: String,
    crop: Crop,
    onChange: Crop => Callback
  ): VdomElement = {
    component(
      new ReactImageCrop.Props(
        src = src,
        crop = crop,
        onChange = c => onChange(c).runNow()
      )
    )
  }
}
