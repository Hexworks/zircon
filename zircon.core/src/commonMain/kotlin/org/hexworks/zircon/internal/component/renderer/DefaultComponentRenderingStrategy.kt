package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.*
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
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


            val componentArea = graphics.toDrawWindow(
                Rect.create(
                    position = decorationRenderers
                        .map { it.offset }.fold(Position.zero(), Position::plus),
                    size = graphics.size - decorationRenderers
                        .map { it.occupiedSize }.fold(Size.zero(), Size::plus)
                )
            )

            componentRenderer.render(
                drawWindow = componentArea,
                context = ComponentRenderContext(component)
            )

            decorationRenderers.forEach { renderer ->
                val bounds = Rect.create(currentOffset, currentSize)
                renderer.render(graphics.toDrawWindow(bounds), ComponentDecorationRenderContext(component))
                currentOffset += renderer.offset
                currentSize -= renderer.occupiedSize
            }

            componentPostProcessors.forEach { renderer ->
                renderer.render(componentArea, ComponentPostProcessorContext(component))
            }
        }
    }

    override fun calculateContentSize(componentSize: Size): Size = componentSize -
            decorationRenderers.asSequence().map {
                it.occupiedSize
            }.fold(Size.zero(), Size::plus)

}
