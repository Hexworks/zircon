package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics

class ComponentRendererStub<T : Component>(
        private val actualRenderer: ComponentRenderer<T> = NoOpGenericRenderer()) : ComponentRenderer<T> {

    val renderings = mutableListOf<Pair<TileGraphics, ComponentRenderContext<T>>>()

    fun renderedOnce() = renderings.size == 1

    fun clear() = renderings.clear()

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<T>) {
        renderings.add(tileGraphics to context)
        actualRenderer.render(tileGraphics, context)
    }
}
