// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.reactimagecrop

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import japgolly.scalajs.react.{Children, JsComponent}

object ReactImageCrop {

  @JSImport("react-image-crop", JSImport.Default, "ReactCrop")
  @js.native
  object RawComponent extends js.Object

  // See https://github.com/DominicTobias/react-image-crop
  final class Props(
    val src: String,
    val crop: Crop,
    val onChange: js.Function1[Crop, Unit],
    val className: js.UndefOr[String] = js.undefined,
    val style: js.UndefOr[js.Dictionary[String]] = js.undefined,
    val imageStyle: js.UndefOr[js.Dictionary[String]] = js.undefined
  ) extends js.Object

  val component = JsComponent[ReactImageCrop.Props, Children.None, Null](ReactImageCrop.RawComponent)
}
