package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * Strategy for applying decorations to and rendering
 * [org.hexworks.zircon.api.component.Component]s.
 */
interface ComponentRenderingStrategy<T : Component> {

    /**
     * The [ComponentDecorationRenderer]s this [Component] has.
     */
    val decorationRenderers: List<ComponentDecorationRenderer>
    /**
     * The [ComponentRenderer] which will be used to render
     * the *content* of this [Component].
     */
    val componentRenderer: ComponentRenderer<T>
    /**
     * The [ComponentPostProcessor]s this [Component] has.
     */
    val componentPostProcessors: List<ComponentPostProcessor<T>>

    /**
     * Renders the given `component` onto the given `graphics`.
     */
    fun render(component: T, graphics: TileGraphics)

    /**
     * The [Position] where the content of the rendered [Component] starts
     * relative to the top left corner of the component. In other words
     * the content position is the sum of the offset positions for each
     * decoration.
     */
    fun calculateContentPosition(): Position = decorationRenderers.asSequence().map {
        it.offset
    }.fold(Position.defaultPosition(), Position::plus)

    /**
     * Calculates the [Size] of the content of the [Component] which is
     * rendered. In other words the content size is the total size of
     * the component minus the size of the decorations.
     */
    fun calculateContentSize(componentSize: Size): Size = componentSize -
            decorationRenderers.asSequence().map {
                it.occupiedSize
            }.fold(Size.zero(), Size::plus)
}
