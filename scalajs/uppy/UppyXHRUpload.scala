// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.uppy

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("@uppy/xhr-upload", JSImport.Default)
@js.native
object UppyXHRUploadObject extends Uppy.Plugin

object UppyXHRUpload {

  trait Response extends js.Object {
    val responseText: String
    val status: String
    val statusText: String
    val response: js.Dictionary[String]
  }

  final class Options(
    val endpoint: String,
    val headers: js.Dictionary[String],
    val limit: Int,
    val fieldName: String,
    val getResponseData: js.Function2[String, Response, String]
  ) extends js.Object

  def addToUppy(uppy: Uppy, options: UppyXHRUpload.Options): Uppy =
    uppy.use(UppyXHRUploadObject, options)
}
