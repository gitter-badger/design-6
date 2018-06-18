// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.d3

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

// import * as d3 from d3
@JSImport("d3", JSImport.Namespace)
@js.native
object D3 extends js.Object {
  def axisBottom[T](scale: Scale[T]): Axis = js.native // linter:ignore UnusedParameter
  def axisLeft[T](scale: Scale[T]): Axis = js.native // linter:ignore UnusedParameter
  def event: Event = js.native
  def select(selector: String): Selection = js.native // linter:ignore UnusedParameter
  def scalePoint[T](): Scale[T] = js.native // linter:ignore UnusedParameter
  def scaleTime(): Scale[js.Date] = js.native // linter:ignore UnusedParameter
  def timeFormat(format: String): Format = js.native // linter:ignore UnusedParameter
}
