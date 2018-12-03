// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.progressindicators.placeholder

import japgolly.scalajs.react.vdom.{SvgAttrAndStyles => sa, SvgTags => st}

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class ListLoader(props: PlaceholderProps) {
  // scalastyle:off multiple.string.literals
  def apply(): VdomElement = PlaceholderLoader(props)(
    st.rect(
      sa.x := "0",
      sa.y := "0",
      sa.rx := "3",
      sa.ry := "3",
      sa.width := "250",
      sa.height := "10"
    ),
    st.rect(
      sa.x := "20",
      sa.y := "20",
      sa.rx := "3",
      sa.ry := "3",
      sa.width := "220",
      sa.height := "10"
    ),
    st.rect(
      sa.x := "20",
      sa.y := "40",
      sa.rx := "3",
      sa.ry := "3",
      sa.width := "170",
      sa.height := "10"
    ),
    st.rect(
      sa.x := "0",
      sa.y := "60",
      sa.rx := "3",
      sa.ry := "3",
      sa.width := "250",
      sa.height := "10"
    ),
    st.rect(
      sa.x := "20",
      sa.y := "80",
      sa.rx := "3",
      sa.ry := "3",
      sa.width := "200",
      sa.height := "10"
    ),
    st.rect(
      sa.x := "20",
      sa.y := "100",
      sa.rx := "3",
      sa.ry := "3",
      sa.width := "80",
      sa.height := "10"
    )
  )
  // scalastyle:on multiple.string.literals
}
