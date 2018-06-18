// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.d3

import scala.scalajs.js

@js.native
trait Axis extends js.Object {
  def tickFormat(format: Format): Axis = js.native // linter:ignore UnusedParameter
  def tickFormat(format: js.Function2[js.Object, Int, js.Object]): Axis = js.native // linter:ignore UnusedParameter
}

@js.native
trait Format extends js.Object
