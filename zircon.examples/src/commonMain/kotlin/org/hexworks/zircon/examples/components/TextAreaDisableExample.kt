package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.textArea
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** A text area that a button disables and enables again. */
public val textAreaDisableExample: Example = Example("Disabled text") { context ->
    val root = panel { preferredSize = context.contentSize }
    val textArea = textArea {
        text = "Enabled"
        position = Position(2, 2)
        preferredSize = Size(10, 3)
    }
    val toggleButton = button { +"Toggle TextArea"; position = Position(14, 2) }
    toggleButton.onActivated {
        if (textArea.isDisabled) {
            textArea.isDisabled = false
            textArea.text = "Enabled"
        } else {
            textArea.isDisabled = true
            textArea.text = "Disabled"
        }
    }
    root.addComponent(textArea)
    root.addComponent(toggleButton)
    ExampleView(root)
}
