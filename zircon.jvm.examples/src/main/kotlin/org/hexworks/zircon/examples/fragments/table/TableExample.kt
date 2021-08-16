package org.hexworks.zircon.examples.fragments.table

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.Table
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.*
import org.hexworks.zircon.api.dsl.fragment.buildTable

/**
 * This example shows the usage of the table fragment.
 */
object TableExample {

    private val theme = ColorThemes.zenburnVanilla()
    private val persons = 2.randomPersons().toProperty()

    @JvmStatic
    fun main(args: Array<String>) {


        val tableFragment = buildTable<Person> {
            // TODO: Use an observable list and add UI elements to add/remove elements
            data = persons
            height = 20
            colSpacing = 1
            rowSpacing = 1
            textColumn {
                name = "First name"
                width = 14
                valueProvider = Person::firstName
            }
            textColumn {
                name = "Last name"
                width = 14
                valueProvider = Person::lastName
            }
            numberColumn {
                name = "Age"
                width = 3
                valueProvider = Person::age
            }
            iconColumn {
                name = "Height"
                valueProvider = Person::heightIcon
            }
            observableTextColumn {
                name = "Wage"
                width = 8
                valueProvider = Person::formattedWage
            }
        }

        val selectionPanel = buildSelectionPanel(tableFragment)

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

    /**
     * Builds the right panel displaying the currently selected person.
     */
    private fun buildSelectionPanel(tableFragment: Table<Person>) = buildVbox {

        preferredSize = Size.create(25, tableFragment.size.height)
        spacing = 1
        decoration = box()
        position = Position.topRightOf(tableFragment.root)

        val panelContentSize = contentSize

        val personValue = tableFragment
            .selectedRowsValue
            .bindTransform {
                it.firstOrNull() ?: Person(
                    "None",
                    "Selected",
                    0,
                    Height.SHORT,
                    50000.toProperty()
                )
            }

        header {
            text = "Selected person:"
        }

        withChildren(
            personValue.asLabel(contentSize.width, Person::firstName),
            personValue.asLabel(contentSize.width, Person::lastName),
            heightPanel(contentSize.width, personValue),
        )

        label {
            preferredSize = Size.create(contentSize.width, 1)
            textProperty.updateFrom(personValue.value.formattedWage)
        }

        button {
            +"shuffle"
            onActivated {
                val newWage = randomWage()
                personValue.value.wage.updateValue(newWage)
            }
        }

        horizontalNumberInput {
            preferredSize = Size.create(contentSize.width, 1)
            maxValue = Person.MAX_WAGE
            minValue = Person.MIN_WAGE
        }.apply {
            currentValue = personValue.value.wage.value
            currentValueProperty.bindTransform { personValue.value.wage.updateValue(it) }
        }

        button {
            preferredSize = contentSize.withHeight(1)
            textProperty.updateFrom(personValue.bindTransform { "Delete ${it.firstName} ${it.lastName}" })
            onActivated {
                persons.remove(personValue.value)
            }
        }

        hbox {
            spacing = 1
            preferredSize = panelContentSize.withHeight(1)

            button {
                +"+"
                onActivated {
                    persons.add(randomPerson())
                }
            }

            label { +"random Person" }

            button {
                +"-"
                onActivated {
                    if (persons.isNotEmpty()) {
                        persons.removeAt(persons.lastIndex)
                    }
                }
            }
        }
    }


    private fun heightPanel(width: Int, person: ObservableValue<Person>): HBox =
        Components
            .hbox()
            .withSpacing(1)
            .withPreferredSize(width, 1)
            .build()
            .apply {
                addComponents(
                    Components
                        .icon()
                        .withIcon(person.value.heightIcon)
                        .build()
                        .apply { iconProperty.updateFrom(person.bindTransform { it.heightIcon }) },
                    Components.label()
                        .withPreferredSize(width - 2, 1)
                        .withText(person.value.height.name)
                        .build()
                        .apply { textProperty.updateFrom(person.bindTransform { it.height.name }) }
                )
            }

    private fun <T : Any> ObservableValue<T>.asLabel(width: Int, labelText: T.() -> String) = buildLabel {
        preferredSize = Size.create(width, 1)
        textProperty.updateFrom(bindTransform(labelText), true)
    }

}
