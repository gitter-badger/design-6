// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.avatar
import scala.util.hashing.MurmurHash3

import anduin.component.util.ColorUtils

private[avatar] object AvatarColor {

  private val GoldenRatioConjugate = 0.618033988749895

  def apply(id: String): String = {
    // Taken from http://martin.ankerl.com/2009/12/09/how-to-create-random-colors-programmatically/

    val hash = MurmurHash3.stringHash(id) * GoldenRatioConjugate
    val h = (hash - Math.floor(hash)) * 360

    // Note: the values for s and v are chosen to be 0.3 and 0.7 based on a color palette that we like
    val s = 0.3 * 100
    val v = 0.7 * 100

    val hsv = (h, s, v)
    val (r, g, b) = ColorUtils.hsvToRgb(hsv)
    s"rgb($r,$g,$b)"
  }
}
