// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.datefns

import scala.scalajs.js
import scala.scalajs.js.{Date, UndefOr}
import scala.scalajs.js.annotation.JSImport

@JSImport("date-fns/distance_in_words_to_now", JSImport.Default)
@js.native
object DistanceInWordsToNow extends js.Object {
  def apply(date: Date, options: UndefOr[DistanceInWordsOptions] = js.undefined): String = js.native
}
