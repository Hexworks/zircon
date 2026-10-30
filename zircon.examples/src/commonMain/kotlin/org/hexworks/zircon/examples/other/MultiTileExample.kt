package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * Components with their own tilesets: every component (and container) can override the
 * tileset it is drawn with, and a child keeps its own override inside a parent's.
 */
public val multiTileExample: Example = Example("Multi tile") { context ->
    val root = panel { preferredSize = context.contentSize }

    root.addComponent(label {
        +"Rexpaint 20x20"
        position = Position(1, 1)
    }.apply { tileset = CP437TilesetResources.rexPaint20x20() })

    root.addComponent(label {
        +"Yobbo 20x20"
        position = Position(1, 3)
    }.apply { tileset = CP437TilesetResources.yobbo20x20() })

    root.addComponent(panel {
        decorations { +box(title = "Anikki 20x20") }
        position = Position(1, 5)
        preferredSize = Size(17, 2)
    }.apply { tileset = CP437TilesetResources.anikki20x20() })

    val container = panel {
        position = Position(1, 8)
        preferredSize = Size(17, 3)
        decorations { +box(title = "Bisasam 20x20") }
    }
    val inner = label { +"Oreslam 20x20" }
    container.addComponent(inner)
    root.addComponent(container)
    container.tileset = CP437TilesetResources.bisasam20x20()
    inner.tileset = CP437TilesetResources.oreslam20x20()

    ExampleView(root)
}
