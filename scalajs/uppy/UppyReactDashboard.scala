// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.uppy

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:off underscore.import

object UppyReactDashboard {
  class Props(
    val uppy: Uppy,
    val plugins: js.UndefOr[js.Array[String]] = js.undefined,
    val proudlyDisplayPoweredByUppy: js.UndefOr[Boolean] = js.undefined,
    val showProgressDetails: js.UndefOr[Boolean] = js.undefined,
    val showLinkToFileUploadResult: js.UndefOr[Boolean] = js.undefined,
    val width: js.UndefOr[Int] = js.undefined,
    val height: js.UndefOr[Int] = js.undefined
  ) extends js.Object

  @JSImport("@uppy/react", "Dashboard")
  @js.native
  object RawComponent extends js.Object

  // Don't try to add type annotation for this
  val component = JsComponent[Props, Children.None, Null](RawComponent)
}
