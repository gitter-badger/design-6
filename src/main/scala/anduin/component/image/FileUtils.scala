// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.image

import scala.scalajs.js
import scala.scalajs.js.typedarray.{ArrayBuffer, Uint8Array}

import org.scalajs.dom
import org.scalajs.dom.raw.{Blob, BlobPropertyBag, File}

private[image] object FileUtils {

  private final val Base64Prefix = ";base64,"

  private def base64ToByteArray(dataUri: String): Uint8Array = {
    val base64Index = dataUri.indexOf(Base64Prefix) + Base64Prefix.length
    val base64 = dataUri.substring(base64Index)
    val raw = dom.window.atob(base64)
    val rawLength = raw.length
    val array = new Uint8Array(new ArrayBuffer(rawLength))

    0.until(rawLength).foreach { i =>
      array(i) = raw.charAt(i).toShort
    }

    array
  }

  def base64ToFile(dataUri: String, contentType: String): File = {
    val bytes = base64ToByteArray(dataUri)

    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
    val file = new Blob(js.Array(bytes), BlobPropertyBag(contentType)).asInstanceOf[File]
    file
  }
}
