package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessor
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessorContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics

class DefaultComponentRenderingStrategy<T : Component>(
    /**
     * The [ComponentRenderer] which will be used to render
     * the *content* of this [Component].
     */
    val componentRenderer: ComponentRenderer<in T>,
    /**
     * The [ComponentDecorationRenderer]s this [Component] has.
     */
    val decorationRenderers: List<ComponentDecorationRenderer> = listOf(),
    /**
     * The [ComponentPostProcessor]s this [Component] has.
     */
    val componentPostProcessors: List<ComponentPostProcessor<T>> = listOf()
) : ComponentRenderingStrategy<T> {

    override val contentPosition: Position = decorationRenderers.asSequence()
        .map {
            it.offset
        }.fold(Position.defaultPosition(), Position::plus)

    override fun render(component: T, graphics: TileGraphics) {
        if (component.isHidden.not()) {
            var currentOffset = Position.defaultPosition()
            var currentSize = graphics.size

            val graphicsCopy = graphics.createCopy()

            val componentArea = graphicsCopy.toSubTileGraphics(
                Rect.create(
                    position = decorationRenderers
                        .map { it.offset }.fold(Position.zero(), Position::plus),
                    size = graphicsCopy.size - decorationRenderers
                        .map { it.occupiedSize }.fold(Size.zero(), Size::plus)
                )
            )

            componentRenderer.render(
                tileGraphics = componentArea,
                context = ComponentRenderContext(component)
            )

            decorationRenderers.forEach { renderer ->
                val bounds = Rect.create(currentOffset, currentSize)
                renderer.render(graphicsCopy.toSubTileGraphics(bounds), ComponentDecorationRenderContext(component))
                currentOffset += renderer.offset
                currentSize -= renderer.occupiedSize
            }

            componentPostProcessors.forEach { renderer ->
                renderer.render(componentArea, ComponentPostProcessorContext(component))
            }

            graphics.transform { position, _ ->
                graphicsCopy.getTileAtOrNull(position) ?: Tile.empty()
            }
        }
    }

    override fun calculateContentSize(componentSize: Size): Size = componentSize -
            decorationRenderers.asSequence().map {
                it.occupiedSize
            }.fold(Size.zero(), Size::plus)

}
