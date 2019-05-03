// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.dom

import scala.scalajs.js

import org.scalajs.dom.raw.Node

/*
I can't figure out how to walk though a given node and apply the filter.
The following code can be compiled without any problems, but it throws an error on runtime:
`Class extends value function NodeFilter() { [native code] } is not a constructor or null`

```
object TextNodeFilter extends NodeFilter {
  override def acceptNode(node: Node): Int = {
    if (node.nodeType == Node.TEXT_NODE) {
      NodeFilter.FILTER_ACCEPT
    } else {
      NodeFilter.FILTER_SKIP
    }
  }
}

document.createTreeWalker(
  root = doc,
  whatToShow = NodeFilter.SHOW_ELEMENT,
  filter = TextNodeFilter,
  entityReferenceExpansion = true
)
```

The workaround is to create an alias to associated DOM objects.
 */
trait NodeFilterAlias extends js.Object {
  def acceptNode(n: Node): Int
}
