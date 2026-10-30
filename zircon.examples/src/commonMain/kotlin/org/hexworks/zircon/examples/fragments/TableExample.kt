package org.hexworks.zircon.examples.fragments

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.horizontalNumberInput
import org.hexworks.zircon.api.builder.component.icon
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.fragment.table
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * A table of people with a selectable row and a panel that edits the selected person;
 * rows can be added and removed.
 */
public val tableExample: Example = Example("Table") { context ->
    val size = context.contentSize
    val persons = randomPersons(15).toProperty()
    val tableWidth = size.width / 3 * 2
    val panelWidth = size.width / 3

    val tableFragment = table<Person> {
        data = persons
        height = size.height

        selectedRowRenderer = ComponentRenderer { tileGraphics, ctx ->
            tileGraphics.fill(Tile.defaultTile())
            tileGraphics.applyStyle(ctx.currentStyle.withBackgroundColor(ctx.theme.accentColor))
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

    val nobody = Person("None", "Selected", 0, Height.SHORT, 50000.toProperty())
    val personValue: ObservableValue<Person> = tableFragment.selectedRowsProperty.bindTransform {
        it.firstOrNull() ?: nobody
    }

    val selectionPanel = vBox {
        preferredSize = Size(panelWidth, size.height)
        spacing = 1
        decorations { +box(title = "Selected") }
    }
    val panelContentWidth = selectionPanel.contentSize.width

    fun ObservableValue<Person>.asLabel(text: Person.() -> String) = label {
        preferredSize = Size(panelContentWidth, 1)
        textProperty.updateFrom(bindTransform(text))
    }

    selectionPanel.addComponent(header { +"Selected person:" })
    selectionPanel.addComponent(personValue.asLabel { firstName })
    selectionPanel.addComponent(personValue.asLabel { lastName })
    selectionPanel.addComponent(hBox {
        spacing = 1
        preferredSize = Size(panelContentWidth, 1)
        icon { iconProperty.updateFrom(personValue.bindTransform { it.heightIcon }) }
        label {
            preferredSize = Size(panelContentWidth - 2, 1)
            textProperty.updateFrom(personValue.bindTransform { it.height.name })
        }
    })

    // The wage label follows both the selection and the selected person's wage.
    val wageText = "".toProperty()
    fun refreshWage() {
        wageText.value = personValue.value.formattedWage.value
    }
    personValue.onChange { refreshWage() }
    refreshWage()
    selectionPanel.addComponent(label {
        preferredSize = Size(panelContentWidth, 1)
        textProperty.updateFrom(wageText)
    })

    val wageInput = horizontalNumberInput {
        preferredSize = Size(panelContentWidth, 1)
        minValue = Person.MIN_WAGE
        maxValue = Person.MAX_WAGE
        initialValue = personValue.value.wage.value
    }
    wageInput.currentValueProperty.onChange { (_, newValue) ->
        if (personValue.value !== nobody) {
            personValue.value.wage.value = newValue
            refreshWage()
        }
    }
    personValue.onChange { (_, person) -> wageInput.currentValue = person.wage.value }

    selectionPanel.addComponent(button {
        +"shuffle"
        onActivated {
            val person = personValue.value
            if (person !== nobody) {
                person.wage.value = randomWage()
                wageInput.currentValue = person.wage.value
                refreshWage()
            }
        }
    })
    selectionPanel.addComponent(wageInput)
    selectionPanel.addComponent(button {
        preferredSize = Size(panelContentWidth, 1)
        textProperty.updateFrom(personValue.bindTransform { "Delete ${it.firstName} ${it.lastName}" })
        onActivated {
            val person = personValue.value
            if (person !== nobody) persons.remove(person)
        }
    })
    selectionPanel.addComponent(hBox {
        spacing = 1
        preferredSize = Size(panelContentWidth, 1)
        button {
            +"+"
            onActivated { persons.add(randomPerson()) }
        }
        label { +"random person" }
        button {
            +"-"
            onActivated { if (persons.isNotEmpty()) persons.removeAt(persons.lastIndex) }
        }
    })

    val root = hBox { preferredSize = size }
    root.addComponent(panel {
        preferredSize = Size(tableWidth, size.height)
    }.apply { addComponent(tableFragment.root) })
    root.addComponent(selectionPanel)
    ExampleView(root)
}
