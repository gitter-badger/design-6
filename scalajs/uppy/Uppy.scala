// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.uppy

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("@uppy/core", JSImport.Default)
@js.native
class Uppy(
  val options: Uppy.Options
) extends js.Object {
  def use(plugin: Uppy.Plugin, options: js.Object): Uppy = js.native
  def on(event: String, callback: js.Function): Uppy = js.native
  def reset(): Unit = js.native
  def close(): Unit = js.native
}

object Uppy {
  final class Options(val id: String) extends js.Object

  trait FileResponse extends js.Object {
    val status: String
    val body: String
  }

  trait File extends js.Object {
    val id: String
    val response: FileResponse
  }

  trait UploadResult extends js.Object {
    val successful: js.Array[File]
    // @TODO: This is bad interface. Will revisit
    val failed: js.Array[js.Object]
  }

  trait Plugin extends js.Object

  def onComplete(uppy: Uppy, callback: Uppy.UploadResult => Unit): Uppy =
    uppy.on("complete", callback)
}
