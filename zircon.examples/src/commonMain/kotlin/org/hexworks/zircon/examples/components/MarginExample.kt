package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/** Boxed panels inside undecorated panels of the same size, with growing margins. */
public val marginExample: Example = Example("Margins") { context ->
    val panelWidth = 26
    val panelHeight = 8
    val root = vBox {
        preferredSize = context.contentSize
        spacing = 1
        header { +"Panels with margin" }
        panel {
            preferredSize = Size(panelWidth, panelHeight)
            componentRenderer = NoOpComponentRenderer()
            panel {
                preferredSize = Size(panelWidth, panelHeight)
                decorations { +box(title = "Margin 1"); +margin(1) }
            }
        }
        panel {
            preferredSize = Size(panelWidth, panelHeight)
            componentRenderer = NoOpComponentRenderer()
            panel {
                preferredSize = Size(panelWidth, panelHeight)
                decorations { +box(title = "Margin 1, 2"); +margin(1, 2) }
            }
        }
        panel {
            preferredSize = Size(panelWidth, panelHeight)
            componentRenderer = NoOpComponentRenderer()
            panel {
                preferredSize = Size(panelWidth, panelHeight)
                decorations { +box(title = "Margin 0, 1, 2, 3"); +margin(0, 1, 2, 3) }
            }
        }
    }
    ExampleView(root)
}
