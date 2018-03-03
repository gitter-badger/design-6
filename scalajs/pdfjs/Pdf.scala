// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.pdfjs

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}
import scala.scalajs.js.typedarray.ArrayBuffer
import scala.scalajs.js.{UndefOr, |}

import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.raw.{HTMLCanvasElement, HTMLElement}

import anduin.scalajs.moment.{Date, Moment}

// scalastyle:off number.of.types

object PdfWorkerUrl {
  // The path to PDF parsing worker
  def pdfWorkerUrl(productionBuild: Boolean): String =
    if (productionBuild) {
      "/web/gondor/js/pdfjs/pdf-2.0.402.worker.min.js"
    } else {
      "/web/gondor/js/pdfjs/pdf-2.0.402.worker.js"
    }
}

@JSImport("pdfjs-dist", "PDFJS", "PDFJS")
@js.native
object PDFJS extends js.Object {
  val GlobalWorkerOptions: GlobalWorkerOptions = js.native
  var disableWorker: Boolean = js.native // scalastyle:ignore var.field
  var PasswordResponses: PDFPasswordResponses = js.native // scalastyle:ignore var.field
  val AnnotationLayer: PDFAnnotationLayer = js.native
  def getDocument(url: String | ArrayBuffer): PDFDocumentLoadingTask = // linter:ignore UnusedParameter
    js.native
  def getPage(number: Double): PDFPromise[PDFPageProxy] = js.native // linter:ignore UnusedParameter

  def renderTextLayer(renderParameters: TextLayerRenderParameters): TextLayerRenderTask = // linter:ignore UnusedParameter
    js.native
}

@js.native
trait GlobalWorkerOptions extends js.Object {
  var workerSrc: String = js.native // scalastyle:ignore var.field
}

@js.native
trait PDFProgressData extends js.Object {
  val loaded: Double = js.native
  val total: Double = js.native
}

object PDFProgressData {
  def apply(loaded: Double, total: Double): PDFProgressData =
    js.Dynamic.literal(loaded = loaded, total = total).asInstanceOf[PDFProgressData]
}

@js.native
trait PDFPasswordResponses extends js.Object {
  @JSName("NEED_PASSWORD")
  val Required: Int = js.native

  @JSName("INCORRECT_PASSWORD")
  val Incorrect: Int = js.native
}

@js.native
trait PDFDocumentLoadingTask extends js.Object {
  val promise: PDFPromise[PDFDocumentProxy] = js.native
  var onProgress: js.Function1[PDFProgressData, _] = js.native // scalastyle:ignore var.field

  // The first param is a function which accepts new password
  // The second param is the response which can be either `PDFJS.PasswordResponses.Required` or `PDFJS.PasswordResponses.Incorrect`
  var onPassword // scalastyle:ignore var.field
    : js.Function2[js.Function1[String, Unit], Int, _] = js.native

  def destroy(): PDFPromise[PDFDocumentProxy] = js.native
}

@js.native
trait PDFPromise[T] extends js.Object {
  def resolve(value: T): PDFPromise[T] = js.native // linter:ignore UnusedParameter
  def reject(reason: String): Unit = js.native // linter:ignore UnusedParameter

  // Because "then" is a reserved keyword, we need to name the method as `then` or use @JSGlobal
  @JSName("then")
  def andThen(onResolve: js.Function1[T, _], onReject: js.Function1[js.Any, _] = ???): PDFPromise[T] = // linter:ignore UnusedParameter
    js.native
}

@js.native
trait PDFDocumentProxy extends js.Object {
  val numPages: Double = js.native
  def getPage(number: Double): PDFPromise[PDFPageProxy] = js.native // linter:ignore UnusedParameter
  def getMetadata(): PDFPromise[Metadata] = js.native
  def getDownloadInfo(): PDFPromise[DownloadInfo] = js.native
}

@js.native
trait DownloadInfo extends js.Object {
  val length: Double = js.native
}

@js.native
trait PDFPageProxy extends js.Object {
  def getViewport(scale: Double, rotate: Double = ???): PDFPageViewport = // linter:ignore UnusedParameter
    js.native
  def render(params: PDFRenderParams): PDFRenderTask = js.native // linter:ignore UnusedParameter
  def getTextContent(params: getTextContentParameters): PDFPromise[TextContent] = // linter:ignore UnusedParameter
    js.native
  def cleanup(): Unit = js.native
  def getAnnotations(params: GetAnnotationsParameters): PDFPromise[js.Array[Annotation]] = // linter:ignore UnusedParameter
    js.native
}

@js.native
trait getTextContentParameters extends js.Object

object getTextContentParameters {
  def apply(normalizeWhitespace: Boolean = false): getTextContentParameters = {
    js.Dynamic
      .literal(normalizeWhitespace = normalizeWhitespace)
      .asInstanceOf[getTextContentParameters]
  }
}

@js.native
trait ViewPortCloneParams extends js.Object {
  val dontFlip: Boolean = js.native
}

object ViewPortCloneParams {
  def apply(dontFlip: Boolean): ViewPortCloneParams = {
    js.Dynamic.literal(dontFlip = dontFlip).asInstanceOf[ViewPortCloneParams]
  }
}

@js.native
trait PDFPageViewport extends js.Object {
  var width: Double = js.native // scalastyle:ignore var.field
  var height: Double = js.native // scalastyle:ignore var.field
  def clone(params: ViewPortCloneParams): PDFPageViewport = // linter:ignore UnusedParameter
    js.native
}

@js.native
trait PDFRenderParams extends js.Object {
  val canvasContext: CanvasRenderingContext2D = js.native
  val viewport: PDFPageViewport = js.native
}

object PDFRenderParams {
  def apply(canvasContext: CanvasRenderingContext2D, viewport: PDFPageViewport): PDFRenderParams = {
    js.Dynamic
      .literal(canvasContext = canvasContext, viewport = viewport)
      .asInstanceOf[PDFRenderParams]
  }

  def apply(canvas: HTMLCanvasElement, viewport: PDFPageViewport): PDFRenderParams = {
    val canvasContext = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    this(canvasContext, viewport)
  }
}

@js.native
trait PDFRenderTask extends PDFPromise[PDFPageProxy] {
  def cancel(): Unit = js.native
  val promise: PDFPromise[js.Any] = js.native
}

@js.native
trait TextContent extends js.Object {
  val items: js.Array[TextItem] = js.native
}

@js.native
trait TextItem extends js.Object {
  val str: String = js.native
  val dir: String = js.native // Can be "ttb", "ltr" or "rtl"
}

@js.native
trait TextLayerRenderParameters extends js.Object {
  val textContent: TextContent = js.native
  val container: HTMLElement = js.native
  val viewport: PDFPageViewport = js.native
  val textDivs: js.Array[HTMLElement] = js.native
}

object TextLayerRenderParameters {
  def apply(
    textContent: TextContent,
    container: HTMLElement,
    viewport: PDFPageViewport,
    textDivs: js.Array[HTMLElement] = js.Array[HTMLElement]()
  ): TextLayerRenderParameters = {
    js.Dynamic
      .literal(textContent = textContent, container = container, viewport = viewport, textDivs = textDivs)
      .asInstanceOf[TextLayerRenderParameters]
  }
}

@js.native
trait TextLayerRenderTask extends js.Object {
  def cancel(): Unit = js.native
  val promise: PDFPromise[js.Any] = js.native
}

@js.native
trait Annotation extends js.Object

@js.native
trait GetAnnotationsParameters extends js.Object {
  // Can be `display` or `print`
  val intent: String = js.native
}

object GetAnnotationsParameters {
  def apply(intent: String): GetAnnotationsParameters = {
    js.Dynamic.literal(intent = intent).asInstanceOf[GetAnnotationsParameters]
  }
}

@js.native
trait LinkService extends js.Object {
  def getDestinationHash(destination: js.Any): String = js.native // linter:ignore UnusedParameter
  def navigateTo(destination: js.Any): Unit = js.native // linter:ignore UnusedParameter
  def getAnchorUrl(anchor: String): String = js.native // linter:ignore UnusedParameter
  def executeNamedAction(action: String): Unit = js.native // linter:ignore UnusedParameter
  def getPage(): Int = js.native
  def setPage(page: Int): Unit = js.native // linter:ignore UnusedParameter
  def getRotation(): Int = js.native
  def setRotation(rotation: Int): Unit = js.native // linter:ignore UnusedParameter
  def setHash(hash: String): Unit = js.native // linter:ignore UnusedParameter
  def onFileAttachmentAnnotation(options: FileAttachmentAnnotationOptions): Unit = // linter:ignore UnusedParameter
    js.native
  def cachePageRef(pageNum: Int, pageRef: js.Object): Unit = // linter:ignore UnusedParameter
    js.native
}

@js.native
trait FileAttachmentAnnotationOptions extends js.Object {
  val id: String = js.native
  val filename: String = js.native
  val content: String = js.native
}

// See https://github.com/mozilla/pdf.js/blob/master/web/pdf_link_service.js
// We can't use @ScalaJSDefined annotation (and extend from `LinkService`) here because it's deprecated
// See https://www.scala-js.org/doc/interoperability/sjs-defined-js-classes.html
object SimpleLinkService {
  def apply(): LinkService = {
    js.Dynamic
      .literal(
        getPage = () => 0,
        setPage = (_: Int) => (),
        getRotation = () => 0,
        setRotation = (_: Int) => (),
        navigateTo = (_: js.Any) => (),
        getDestinationHash = (_: js.Any) => "#",
        getAnchorUrl = (_: String) => "#",
        setHash = (_: String) => (),
        executeNamedAction = (_: String) => (),
        onFileAttachmentAnnotation = (_: FileAttachmentAnnotationOptions) => (),
        cachePageRef = (_: Int, _: js.Object) => ()
      )
      .asInstanceOf[LinkService]
  }
}

@js.native
trait AnnotationLayerParameters extends js.Object {
  val viewport: PDFPageViewport = js.native
  val div: HTMLElement = js.native
  val page: PDFPageProxy = js.native
  val annotations: js.Array[Annotation] = js.native
  val linkService: LinkService = js.native
}

object AnnotationLayerParameters {
  def apply(
    viewport: PDFPageViewport,
    div: HTMLElement,
    page: PDFPageProxy,
    annotations: js.Array[Annotation],
    linkService: LinkService = SimpleLinkService()
  ): AnnotationLayerParameters = {
    js.Dynamic
      .literal(viewport = viewport, div = div, page = page, annotations = annotations, linkService = linkService)
      .asInstanceOf[AnnotationLayerParameters]
  }
}

@js.native
trait PDFAnnotationLayer extends js.Object {
  def render(parameters: AnnotationLayerParameters): Unit = // linter:ignore UnusedParameter
    js.native
  def update(parameters: AnnotationLayerParameters): Unit = // linter:ignore UnusedParameter
    js.native
}

@js.native
trait MetadataProperty extends js.Object {
  val Title: UndefOr[String] = js.native
  val Author: UndefOr[String] = js.native
  val Subject: UndefOr[String] = js.native
  val Keywords: UndefOr[String] = js.native
  val CreationDate: UndefOr[String] = js.native
  val ModDate: UndefOr[String] = js.native
  val Creator: UndefOr[String] = js.native
  val Producer: UndefOr[String] = js.native
  val PDFFormatVersion: UndefOr[String] = js.native
}

object MetadataProperty {
  private val DateFormat = "YYYYMMDDHHmmssZZ"

  /**
    * Parse creation/modification date. Note that the format of this string could be different based on machine.
    * Example:
    * - On Mac:        "D:20120706033648+04'00'"
    * - On Mac:        "D:20120625100810-05'00'"
    * - On our server: "D:20161116075241Z'"
    */
  def parseDate(date: String): Option[Date] = {
    if (date.isEmpty) {
      None
    } else {
      // Remove the D: prefix if it is available
      val dateWithoutPrefix = if (date.substring(0, 2) == "D:") date.substring(2) else date
      // Remove all character `'` if applicable
      val dateToParse = dateWithoutPrefix.replace("'", "")

      try {
        Some(Moment(dateToParse, DateFormat))
      } catch {
        case _: Throwable => None
      }
    }
  }
}

@js.native
trait Metadata extends js.Object {
  val info: MetadataProperty = js.native
}
// scalastyle:on number.of.types
