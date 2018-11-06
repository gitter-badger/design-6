// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.reactimagecrop

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

class Crop(
  val x: Double,
  val y: Double,
  @JSName("width")
  val widthPercent: Double,
  @JSName("height")
  val heightPercent: Double
) extends js.Object
