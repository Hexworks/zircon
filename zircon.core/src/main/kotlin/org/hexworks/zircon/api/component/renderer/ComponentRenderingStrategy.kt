package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * Strategy for applying [DecorationRenderer]s for
 * [org.hexworks.zircon.api.component.Component]s.
 */
interface ComponentRenderingStrategy<T : Component> {

    val decorationRenderers: List<ComponentDecorationRenderer>
    val componentRenderer: ComponentRenderer<T>

    fun render(component: T, graphics: TileGraphics)

    fun effectivePosition(): Position = decorationRenderers.asSequence().map {
        it.offset()
    }.fold(Position.defaultPosition(), Position::plus)

    fun effectiveSize(componentSize: Size): Size = componentSize -
            decorationRenderers.asSequence().map {
                it.occupiedSize()
            }.fold(Size.zero(), Size::plus)
}
