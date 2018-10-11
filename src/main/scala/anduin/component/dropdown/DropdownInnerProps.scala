// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.dropdown

import anduin.scalajs.downshift.DownshiftRenderProps

private[dropdown] case class DropdownInnerProps[A](
  outer: Dropdown[A]#Props,
  downshift: DownshiftRenderProps[A],
  measurement: Dropdown.Measurement[A]
)
