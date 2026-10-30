package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/** Panels with every box type and decoration, a disabled one, and nested panels. */
public val panelsExample: Example = Example("Panels") { context ->
    val root = panel { preferredSize = context.contentSize }
    root.addComponent(panel {
        decorations { +box(title = "No Render") }
        preferredSize = Size(18, 5)
        componentRenderer = NoOpComponentRenderer()
        alignment = positionalAlignment(1, 1)
    })
    root.addComponent(panel {
        decorations { +shadow() }
        preferredSize = Size(18, 5)
        alignment = positionalAlignment(1, 8)
    })
    root.addComponent(panel {
        decorations { +box(); +shadow() }
        preferredSize = Size(18, 5)
        alignment = positionalAlignment(1, 15)
    })
    root.addComponent(panel {
        decorations { +box(boxType = BoxType.DOUBLE) }
        preferredSize = Size(18, 5)
        alignment = positionalAlignment(1, 22)
    })
    root.addComponent(panel {
        decorations { +box(boxType = BoxType.BASIC) }
        preferredSize = Size(18, 5)
        alignment = positionalAlignment(21, 1)
    })
    val disabledPanel = panel {
        decorations { +box(title = "Disabled") }
        preferredSize = Size(18, 5)
        alignment = positionalAlignment(21, 8)
    }
    root.addComponent(disabledPanel)
    disabledPanel.isDisabled = true
    root.addComponent(panel {
        decorations { +box(boxType = BoxType.SINGLE); +shadow() }
        preferredSize = Size(18, 5)
        alignment = positionalAlignment(21, 15)
    })
    root.addComponent(panel {
        preferredSize = Size(18, 5)
        decorations { +box(title = "Wombat", boxType = BoxType.TOP_BOTTOM_DOUBLE) }
        alignment = positionalAlignment(21, 22)
    })
    root.addComponent(panel {
        preferredSize = Size(18, 5)
        decorations { +box(boxType = BoxType.LEFT_RIGHT_DOUBLE) }
        alignment = positionalAlignment(41, 1)
    })
    val parent = panel {
        preferredSize = Size(18, 19)
        decorations { +box(title = "Parent") }
        alignment = positionalAlignment(41, 8)
    }
    root.addComponent(parent)
    val nested0 = panel {
        preferredSize = Size(14, 15)
        alignment = positionalAlignment(1, 1)
        decorations { +box(title = "Nested 0", boxType = BoxType.DOUBLE) }
    }
    val nested1 = panel {
        preferredSize = Size(10, 11)
        alignment = positionalAlignment(1, 1)
        decorations { +box(title = "Nested 1", boxType = BoxType.DOUBLE) }
    }
    parent.addComponent(nested0)
    nested0.addComponent(nested1)
    ExampleView(root)
}
