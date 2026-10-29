package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.internal.component.impl.RootContainer

public class RootContainerRenderer : ComponentRenderer<RootContainer> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<RootContainer>) {
        // TODO: make this tile configurable in ZirconDefaults
        drawWindow.fill(Tile.defaultTile())
        drawWindow.applyStyle(context.currentStyle)
    }
}
