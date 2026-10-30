package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentAlignments.alignmentWithin
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** A table built from a VBox of HBoxes. */
public val hBoxVBoxExample: Example = Example("HBox+VBox") { context ->
    val root = context.oneColumn { box ->
        val contentWidth = 46
        val table = vBox {
            preferredSize = Size(contentWidth + 2, 20)
            alignment = alignmentWithin(box, ComponentAlignment.CENTER)
            decorations { +box(BoxType.SINGLE, "Crew") }
        }
        val headerRow = hBox { preferredSize = Size(contentWidth, 1) }
        listOf("#" to 4, "First Name" to 12, "Last Name" to 12, "Job" to 12).forEach { (text, width) ->
            headerRow.addComponent(header { +text; preferredSize = Size(width, 1) })
        }
        table.addComponent(headerRow)
        val crew = listOf(
            listOf("0", "Sam", "Lawrey", "Stoneworker"),
            listOf("1", "Jane", "Fisher", "Woodcutter"),
            listOf("2", "John", "Smith", "Mason"),
            listOf("3", "Steve", "Thrush", "Farmer"),
        )
        crew.forEach { person ->
            val row = hBox { preferredSize = Size(contentWidth, 1) }
            person.forEachIndexed { i, text ->
                row.addComponent(label { +text; preferredSize = Size(if (i == 0) 4 else 12, 1) })
            }
            table.addComponent(row)
        }
        box.addComponent(table)
    }
    ExampleView(root)
}
