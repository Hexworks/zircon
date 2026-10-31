package org.hexworks.zircon.examples.other

import org.hexworks.cobalt.databinding.api.value.NoActionOnBind
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
 * tileset it is drawn with, and a child keeps its own override inside a parent's.
 *
 * Attaching a component copies the parent's tileset (and theme) into it, and a later
 * change of the parent's tileset is copied too. A component built with
 * `bindingAction = NoActionOnBind` keeps its own tileset when attached, and the root here
 * is built with the screen's tileset so attaching the root changes nothing below it.
 * A container only accepts tilesets of its own cell size, so the fonts here are the CP437
 * sheets that match the selected tileset.
 */
public val multiTileExample: Example = Example("Multi tile") { context ->
    val screenTileset = context.screen.tileset
    val theme = context.screen.theme
    val root = panel {
        preferredSize = context.contentSize
        tileset = screenTileset
    }
    val fonts = TILESETS
        .filter { it.resource.tilesetType == TilesetType.CP437Tileset && it.resource.size == screenTileset.size }
        .let { matching -> List(5) { matching[it % matching.size] } }

    root.addComponent(label {
        +fonts[0].name
        position = Position(1, 1)
        tileset = fonts[0].resource
        colorTheme = theme
        bindingAction = NoActionOnBind
    })

    root.addComponent(label {
        +fonts[1].name
        position = Position(1, 3)
        tileset = fonts[1].resource
        colorTheme = theme
        bindingAction = NoActionOnBind
    })

    root.addComponent(panel {
        decorations { +box(title = fonts[2].name) }
        position = Position(1, 5)
        preferredSize = Size(22, 2)
        tileset = fonts[2].resource
        colorTheme = theme
        bindingAction = NoActionOnBind
    })

    val container = panel {
        position = Position(1, 8)
        preferredSize = Size(22, 3)
        decorations { +box(title = fonts[3].name) }
        tileset = fonts[3].resource
        colorTheme = theme
        bindingAction = NoActionOnBind
    }
    container.addComponent(label {
        +fonts[4].name
        tileset = fonts[4].resource
        colorTheme = theme
        bindingAction = NoActionOnBind
    })
    root.addComponent(container)

    ExampleView(root)
}
