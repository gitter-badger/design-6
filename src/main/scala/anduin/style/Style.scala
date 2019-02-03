// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// scalastyle:off number.of.methods
class Style(val classes: List[String]) {

  def value: String = classes.mkString(" ").trim

  // Sizing
  def width: Width = Width(classes)
  def maxWidth: MaxWidth = MaxWidth(classes)
  def height: Height = Height(classes)
  def minHeight: MinHeight = MinHeight(classes)

  def active: Active = Active(classes)
  def animation: Animation = Animation(classes)
  def background: Background = Background(classes)
  def backgroundPosition: BackgroundPosition = BackgroundPosition(classes)
  def backgroundSize: BackgroundSize = BackgroundSize(classes)
  def border: Border = Border(classes)
  def borderColor: BorderColor = BorderColor(classes)
  def borderRadius: BorderRadius = BorderRadius(classes)
  def borderStyle: BorderStyle = BorderStyle(classes)
  def borderWidth: BorderWidth = BorderWidth(classes)
  def color: Color = Color(classes)
  def coordinate: Coordinate = Coordinate(classes)
  def cursor: Cursor = Cursor(classes)
  def debugGrid: DebugGrid = DebugGrid(classes)
  def display: Display = Display(classes)
  def disabled: Disabled = Disabled(classes)
  def flexbox: Flexbox = Flexbox(classes)
  def focus: Focus = Focus(classes)
  def fontFamily: FontFamily = FontFamily(classes)
  def fontSize: FontSize = FontSize(classes)
  def fontStyle: FontStyle = FontStyle(classes)
  def fontWeight: FontWeight = FontWeight(classes)
  def hover: Hover = Hover(classes)
  def letterSpacing: LetterSpacing = LetterSpacing(classes)
  def lineHeight: LineHeight = LineHeight(classes)
  // TODO: replace with List component
  def listing: Listing = Listing(classes)
  def margin: Margin = Margin(classes)
  def opacity: Opacity = Opacity(classes)
  def overflow: Overflow = Overflow(classes)
  def padding: Padding = Padding(classes)
  def position: Position = Position(classes)
  def pointerEvents: PointerEvents = PointerEvents(classes)
  def shadow: Shadow = Shadow(classes)
  // TODO: replace with Table component
  def table: Table = Table(classes)
  def textAlign: TextAlign = TextAlign(classes)
  def textTransform: TextTransform = TextTransform(classes)
  // TODO: only usage is Truncate, which should be a component
  def typography: Typography = Typography(classes)
  def transition: Transition = Transition(classes)
  def verticalAlign: VerticalAlign = VerticalAlign(classes)
  def whiteSpace: WhiteSpace = WhiteSpace(classes)
  def wordBreak: WordBreak = WordBreak(classes)
  def zIndex: Zindex = Zindex(classes)

  override def toString: String = value
}
// scalastyle:on number.of.methods

object Style extends Style(classes = List.empty) {
  implicit def convertToTagMod(builder: Style): TagMod = ^.cls := builder.value
}
