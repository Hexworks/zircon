package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.logArea
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** A log area: plain rows, a typing effect, inline components and a styled paragraph. */
public val logAreaExample: Example = Example("Log area") { context ->
    val root = vBox { preferredSize = context.contentSize }
    val width = context.contentSize.width - 2
    val logArea = logArea {
        decorations { +box(BoxType.DOUBLE, "Log") }
        preferredSize = Size(context.contentSize.width, context.contentSize.height - 1)
    }
    logArea.addRow(paragraph { +"This is a simple log row"; preferredSize = Size(width, 1) })
    logArea.addRow(paragraph {
        +"This is a log row with a typing effect"
        preferredSize = Size(width, 1)
        typingEffectSpeedInMs = 200
    })
    logArea.addNewRows(2)
    logArea.addInlineRow(listOf(label { +"This is a log row with a " }, button { +"Button" }))
    logArea.addNewRows(2)
    logArea.addRow(paragraph {
        +"This is a long log row, which gets wrapped, since it is long"
        preferredSize = Size(30, 3)
    })
    logArea.addNewRows(1)
    logArea.addRow(paragraph {
        +"This is a long log row, which gets wrapped, since it is long with a different style"
        preferredSize = Size(30, 4)
        componentStyleSet = componentStyleSet {
            defaultStyle = styleSet {
                foregroundColor = DefaultAnsiPalette[ANSIColor.YELLOW]
                backgroundColor = context.screen.theme.primaryBackgroundColor
            }
        }
    })
    root.addComponent(logArea)
    ExampleView(root)
}
