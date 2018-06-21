// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.d3

import org.scalajs.dom.raw.Element

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("d3-selection", JSImport.Namespace)
@js.native
object D3Selection extends js.Object {
  def select(selector: String): Selection[Element] = js.native // linter:ignore UnusedParameter
}

@js.native
trait BaseDom[D, T <: BaseDom[D, T]] extends js.Object {
  def attr(name: String, value: String): T = js.native // linter:ignore UnusedParameter
  def attr[R](name: String, value: js.Function1[D, R]): T = js.native // linter:ignore UnusedParameter
  def html(value: String): T = js.native // linter:ignore UnusedParameter
  def style(name: String, value: String): T = js.native // linter:ignore UnusedParameter
  def style[R](name: String, value: js.Function1[D, R]): T = js.native // linter:ignore UnusedParameter
  def style[R](name: String, value: js.Function2[D, Int, R]): T = js.native // linter:ignore UnusedParameter
  def text(value: String): T = js.native // linter:ignore UnusedParameter
  def text[R](value: js.Function1[D, R]): T = js.native // linter:ignore UnusedParameter
}

@js.native
trait BaseSelection[D, T <: BaseSelection[D, T]] extends BaseDom[D, T] {
  def append(`type`: String): T = js.native // linter:ignore UnusedParameter

  def data(): js.Array[D] = js.native
  def data[NewDatum <: D](data: js.Array[NewDatum]): Update[NewDatum] = js.native // linter:ignore UnusedParameter
  def data[NewDatum <: D, R](data: js.Array[NewDatum], key: js.Function0[R]): Update[NewDatum] = // linter:ignore UnusedParameter
    js.native
}

@js.native
trait Selection[D] extends BaseSelection[D, Selection[D]] {
  def select[U](selector: String): Selection[U] = js.native // linter:ignore UnusedParameter
  def selectAll[U](selector: String): Selection[U] = js.native // linter:ignore UnusedParameter
}

@js.native
trait Update[D] extends BaseSelection[D, Update[D]] {
  def enter(): Enter[D] = js.native
  def exit(): Selection[D] = js.native
}

@js.native
trait Enter[D] extends js.Object {
  def append(tagName: String): Selection[D] = js.native // linter:ignore UnusedParameter
}
