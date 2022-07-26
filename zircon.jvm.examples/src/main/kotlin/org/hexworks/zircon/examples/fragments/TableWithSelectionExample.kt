package org.hexworks.zircon.examples.fragments

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.dsl.component.*
import org.hexworks.zircon.api.dsl.fragment.buildTable
import org.hexworks.zircon.api.fragment.Table
import org.hexworks.zircon.examples.base.OneColumnComponentExample
import org.hexworks.zircon.examples.fragments.table.*
import org.hexworks.zircon.examples.fragments.table.randomWage

object TableWithSelectionExample : OneColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        TableWithSelectionExample.show("Table with selection")
    }

    override fun build(box: VBox) {

        val persons = 15.randomPersons().toProperty()
        val tableWidth = box.width / 3 * 2
        val panelWidth = box.width / 3

        val tableFragment = buildTable<Person> {
            // TODO: Use an observable list and add UI elements to add/remove elements
            data = persons
            height = box.height

            selectedRowRenderer = ComponentRenderer { tileGraphics, context ->
                val style = context.currentStyle
                tileGraphics.fill(Tile.defaultTile())
                tileGraphics.applyStyle(style.withBackgroundColor(context.theme.accentColor))
            }

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

        val selectionPanel = buildSelectionPanel(persons, tableFragment, panelWidth)

        box.addComponent(buildHbox {
            preferredSize = box.contentSize
            withChildren(buildPanel {
                preferredSize = Size.create(tableWidth, box.height)
                withChildren(tableFragment.root)
            }, selectionPanel)
        })
    }

    /**
     * Builds the right panel displaying the currently selected person.
     */
    private fun buildSelectionPanel(
        persons: ListProperty<Person>,
        tableFragment: Table<Person>,
        width: Int
    ) = buildVbox {

        preferredSize = Size.create(width, tableFragment.size.height)
        spacing = 1
        decoration = box()

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


    private fun heightPanel(width: Int, person: ObservableValue<Person>): HBox = buildHbox {
        spacing = 1
        preferredSize = Size.create(width, 1)

        icon {
            iconProperty.updateFrom(person.bindTransform { it.heightIcon })
        }

        label {
            preferredSize = Size.create(width - 2, 1)
            textProperty.updateFrom(person.bindTransform { it.height.name })
        }

    }

    private fun <T : Any> ObservableValue<T>.asLabel(width: Int, labelText: T.() -> String) = buildLabel {
        preferredSize = Size.create(width, 1)
        textProperty.updateFrom(bindTransform(labelText), true)
    }

}
