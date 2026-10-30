package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** A button with its own component style set: cyan at rest, red when focused. */
public val styleSetsExample: Example = Example("Style sets") { context ->
    val root = context.oneColumn { box ->
        val panel = panel {
            decorations {
                +box(title = "Buttons on panel")
                +shadow()
            }
            preferredSize = Size(30, 20)
            position = Position(5, 5)
        }
        box.addComponent(panel)
        val button = button {
            +"Button"
            decorations { +side() }
            position = Position(1, 3)
        }
        panel.addComponent(button)
        button.componentStyleSet = componentStyleSet {
            defaultStyle = styleSet {
                foregroundColor = DefaultAnsiPalette[ANSIColor.CYAN]
                backgroundColor = DefaultAnsiPalette[ANSIColor.BLACK]
            }
            activeStyle = styleSet {
                foregroundColor = DefaultAnsiPalette[ANSIColor.CYAN]
                backgroundColor = DefaultAnsiPalette[ANSIColor.BLACK]
            }
            focusedStyle = styleSet {
                foregroundColor = DefaultAnsiPalette[ANSIColor.RED]
                backgroundColor = DefaultAnsiPalette[ANSIColor.BLACK]
            }
        }
    }
    ExampleView(root)
}
