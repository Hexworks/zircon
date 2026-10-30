package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.colorTheme
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.logArea
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.builder.base.withComponentRenderer
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toCharacterTileString
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** A label drawn by a custom renderer, and one with its own theme, inside a log area. */
public val customRenderExample: Example = Example("Custom render") { context ->
    val root = context.oneColumn { box ->
        val logArea = logArea {
            preferredSize = box.size
            decorations { +box() }
        }
        val colored = label {
            preferredSize = Size(10, 1)
            withComponentRenderer { graphics, _ ->
                graphics.draw(
                    "Colored".toCharacterTileString(styleSet {
                        backgroundColor = DefaultAnsiPalette[ANSIColor.RED]
                        foregroundColor = DefaultAnsiPalette[ANSIColor.GREEN]
                    })
                )
            }
        }
        logArea.addInlineRow(listOf(label { +"This is a " }, colored, label { +" Label." }))
        val leftLabel = label {
            +"Nightmare"
            colorTheme = colorTheme {
                accentColor = DefaultAnsiPalette[ANSIColor.BRIGHT_YELLOW]
                primaryForegroundColor = DefaultAnsiPalette[ANSIColor.RED]
                primaryBackgroundColor = DefaultAnsiPalette[ANSIColor.GREEN]
                secondaryForegroundColor = DefaultAnsiPalette[ANSIColor.BLUE]
                secondaryBackgroundColor = DefaultAnsiPalette[ANSIColor.CYAN]
            }
        }
        logArea.addRow(leftLabel, applyTheme = false)
        box.addComponent(logArea)
    }
    ExampleView(root)
}
