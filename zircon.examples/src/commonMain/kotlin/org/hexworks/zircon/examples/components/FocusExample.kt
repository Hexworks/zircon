package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.textArea
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/** Buttons that give focus to a text area and take it away again. */
public val focusExample: Example = Example("Focus") { context ->
    val root = panel {
        preferredSize = context.contentSize
        componentRenderer = NoOpComponentRenderer()
    }
    val textArea = textArea {
        text = "Test focusing on me\nby clicking the\nbuttons."
        preferredSize = Size(20, 5)
        position = Position(2, 2)
    }
    val giveFocus = button {
        +"Give focus"
        position = Position(24, 2)
    }
    val takeFocus = button {
        +"Take focus"
        position = Position(24, 4)
    }
    root.addComponent(textArea)
    root.addComponent(giveFocus)
    root.addComponent(takeFocus)
    giveFocus.onActivated { textArea.requestFocus() }
    takeFocus.onActivated { textArea.clearFocus() }
    ExampleView(root)
}
