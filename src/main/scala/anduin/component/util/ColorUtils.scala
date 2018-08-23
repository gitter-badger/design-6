// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

object ColorUtils {

  def hsvToRgb(hsv: (Double, Double, Double)): (Int, Int, Int) = {
    val hue = if (hsv._1 == 0) {
      1
    } else if (hsv._1 == 360) {
      359
    } else {
      hsv._1
    }
    val h = hue / 360
    val s = hsv._2 / 100
    val v = hsv._3 / 100

    val i = Math.floor(h * 6)
    val f = h * 6 - i
    val p = v * (1 - s)
    val q = v * (1 - f * s)
    val t = v * (1 - (1 - f) * s)

    val (r, g, b) = i % 6 match {
      case 0 => (v, t, p)
      case 1 => (q, v, p)
      case 2 => (p, v, t)
      case 3 => (p, q, v)
      case 4 => (t, p, v)
      case 5 => (v, p, q)
      case _ => ??? // impossible
    }

    val R = Math.round(r * 255).toInt
    val G = Math.round(g * 255).toInt
    val B = Math.round(b * 255).toInt

    (R, G, B)
  }
}
