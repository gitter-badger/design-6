// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

// - These are common functionality for some Portal consumer (e.g. Modal,
//   Popover) but should not be inside PortalWrapper (whose main focus is state
//   management).
// - Think of these like plugins for Portal. Currently we have:
//   1. Closable (only interface)
object PortalUtils {

  // isClosable ===

  final case class isClosable(onEsc: Boolean, onOutsideClick: Boolean)

  val defaultIsClosable =
    Some(PortalUtils.isClosable(onEsc = true, onOutsideClick = true))

  // ===
}
