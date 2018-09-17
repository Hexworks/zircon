package org.hexworks.zircon.api.component.renderer.impl

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.*
import org.hexworks.zircon.api.data.Bounds
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.TileGraphics

class DefaultComponentRenderingStrategy<T : Component>(
        override val decorationRenderers: List<ComponentDecorationRenderer>,
        override val componentRenderer: ComponentRenderer<T>) : ComponentRenderingStrategy<T> {

    override fun render(component: T, graphics: TileGraphics) {
        var currentOffset = Position.defaultPosition()
        var currentSize = graphics.size()
        decorationRenderers.forEach { renderer ->
            val bounds = Bounds.create(currentOffset, currentSize)
            renderer.render(graphics.toSubTileGraphics(bounds), ComponentDecorationRenderContext(component))
            currentOffset += renderer.offset()
            currentSize -= renderer.occupiedSize()
        }

        componentRenderer.render(
                tileGraphics = graphics.toSubTileGraphics(Bounds.create(currentOffset, currentSize)),
                context = ComponentRenderContext(component))

    }
}
