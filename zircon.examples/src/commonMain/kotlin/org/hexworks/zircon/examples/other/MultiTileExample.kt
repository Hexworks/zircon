package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetType
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.examples.TILESETS

/**
 * Components with their own tilesets: every component (and container) can override the
 * tileset it is drawn with, and a child keeps its own override inside a parent's. A
 * container only accepts tilesets of its own cell size, so the fonts here are the CP437
 * sheets that match the selected tileset.
 */
public val multiTileExample: Example = Example("Multi tile") { context ->
    val root = panel { preferredSize = context.contentSize }
    val cell = context.screen.tileset.size
    val fonts = TILESETS
        .filter { it.resource.tilesetType == TilesetType.CP437Tileset && it.resource.size == cell }
        .let { matching -> List(5) { matching[it % matching.size] } }

    root.addComponent(label {
        +fonts[0].name
        position = Position(1, 1)
    }.apply { tileset = fonts[0].resource })

    root.addComponent(label {
        +fonts[1].name
        position = Position(1, 3)
    }.apply { tileset = fonts[1].resource })

    root.addComponent(panel {
        decorations { +box(title = fonts[2].name) }
        position = Position(1, 5)
        preferredSize = Size(22, 2)
    }.apply { tileset = fonts[2].resource })

    val container = panel {
        position = Position(1, 8)
        preferredSize = Size(22, 3)
        decorations { +box(title = fonts[3].name) }
    }
    val inner = label { +fonts[4].name }
    container.addComponent(inner)
    root.addComponent(container)
    container.tileset = fonts[3].resource
    inner.tileset = fonts[4].resource

    ExampleView(root)
}
