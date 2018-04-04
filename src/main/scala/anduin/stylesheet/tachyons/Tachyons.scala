// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.stylesheet.tachyons

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

// scalastyle:off number.of.methods
class Tachyons(val classes: List[String]) {

  def value: String = classes.mkString(" ").trim

  def active: Active = Active(classes)
  def backgroundColor: BackgroundColor = BackgroundColor(classes)
  def backgroundPosition: BackgroundPosition = BackgroundPosition(classes)
  def backgroundSize: BackgroundSize = BackgroundSize(classes)
  def border: Border = Border(classes)
  def borderColor: BorderColor = BorderColor(classes)
  def borderRadius: BorderRadius = BorderRadius(classes)
  def borderStyle: BorderStyle = BorderStyle(classes)
  def borderWidth: BorderWidth = BorderWidth(classes)
  def boxSizing: BoxSizing = BoxSizing(classes)
  def clear: Clear = Clear(classes)
  def code: Code = Code(classes)
  def color: Color = Color(classes)
  def coordinate: Coordinate = Coordinate(classes)
  def cursor: Cursor = Cursor(classes)
  def debugGrid: DebugGrid = DebugGrid(classes)
  def display: Display = Display(classes)
  def flexbox: Flexbox = Flexbox(classes)
  def float: Float = Float(classes)
  def focus: Focus = Focus(classes)
  def fontFamily: FontFamily = FontFamily(classes)
  def fontSize: FontSize = FontSize(classes)
  def fontStyle: FontStyle = FontStyle(classes)
  def fontWeight: FontWeight = FontWeight(classes)
  def form: Form = Form(classes)
  def height: Height = Height(classes)
  def hover: Hover = Hover(classes)
  def letterSpacing: LetterSpacing = LetterSpacing(classes)
  def lineHeight: LineHeight = LineHeight(classes)
  def link: Link = Link(classes)
  def listing: Listing = Listing(classes)
  def margin: Margin = Margin(classes)
  def maxWidth: MaxWidth = MaxWidth(classes)
  def negativeMargin: NegativeMargin = NegativeMargin(classes)
  def opacity: Opacity = Opacity(classes)
  def outline: Outline = Outline(classes)
  def overflow: Overflow = Overflow(classes)
  def padding: Padding = Padding(classes)
  def position: Position = Position(classes)
  def shadow: Shadow = Shadow(classes)
  def table: Table = Table(classes)
  def textAlign: TextAlign = TextAlign(classes)
  def textDecoration: TextDecoration = TextDecoration(classes)
  def textTransform: TextTransform = TextTransform(classes)
  def transformRotate: TransformRotate = TransformRotate(classes)
  def typography: Typography = Typography(classes)
  def utilities: Utilities = Utilities(classes)
  def verticalAlign: VerticalAlign = VerticalAlign(classes)
  def visibility: Visibility = Visibility(classes)
  def whiteSpace: WhiteSpace = WhiteSpace(classes)
  def width: Width = Width(classes)
  def wordBreak: WordBreak = WordBreak(classes)
  def zIndex: Zindex = Zindex(classes)

  override def toString: String = value
}
// scalastyle:on number.of.methods

object Tachyons extends Tachyons(classes = List.empty) {
  implicit def convertToTagMod(builder: Tachyons): TagMod = ^.cls := builder.value
}
