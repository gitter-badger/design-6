package anduin.guide.app.router

import anduin.guide.app.main.layout.Layout
import anduin.guide.app.main.Pages._
import anduin.guide.pages.brand.logo.PageLogo
import anduin.guide.pages.components.card.PageCard
import anduin.guide.pages.components.checkbox.PageCheckbox
import anduin.guide.pages.components.radio.PageRadio
import anduin.guide.pages.components.dropdown.PageDropdown
import anduin.guide.pages.components.dialog.PageDialog
import anduin.guide.pages.components.button._
import anduin.guide.pages.components.field.PageField
import anduin.guide.pages.components.home.PageComponent
import anduin.guide.pages.components.icon._
import anduin.guide.pages.components.progressindicator.PageProgressIndicator
import anduin.guide.pages.components.modal.PageModal
import anduin.guide.pages.components.portal.PagePortal
import anduin.guide.pages.components.popover.PagePopover
import anduin.guide.pages.components.stepper.PageStepper
import anduin.guide.pages.components.suggest.PageSuggest
import anduin.guide.pages.components.tab.PageTab
import anduin.guide.pages.components.tag.PageTag
import anduin.guide.pages.components.table.PageTable
import anduin.guide.pages.components.textbox._
import anduin.guide.pages.components.toggle.PageToggle
import anduin.guide.pages.components.tooltip.PageTooltip
import anduin.guide.pages.components.well.PageWell
import anduin.guide.pages.components.playground.PagePlayground
import anduin.guide.pages.style._
import anduin.guide.pages.home.PageHome
import anduin.guide.pages.wip.PageWIP
import japgolly.scalajs.react.extra.router.{Redirect, RouterConfig, RouterConfigDsl}
import japgolly.scalajs.react.vdom.VdomElement

object Router {

  private type RenderFn = Ctl => VdomElement

  val config: RouterConfig[Page] = RouterConfigDsl[Page].buildConfig { dsl =>
    import dsl._

    val hash = string("(#.*|)$")

    // format: off
    (trimSlashes
      | staticRoute(root, Home) ~> renderR(PageHome.render)
      // Style
      | dynamicRouteCT("style" ~ hash.caseClass[Style]) ~> renderR(PageStyle.render)
      | dynamicRouteCT("logo" ~ hash.caseClass[Logo]) ~> renderR(PageLogo.render)
      | dynamicRouteCT("color" ~ hash.caseClass[Color]) ~> renderR(PageColor.render)
      | dynamicRouteCT("layout" ~ hash.caseClass[Layout]) ~> renderR(PageLayout.render)
      | dynamicRouteCT("typography" ~ hash.caseClass[Typography]) ~> renderR(PageTypography.render)
      // Component
      | dynamicRouteCT("playground" ~ hash.caseClass[Playground]) ~> renderR(PagePlayground.render)
      | dynamicRouteCT("avatar" ~ hash.caseClass[Avatar]) ~> renderR(PageWIP.render)
      | dynamicRouteCT("button" ~ hash.caseClass[Button]) ~> renderR(PageButton.render)
      | dynamicRouteCT("button-box" ~ hash.caseClass[ButtonBox]) ~> renderR(PageButtonBox.render)
      | dynamicRouteCT("card" ~ hash.caseClass[Card]) ~> renderR(PageCard.render)
      | dynamicRouteCT("checkbox" ~ hash.caseClass[Checkbox]) ~> renderR(PageCheckbox.render)
      | dynamicRouteCT("component" ~ hash.caseClass[Component]) ~> renderR(PageComponent.render)
      | dynamicRouteCT("date-time" ~ hash.caseClass[DateTime]) ~> renderR(PageWIP.render)
      | dynamicRouteCT("dialog" ~ hash.caseClass[Dialog]) ~> renderR(PageDialog.render)
      | dynamicRouteCT("dropdown" ~ hash.caseClass[Dropdown]) ~> renderR(PageDropdown.render)
      | dynamicRouteCT("field" ~ hash.caseClass[Field]) ~> renderR(PageField.render)
      | dynamicRouteCT("filter" ~ hash.caseClass[Filter]) ~> renderR(PageWIP.render)
      | dynamicRouteCT("icon" ~ hash.caseClass[Icon]) ~> renderR(PageIcon.render)
      | dynamicRouteCT("icon-file" ~ hash.caseClass[IconFile]) ~> renderR(PageIconFile.render)
      | dynamicRouteCT("icon-folder" ~ hash.caseClass[IconFolder]) ~> renderR(PageIconFolder.render)
      | dynamicRouteCT("icon-glyph" ~ hash.caseClass[IconGlyph]) ~> renderR(PageIconGlyph.render)
      | dynamicRouteCT("icon-negotiation" ~ hash.caseClass[IconNego]) ~> renderR(PageIconNego.render)
      | dynamicRouteCT("icon-product" ~ hash.caseClass[IconProduct]) ~> renderR(PageIconProduct.render)
      | dynamicRouteCT("menu" ~ hash.caseClass[Menu]) ~> renderR(PageWIP.render)
      | dynamicRouteCT("modal" ~ hash.caseClass[Modal]) ~> renderR(PageModal.render)
      | dynamicRouteCT("multi-dropdown" ~ hash.caseClass[DropdownMulti]) ~> renderR(PageWIP.render)
      | dynamicRouteCT("multi-suggest" ~ hash.caseClass[SuggestMulti]) ~> renderR(PageWIP.render)
      | dynamicRouteCT("portal" ~ hash.caseClass[Portal]) ~> renderR(PagePortal.render)
      | dynamicRouteCT("popover" ~ hash.caseClass[Popover]) ~> renderR(PagePopover.render)
      | dynamicRouteCT("progress-indicator" ~ hash.caseClass[Progress]) ~> renderR(PageProgressIndicator.render)
      | dynamicRouteCT("radio" ~ hash.caseClass[Radio]) ~> renderR(PageRadio.render)
      | dynamicRouteCT("stepper" ~ hash.caseClass[Stepper]) ~> renderR(PageStepper.render)
      | dynamicRouteCT("suggest" ~ hash.caseClass[Suggest]) ~> renderR(PageSuggest.render)
      | dynamicRouteCT("tab" ~ hash.caseClass[Tab]) ~> renderR(PageTab.render)
      | dynamicRouteCT("table" ~ hash.caseClass[Table]) ~> renderR(PageTable.render)
      | dynamicRouteCT("tag" ~ hash.caseClass[Tag]) ~> renderR(PageTag.render)
      | dynamicRouteCT("text-box" ~ hash.caseClass[TextBox]) ~> renderR(PageTextBox.render)
      | dynamicRouteCT("text-box-value" ~ hash.caseClass[TextBoxValue]) ~> renderR(PageTextBoxValue.render)
      | dynamicRouteCT("text-box-appearance" ~ hash.caseClass[TextBoxAppearance]) ~> renderR(PageTextBoxAppearance.render)
      | dynamicRouteCT("toast" ~ hash.caseClass[Toast]) ~> renderR(PageWIP.render)
      | dynamicRouteCT("toggle" ~ hash.caseClass[Toggle]) ~> renderR(PageToggle.render)
      | dynamicRouteCT("tooltip" ~ hash.caseClass[Tooltip]) ~> renderR(PageTooltip.render)
      | dynamicRouteCT("tree" ~ hash.caseClass[Tree]) ~> renderR(PageWIP.render)
      | dynamicRouteCT("well" ~ hash.caseClass[Well]) ~> renderR(PageWell.render)
      | emptyRule)
      .notFound(redirectToPage(Home)(Redirect.Replace))
      .renderWith(Layout.render)
    // format: on
  }
}
