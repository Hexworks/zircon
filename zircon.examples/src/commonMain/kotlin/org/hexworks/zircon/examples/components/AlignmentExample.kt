package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentAlignments.alignmentAround
import org.hexworks.zircon.api.ComponentAlignments.alignmentWithin
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.ComponentAlignment.BOTTOM_CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.BOTTOM_LEFT
import org.hexworks.zircon.api.component.ComponentAlignment.BOTTOM_RIGHT
import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.LEFT_CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.RIGHT_CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.TOP_CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.TOP_LEFT
import org.hexworks.zircon.api.component.ComponentAlignment.TOP_RIGHT
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/** Labels aligned within a panel, around it, and within the content area. */
public val alignmentExample: Example = Example("Alignment") { context ->
    val root = panel {
        preferredSize = context.contentSize
        componentRenderer = NoOpComponentRenderer()
    }
    val panel = panel {
        decorations {
            +box(title = "Component alignment test")
            +shadow()
        }
        preferredSize = Size(30, 15)
        alignment = alignmentWithin(root, CENTER)
    }
    root.addComponent(panel)
    val eight = listOf(TOP_LEFT, TOP_CENTER, TOP_RIGHT, RIGHT_CENTER, BOTTOM_RIGHT, BOTTOM_CENTER, BOTTOM_LEFT, LEFT_CENTER)
    fun ComponentAlignment.short() = name.split('_').joinToString("") { it.take(1) }
    // Within the panel.
    (eight + CENTER).forEach { align ->
        panel.addComponent(label {
            +(if (align == CENTER) "CENTER" else align.short())
            alignment = alignmentWithin(panel as Container, align)
        })
    }
    // Around the panel.
    eight.forEach { align ->
        root.addComponent(label {
            +align.short()
            alignment = alignmentAround(panel, align)
        })
    }
    // Within the content area.
    eight.forEach { align ->
        root.addComponent(label {
            +align.short()
            alignment = alignmentWithin(root, align)
        })
    }
    ExampleView(root)
}
