// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.avatar

import scala.util.hashing.MurmurHash3

object AvatarColor {

  private val GoldenRatioConjugate = 0.618033988749895

  def apply(id: String): String = {
    // Taken from http://martin.ankerl.com/2009/12/09/how-to-create-random-colors-programmatically/
    val hash = MurmurHash3.stringHash(id) * GoldenRatioConjugate
    val (h, s, l) = ((hash - Math.floor(hash)) * 360, 0.45 * 100, 0.55 * 100)
    s"hsl($h,$s%,$l%)"
  }
}
