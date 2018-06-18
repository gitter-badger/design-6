// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.d3

import org.scalajs.dom.raw.Element

import scala.scalajs.js

@js.native
trait Selection extends js.Object {
  type T <: js.Object

  def append(tag: String): Selection = js.native // linter:ignore UnusedParameter
  def attr(name: String, value: String): Selection = js.native // linter:ignore UnusedParameter
  def call(axis: Axis): Selection = js.native // linter:ignore UnusedParameter
  def data(data: js.Array[T]): SelectionWithType[T] = js.native // linter:ignore UnusedParameter
  def html(html: String): Selection = js.native // linter:ignore UnusedParameter
  def node(): Element = js.native // linter:ignore UnusedParameter
  def on(event: String, handler: js.Function2[js.Object, Int, Unit]): Selection = js.native // linter:ignore UnusedParameter
  def selectAll(selector: String): Selection = js.native // linter:ignore UnusedParameter
  def style(name: String, value: String): Selection = js.native // linter:ignore UnusedParameter
}

@js.native
trait SelectionWithType[U <: js.Object] extends Selection {
  type T = U

  def attr(name: String, value: js.Function2[U, Int, String]): SelectionWithType[U] = js.native // linter:ignore UnusedParameter
  def call(func: js.Function1[SelectionWithType[U], Unit]): SelectionWithType[U] = js.native // linter:ignore UnusedParameter
  def each(func: js.ThisFunction0[Element, U]): Unit = js.native // linter:ignore UnusedParameter
  def enter(): SelectionWithType[U] = js.native // linter:ignore UnusedParameter
  def text(value: js.Function2[U, Int, String]): SelectionWithType[U] = js.native // linter:ignore UnusedParameter
}
