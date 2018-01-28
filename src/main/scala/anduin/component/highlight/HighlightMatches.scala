// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.highlight

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class HighlightMatches(
  text: String,
  highlightedIndexes: Set[Int] = Set.empty,
  classes: String = "",
  matchedClasses: String = "",
  unmatchedClasses: String = ""
) {

  def apply(keyOpt: Option[String] = None): ScalaComponent.Unmounted[_, _, _] = {
    keyOpt.fold(HighlightMatches.component(this))(
      HighlightMatches.component.withKey(_)(this)
    )
  }
}

object HighlightMatches {

  private type Props = HighlightMatches
  private val ComponentName = getClass.getName

  private val component = ScalaComponent
    .builder[Props](ComponentName)
    .stateless
    .render_P { props =>
      <.div(
        ^.cls := props.classes,
        TagMod.fromTraversableOnce {
          props.text.zipWithIndex.map {
            case (char, index) =>
              val matched = props.highlightedIndexes.contains(index)
              <.span(
                ^.classSet(
                  props.matchedClasses -> matched,
                  props.unmatchedClasses -> !matched
                ),
                char.toString
              )
          }
        }
      )
    }
    .build
}
