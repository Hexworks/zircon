package org.hexworks.zircon.api.component.renderer.impl

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Visibility
import org.hexworks.zircon.api.component.renderer.*
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.renderer.HiddenComponentRenderer

class DefaultComponentRenderingStrategy<T : Component>(
        override val componentRenderer: ComponentRenderer<in T>,
        override val decorationRenderers: List<ComponentDecorationRenderer> = listOf(),
        override val componentPostProcessors: List<ComponentPostProcessor<T>> = listOf()) : ComponentRenderingStrategy<T> {

    private val hiddenRenderer = HiddenComponentRenderer<T>()

    override fun render(component: T, graphics: TileGraphics) {

        if (component.visibilityProperty.value == Visibility.Hidden)
            invisibleRender(component, graphics)
        else {
            var currentOffset = Position.defaultPosition()
            var currentSize = graphics.size
            decorationRenderers.forEach { renderer ->
                val bounds = Rect.create(currentOffset, currentSize)
                renderer.render(graphics.toSubTileGraphics(bounds), ComponentDecorationRenderContext(component))
                currentOffset += renderer.offset
                currentSize -= renderer.occupiedSize
            }

            val componentArea = graphics.toSubTileGraphics(Rect.create(currentOffset, currentSize))

            componentRenderer.render(
                    tileGraphics = componentArea,
                    context = ComponentRenderContext(component))

            componentPostProcessors.forEach { renderer ->
                renderer.render(componentArea, ComponentPostProcessorContext(component))
            }
        }
    }

    private fun invisibleRender(component: T, graphics: TileGraphics) {
        var currentOffset = Position.defaultPosition()
        var currentSize = graphics.size
        decorationRenderers.forEach { renderer ->
            val bounds = Rect.create(currentOffset, currentSize)
            hiddenRenderer.render(graphics.toSubTileGraphics(bounds), ComponentRenderContext(component))
            currentOffset += renderer.offset
            currentSize -= renderer.occupiedSize
        }

        val componentArea = graphics.toSubTileGraphics(Rect.create(currentOffset, currentSize))

        hiddenRenderer.render(
                tileGraphics = componentArea,
                context = ComponentRenderContext(component))
    }


    override fun calculateContentPosition(): Position = decorationRenderers.asSequence().map {
        it.offset
    }.fold(Position.defaultPosition(), Position::plus)

    override fun calculateContentSize(componentSize: Size): Size = componentSize -
            decorationRenderers.asSequence().map {
                it.occupiedSize
            }.fold(Size.zero(), Size::plus)
}
