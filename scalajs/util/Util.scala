// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.util

import scala.scalajs.js

object Util {

  // This works like `undefOr.toOption` but convert `null` to `None` instead of
  // Some(null). To be specific:
  // A            => Some(A)
  // null         => None
  // js.undefined => None
  def safelyToOption[A](a: js.UndefOr[A]): Option[A] =
    a.toOption.filter(_ != null)
}
