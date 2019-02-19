// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.utils

import org.scalajs.dom

object RedirectorUtils {
  def redirectToUrl(url: String): Unit = {
    dom.window.setTimeout(() => dom.window.location.replace(url), 2000)
    ()
  }
}
