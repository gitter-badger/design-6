// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

import scala.scalajs.js.timers.RawTimers

import japgolly.scalajs.react.Callback
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.raw.HTMLIFrameElement

object JavaScriptUtils {

  val voidMethod = "javascript: void(0);"

  // The amount of milliseconds that the download frame will be removed from the document
  private val DownloadTimeout = 30.0 * 1000.0 // 30 seconds * 1000 milli/sec

  /**
    * Download a given file
    * Usually, we can force the browser to download a file by setting the new URL for `window.location.url`.
    * But Firefox then stops all long polling requests which make our application not reactive as expect.
    * This method fixes this problem as it creates a hidden frame for downloading the file.
    * @param url The given URL
    * @return Callback
    */
  def download(url: String): Callback = {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
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

  /**
    * Target can be "_self" so that this url open in a same tab.
    * If target is "_blank", this url will be opened in a new tab
    * @param url
    * @param target
    * @return Callback
    */
  def openURL(
    url: String,
    target: String = "_self"
  ): Callback = {
    Callback(
      dom.window.open(url, target)
    )
  }

  /**
    * Close current tab on a browser
    * @return Callback
    */
  def closeTab(): Callback = {
    Callback(
      dom.window.close()
    )
  }
}
