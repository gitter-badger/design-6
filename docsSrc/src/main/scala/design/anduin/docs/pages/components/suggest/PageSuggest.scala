package design.anduin.docs.pages.components.suggest

import java.time.{ZoneId, ZonedDateTime}

import design.anduin.components.input.suggest.{MultiSuggest, Suggest}
import design.anduin.components.tag.Tag
import design.anduin.docs.app.main.Pages
import design.anduin.docs.components._
import anduin.mcro.Source
import japgolly.scalajs.react.vdom.html_<^._

import scala.collection.JavaConverters._

object PageSuggest {

  private case class Contact(name: String, email: String)

  private object Contact {
    //noinspection TypeAnnotation
    val TypedSuggest = (new Suggest[Contact])()
    //noinspection TypeAnnotation
    val TypedMultiSuggest = (new MultiSuggest[Contact])()
    val sampleItems = List(
      Contact("Ada Banks", "ada.banks.cceo@gmail.com"),
      Contact("Angelino Sansone", "angelino.sansone.cs@gmail.com"),
      Contact("Daiki Rikuto", "daiki.rikuto.cs@gmail.com"),
      Contact("David Walton", "david.walton.ni@gmail.com"),
      Contact("Eric Miller", "eric.miller.ni@gmail.com"),
      Contact("Harry Williams", "hary.williams.ic@gmail.com"),
      Contact("Jenny Yoo", "jenny.yoo.bm@gmail.com"),
      Contact("John Mcregor", "john.mcregor.icfo@gmail.com"),
      Contact("Jonathin Sundin", "jonathin.sundin.bm@gmail.com"),
      Contact("Logan Smith", "logan.smith.igp@gmail.com"),
      Contact("Vincent Harris", "vincent.harris.ccfo@gmail.com"),
      Contact("Virginia Lee", "virginia.lee.cc@gmail.com")
    )
  }

  private case class Zone(name: String, offset: String)

  private object Zone {
    //noinspection TypeAnnotation
    val TypedSuggest = (new Suggest[Zone])()
    val sampleItems: List[Zone] = ZoneId.getAvailableZoneIds.asScala
      .map { idString =>
        Zone(
          name = idString.replace("_", " "),
          offset = ZonedDateTime.now(ZoneId.of(idString)).getOffset.toString
        )
      }
      .toList
      .sortBy(_.offset)
  }

  def render(ctl: Pages.Ctl): VdomElement = {
    <.div(
      Header("Suggest (WIP)", Some(Suggest))(),
      Toc(headings = Source.getTocHeadings)(),
      Markdown(
        """
          |>::warning::**WIP material**
          |>
          |>This page is not completed. It exists mainly to assist the early
          |>development of Suggest and MultiSuggest, such as testing and API
          |>discussion.
          |
          |There are actually 2 components in this package:
          |[Suggest](#suggest) and [MultiSuggest](#multisuggest).
        """.stripMargin
      )(),
      Markdown(
        """
          |# Suggest
          |
          |Suggest is simply a text box with suggestions being shown when
          |users type on it. It is the equivalent of using the native
          |[`datalist`] element with a text `input`.
          |
          |[`datalist`]: https://developer.mozilla.org/en-US/docs/Web/HTML/Element/datalist
          |
          |In fact, it is _very_ similar to the TextBox component. Both expect
          |a `value: String` and an `onChange: String => Callback` handler to
          |get and set its value. The only difference is that Suggest also
          |expects an `opts: Suggest.Opts` prop to provide its options (and
          |related info, such as how to render those options and how to filter
          |them):
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str(
          render = (value, onChange) => {
            Contact.TypedSuggest(
              value = value,
              onChange = onChange,
              opts = Suggest.Opts[Contact](
                // e.g. Contact("Ada Banks", "ada.banks.cceo@gmail.com")
                items = Contact.sampleItems.map(c => Suggest.Opt(c)),
                render = c => s"${c.name} (${c.email})",
                getFilterString = c => s"${c.name}${c.email}",
                getValueString = _.email
              )
            )()
          },
          initialValue = ""
        )()
      }))(),
      Markdown(
        """
          |Note that the most important difference between this and the
          |Dropdown component is that Suggest is a free-style text-based input
          |component (e.g. users can input something that is not from the
          |option list), while Dropdown is a select component (i.e. users can
          |only choose from the option list).
        """.stripMargin
      )(),
      Markdown(
        """
          |# MultiSuggest
          |
          |MultiSuggest is a component that allows users to input multiple
          |strings, using Suggest as the input field. It works with
          |`Seq[String]`, or in other words, it expects `value: Seq[String]`
          |and `onChange: Seq[String] => Callback`)
          |
          |It allows users to:
          |- Add an item (a string) via a Suggest
          |- Edit or remove an added item
          |- Paste a list of items, both in "adding" and "editing" (try this:
          |`ada.banks.cceo@gmail.com,alice@foo.com,,alice.com`)
          |
          |It allows engineers to:
          |- Customize the render of the selected item (`valueToSelectedItem`).
          |For example, in the below input:
          |  - Invalid emails (e.g. `alice.com`) are shown in red
          |  - Known contacts (e.g. `ada.banks.cceo@gmail.com`) are shown in
          |  gray
          |  - Unknown contacts (e.g. `john@foo.com`) are shown in orange.
          |- Set the keys that should trigger the "submit" of an inputting
          |value (`selectKeys` prop)
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.SeqStr(
          render = (value, onChange) => {
            Contact.TypedMultiSuggest(
              value = value,
              onChange = onChange,
              opts = Suggest.Opts[Contact](
                // e.g. Contact("Ada Banks", "ada.banks.cceo@gmail.com")
                items = Contact.sampleItems.map(c => Suggest.Opt(c)),
                render = c => s"${c.name} (${c.email})",
                getFilterString = c => s"${c.name}${c.email}",
                getValueString = _.email
              ),
              selectKeys = List(",", ";", " ", "Enter"),
              valueToSelectedItem = string => {
                val contact = Contact.sampleItems.find(_.email == string)
                MultiSuggest.SelectedItem(
                  label = contact.fold(string)(_.name),
                  color =
                    if (contact.isDefined) Tag.Light.Gray
                    else if (string.contains("@")) Tag.Light.Warning
                    else Tag.Light.Danger
                )
              }
            )()
          },
          initialValue = List.empty[String]
        )()
      }))(),
      Markdown(
        """
          |Note that MultiSuggest is a pretty generic component, in the mean
          |that it won't do any validation (such as de-duplication) when
          |users input a value. It's up to the engineers to handle the input
          |strings intelligently. Things like "de-duplication" or "toggling
          |instead of adding" or "prevent adding invalid values" are easy to
          |(and should be) implement at consumers' land.
        """.stripMargin
      )(),
      Markdown(
        """
          |# Performance Test
          |
          |The following Suggest is seeded with ~500 timezones as its options.
          |It is, however, expected that the Suggest should suggest 20 options
          |at most to avoid unnecessary renders.
          |
          |Note that this is purely for testing purpose. In practice please
          |choose better way to ask for timezone.
        """.stripMargin
      )(),
      ExampleRich(Source.annotate({
        DemoState.Str(
          render = (value, onChange) => {
            Zone.TypedSuggest(
              opts = Suggest.Opts[Zone](
                // e.g. Contact("Ada Banks", "ada.banks.cceo@gmail.com")
                items = Zone.sampleItems.map(z => Suggest.Opt(z)),
                render = z => s"${z.offset} ${z.name}",
                getFilterString = z => s"${z.offset}${z.name}",
                getValueString = _.name
              ),
              value = value,
              onChange = onChange
            )()
          },
          initialValue = ""
        )()
      }))()
    )
  }
}
