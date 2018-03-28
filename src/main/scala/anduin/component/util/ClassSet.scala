// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.util

object ClassSet {
  def apply(classes: (String, Boolean)*): String = {
    classes.filter(_._2).map(_._1).mkString(" ")
  }
}
