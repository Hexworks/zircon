package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.behavior.Additive
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessor
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessorContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position.Companion.ZERO
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.extensions.toDrawWindow
import kotlin.reflect.KProperty1

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

    override val contentPosition: Position = decorationRenderers.foldPositionBy(ComponentDecorationRenderer::offset)

    override fun render(component: T, graphics: TileGraphics) {
        if (component.isHidden.not()) {
            var currentOffset = ZERO
            var currentSize = graphics.size


            val componentArea = graphics.toDrawWindow(
                Boundable.create(
                    position = decorationRenderers.foldPositionBy(ComponentDecorationRenderer::offset),
                    size = graphics.size - decorationRenderers
                        .foldSizeBy(ComponentDecorationRenderer::occupiedSize)
                )
            )

            componentRenderer.render(
                drawWindow = componentArea,
                context = ComponentRenderContext(component)
            )

            decorationRenderers.forEach { renderer ->
                val bounds = Boundable.create(currentOffset, currentSize)
                renderer.render(graphics.toDrawWindow(bounds), ComponentDecorationRenderContext(component))
                currentOffset += renderer.offset
                currentSize -= renderer.occupiedSize
            }

            componentPostProcessors.forEach { renderer ->
                renderer.render(componentArea, ComponentPostProcessorContext(component))
            }
        }
    }

    override fun calculateContentSize(componentSize: Size): Size =
        componentSize - decorationRenderers.foldSizeBy(ComponentDecorationRenderer::occupiedSize)


    private fun Iterable<ComponentDecorationRenderer>.foldPositionBy(
        prop: KProperty1<ComponentDecorationRenderer, Position>,
    ) = foldBy(ZERO, prop)

    private fun Iterable<ComponentDecorationRenderer>.foldSizeBy(
        prop: KProperty1<ComponentDecorationRenderer, Size>,
    ) = foldBy(Size.ZERO, prop)

    private fun <T : Additive<T>> Iterable<ComponentDecorationRenderer>.foldBy(
        zero: T,
        prop: KProperty1<ComponentDecorationRenderer, T>,
    ) = this.map(prop).fold(zero) { additive, other -> additive.plus(other) }
}
