package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.listItem
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/**
 * Text laid out as a box: a header, paragraphs, list items and an inline button, built
 * from a vertical box of text components (the old text box builder is a `vBox` now).
 */
public val textBoxesExample: Example = Example("Text boxes") { context ->
    val columnSize = Size(context.contentSize.width / 2, context.contentSize.height)
    val root = hBox { preferredSize = context.contentSize; componentRenderer = NoOpComponentRenderer() }
    val left = vBox { preferredSize = columnSize; componentRenderer = NoOpComponentRenderer(); spacing = 1 }
    val right = vBox {
        preferredSize = columnSize
        spacing = 1
        decorations { +box(title = "Text boxes on panel") }
    }
    root.addComponent(left)
    root.addComponent(right)
    listOf(left, right).forEach { column ->
        column.addComponent(vBox {
            preferredSize = Size(26, 12)
            header { +"Header!" }
            paragraph { +"This is a plain text box."; preferredSize = Size(26, 1) }
            listItem { +"This is a list item" }
            listItem { +"And another list item" }
            hBox {
                preferredSize = Size(26, 1)
                label { +"Inline text " }
                button { +"Button" }
                label { +" text" }
            }
            paragraph {
                +"And a multi-line paragraph with typewriter effect..."
                preferredSize = Size(26, 3)
                typingEffectSpeedInMs = 200
            }
        })
        column.addComponent(vBox {
            preferredSize = Size(26, 8)
            decorations { +box(); +shadow() }
            header { +"Decorated!" }
            paragraph { +"This is a paragraph which won't fit on one line."; preferredSize = Size(23, 3) }
        })
    }
    ExampleView(root)
}
