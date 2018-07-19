// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.pluralize

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("pluralize", JSImport.Namespace)
@js.native
object Pluralize extends js.Object {
  // Pluralize or singularize a word based on the passed in count
  // ```
  //  Pluralize('test') => "tests"
  //  Pluralize('test', 1) => "test"
  //  Pluralize('test', 5) => "tests"
  //  Pluralize('test', 1, true) => "1 test"
  //  Pluralize('test', 5, true) => "5 tests"
  // ```
  def apply(
    word: String,
    count: js.UndefOr[Int] = js.undefined,
    inclusive: js.UndefOr[Boolean] = js.undefined
  ): String = js.native
}
