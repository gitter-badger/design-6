// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.d3

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("d3-shape", JSImport.Namespace)
@js.native
object D3Shape extends js.Object {
  def pie(): PieGenerator = js.native
  def arc(): ArcGenerator = js.native
}

@js.native
trait ArcDatum extends js.Object

@js.native
trait PieArcDatum[D] extends ArcDatum {
  def data: D = js.native
}

@js.native
trait BaseGenerator[G <: BaseGenerator[G]] extends js.Object

@js.native
trait BaseArcGenerator[G <: BaseArcGenerator[G]] extends js.Object with BaseGenerator[G] {
  def cornerRadius(r: Double): G = js.native
  def innerRadius(r: Double): G = js.native
  def outerRadius(r: Double): G = js.native
}

@js.native
trait ArcGenerator extends BaseArcGenerator[ArcGenerator] {
  def apply[T <: ArcDatum](arguments: T): String = js.native
  def centroid[T <: ArcDatum](arguments: T): js.Tuple2[Double, Double] = js.native
}

@js.native
trait PieGenerator extends js.Object {
  def value[T](func: js.Function1[T, Double]): PieGenerator = js.native
  def apply[D](data: js.Array[D]): js.Array[PieArcDatum[D]] = js.native
}
