package org.hexworks.zircon.examples.fragments.table

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.Table
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.ComponentEventType

/**
 * This example shows the usage of the table fragment.
 */
object TableExample {

    private val theme = ColorThemes.zenburnVanilla()

    private const val WAGE_FORMAT = "%,d $"

    @JvmStatic
    fun main(args: Array<String>) {
        val tableFragment: Table<Person> = buildTable()

        val selectionPanel = buildPanel(tableFragment)

        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withSize(tableFragment.size.withRelativeWidth(selectionPanel.width))
                .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                .withTitle("Table example")
                .build()
        )

        val screen = Screen.create(tileGrid)

        screen.addFragment(tableFragment)
        screen.addComponent(selectionPanel)
        screen.theme = theme
        screen.display()
    }

    private fun buildTable(): Table<Person> =
        Fragments
            .table(50.randomPersons())
            .withHeight(20)
            .withColumnSpacing(1)
            .withRowSpacing(0)
            .withColumns(
                Columns
                    .textColumn("First name", 14, Person::firstName),
                Columns
                    .textColumn("Last name", 14, Person::lastName),
                Columns
                    .textColumn("Age", 3, Person::age),
                Columns
                    .icon("Height", Person::height) { height -> iconFor(height) },
                Columns
                    .textColumn("Wage", 8) { it.wage.bindTransform { wage -> WAGE_FORMAT.format(wage) } }
            )
            .build()

    private fun buildPanel(tableFragment: Table<Person>): VBox {
        return Components
            .vbox()
            .withSize(25, tableFragment.size.height)
            .withSpacing(1)
            .withDecorations(ComponentDecorations.box(BoxType.SINGLE))
            .withPosition(Position.topRightOf(tableFragment.root))
            .build()
            .apply {
                val personObs: ObservableValue<Person> = tableFragment.selectedRowValue
                addComponents(
                    Components
                        .header()
                        .withText("Selected person:")
                        .build(),
                    personObs.asLabel(contentSize.width, Person::firstName),
                    personObs.asLabel(contentSize.width, Person::lastName),
                    heightPanel(contentSize.width, personObs),
                    personObs
                        .asLabel(contentSize.width) { WAGE_FORMAT.format(wage.value) }
                        .apply {
                            personObs.onChange {
                                val bindingPerson = textProperty.updateFrom(personObs.value.wage.bindTransform { wage ->
                                    WAGE_FORMAT.format(wage)
                                })
                                val bindingWage =
                                    textProperty.updateFrom(personObs.value.wage.bindTransform { WAGE_FORMAT.format(it) })
                                personObs.onChange {
                                    bindingPerson.dispose()
                                    bindingWage.dispose()
                                }
                            }
                        },
                    Components
                        .button()
                        .withText("shuffle")
                        .build()
                        .apply {
                            processComponentEvents(ComponentEventType.ACTIVATED) {
                                val newWage = randomWage()
                                val p = personObs.value
                                p.wage.updateValue(newWage)
                            }
                        },
                    Components
                        .horizontalNumberInput(contentSize.width)
                        .withMaxValue(Person.MAX_WAGE)
                        .withMinValue(Person.MIN_WAGE)
                        .build()
                        .apply {
                            currentValue = personObs.value.wage.value
                            currentValueProperty.bindTransform { personObs.value.wage.updateValue(it) }
                        }
                )
            }
    }

    private fun heightPanel(width: Int, person: ObservableValue<Person>): HBox =
        Components
            .hbox()
            .withSpacing(1)
            .withSize(width, 1)
            .build()
            .apply {
                addComponents(
                    Components
                        .icon()
                        .withIcon(person.value.height.icon)
                        .build()
                        .apply { iconProperty.updateFrom(person.bindTransform { it.height.icon }) },
                    Components.
                        label()
                        .withSize(width - 2, 1)
                        .withText(person.value.height.name)
                        .build()
                        .apply { textProperty.updateFrom(person.bindTransform { it.height.name }) }
                )
            }

    private fun <T: Any> ObservableValue<T>.asLabel(width: Int, labelText: T.() -> String): Label =
        Components
            .label()
            .withSize(width, 1)
            .build()
            .apply {
                textProperty.updateFrom(bindTransform(labelText), true)
            }

    private fun iconFor(height: Height): Icon =
            Components
                    .icon()
                    .withIcon(height.icon)
                    .withColorTheme(theme)
                    .build()

    private val Height.icon
        get() = TileBuilder
                .newBuilder()
                .withForegroundColor(when (this) {
                    Height.TALL -> ANSITileColor.BLUE
                    Height.SHORT -> ANSITileColor.RED
                })
                .withBackgroundColor(ANSITileColor.WHITE)
                .withCharacter(
                        when (this) {
                            Height.TALL -> Symbols.TRIANGLE_UP_POINTING_BLACK
                            Height.SHORT -> Symbols.TRIANGLE_DOWN_POINTING_BLACK
                        }
                )
                .buildCharacterTile()
}