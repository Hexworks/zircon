package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.builder.component.checkBox
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Two check boxes with bound selection: toggling one toggles the other; changes are counted. */
public val selectionHandlingExample: Example = Example("Selection") { context ->
    val root = panel { preferredSize = context.contentSize }
    val cb0 = checkBox { +"Foo" }
    val cb1 = checkBox { +"Bar"; position = Position(0, 2) }
    val changes = label { +"Selection changed 0 times"; position = Position(0, 4); preferredSize = Size(30, 1) }
    root.addComponent(cb0)
    root.addComponent(cb1)
    root.addComponent(changes)
    var count = 0
    cb0.selectedProperty.onChange {
        count++
        changes.text = "Selection changed $count times"
    }
    cb0.isSelected = true
    cb0.selectedProperty.bind(cb1.selectedProperty)
    ExampleView(root)
}
