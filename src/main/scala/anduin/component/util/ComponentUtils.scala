// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

object ComponentUtils {

  def name(obj: Object): String = {
    obj.getClass.getSimpleName.split('$').last
  }
}
