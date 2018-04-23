// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.portal

sealed trait Status
case object StatusOpen extends Status
case object StatusClose extends Status
