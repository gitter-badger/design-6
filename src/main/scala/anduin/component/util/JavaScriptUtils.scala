// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.util

import scala.scalajs.js.timers.RawTimers

import japgolly.scalajs.react.Callback
import org.scalajs.dom.document
import org.scalajs.dom.raw.HTMLIFrameElement

object JavaScriptUtils {

  final val voidMethod = "javascript: void(0);"

  // The amount of milliseconds that the download frame will be removed from the document
  private final val DownloadTimeout = 30.0 * 1000.0 // 30 seconds * 1000 milli/sec

  /**
    * Download a given file
    * Usually, we can force the browser to download a file by setting the new URL for `window.location.url`.
    * But Firefox then stops all long polling requests which make our application not reactive as expect.
    * This method fixes this problem as it creates a hidden frame for downloading the file.
    * @param url The given URL
    * @return Callback
    */
  def download(url: String): Callback = {
    val frame = document.createElement("iframe").asInstanceOf[HTMLIFrameElement]
    frame.style.display = "none"
    frame.style.visibility = "hidden"
    document.body.appendChild(frame)

    // Cleanup after given time
    RawTimers.setTimeout(() => {
      document.body.removeChild(frame)
    }, DownloadTimeout)

    Callback {
      frame.src = url
    }
  }
}
