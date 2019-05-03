// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.util

import scala.scalajs.js.timers.RawTimers

import japgolly.scalajs.react.Callback
import org.scalajs.dom
import org.scalajs.dom.raw.{Blob, HTMLIFrameElement, HTMLLinkElement, URL}
import org.scalajs.dom.{XMLHttpRequest, document}

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
    */
  private def downloadWithIframe(url: String) = {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
    val frame = document.createElement("iframe").asInstanceOf[HTMLIFrameElement]
    frame.style.display = "none"
    frame.style.visibility = "hidden"
    document.body.appendChild(frame)

    // Cleanup after given time
    RawTimers.setTimeout(() => {
      document.body.removeChild(frame)
    }, DownloadTimeout)

    frame.src = url
  }

  /**
    * Use the `download` attribute on a link to force the browser
    * to download given file
    * @param url The given URL
    * @param fileName The file name
    */
  private def downloadWithLink(url: String, fileName: String) = {
    // If the `url` and our app belong to different domains,
    // Chrome ignores the `download` attribute.
    // See https://www.chromestatus.com/feature/4969697975992320
    val userAgent = dom.window.navigator.userAgent
    val isChrome = userAgent.indexOf("Chrome") > -1
    if (!isChrome) {
      forceDownload(url, fileName)
    } else {
      val xhr = new XMLHttpRequest
      xhr.responseType = "blob"
      xhr.onload = _ => {
        if (xhr.status == 200) {
          @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
          val blob = xhr.response.asInstanceOf[Blob]
          val blobUrl = URL.createObjectURL(blob)

          forceDownload(blobUrl, fileName)
          URL.revokeObjectURL(blobUrl)
        }
      }
      xhr.open("GET", url, async = true)
      xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest")
      xhr.send()
    }
  }

  private def forceDownload(url: String, fileName: String) = {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
    val link = document.createElement("a").asInstanceOf[HTMLLinkElement]
    link.style.display = "none"
    link.style.visibility = "hidden"
    link.rel = "noopener"
    link.setAttribute("download", fileName)

    // Firefox requires the element available on page
    document.body.appendChild(link)

    // Let's download the file
    link.href = url
    link.click()

    // Remove the link from document
    document.body.removeChild(link)
    ()
  }

  def download(url: String, fileName: Option[String] = None): Callback = {
    Callback {
      fileName.fold {
        downloadWithIframe(url)
      } { name =>
        // We can use an iframe to download the file
        // if the file name taken from `url` is the same as `fileName`
        val fileNameFromUrl = url.substring(url.lastIndexOf("/") + 1)
        if (fileNameFromUrl.startsWith(name)) {
          downloadWithIframe(url)
        } else {
          downloadWithLink(url, name)
        }
      }
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
