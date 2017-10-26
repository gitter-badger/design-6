// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.util

object ComponentUtils {

  def name(obj: Object): String = {
    obj.getClass.getSimpleName.split('$').last
  }
}
