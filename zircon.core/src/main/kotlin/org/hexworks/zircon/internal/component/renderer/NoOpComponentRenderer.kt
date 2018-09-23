package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.SubTileGraphics

class NoOpComponentRenderer<T : Component> : ComponentRenderer<T>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<T>) {
        // no-op
    }

}
