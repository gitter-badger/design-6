// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.datefns

import scala.scalajs.js

trait DistanceInWordsOptions extends js.Object {
  def includeSeconds: Boolean
  def addSuffix: Boolean
}

object DistanceInWordsOptions {

  def apply(
    includeSecondsParam: Boolean = false,
    addSuffixParam: Boolean = false
  ): DistanceInWordsOptions = {
    new DistanceInWordsOptions {
      val includeSeconds: Boolean = includeSecondsParam
      val addSuffix: Boolean = addSuffixParam
    }
  }
}
