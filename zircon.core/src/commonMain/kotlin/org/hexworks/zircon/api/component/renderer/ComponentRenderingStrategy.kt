package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * Strategy for applying decorations and rendering [org.hexworks.zircon.api.component.Component]s.
 * The typical steps for rendering are:
 * - Creating a [org.hexworks.zircon.api.graphics.impl.DrawWindow] over the
 *   [org.hexworks.zircon.api.graphics.DrawSurface] of the component.
 * - Applying decorations by using [DecorationRenderer]s.
 * - Applying post-processing by using [ComponentPostProcessor]s.
 *
 * A component consists of the content area and decorations.
 * Use [contentPosition] to determine where the content area starts
 *
 * 📘 Note that you won't see [org.hexworks.cobalt.databinding.api.property.Property] values
 * here. This is because once a renderer is created, it is immutable, you can't add or
 * remove decorations. This was a deliberate decision to ensure that the rendering process
 * is predictable and consistent.
 */
interface ComponentRenderingStrategy<T : Component> {

    /**
     * The [Position] where the content of the rendered [Component] starts
     * relative to the top left corner of the component. In other words,
     * the content position is the sum of the offset positions for each
     * decoration.
     */
    val contentPosition: Position

    /**
     * Renders the given [component] onto the given [graphics].
     */
    fun render(component: T, graphics: TileGraphics)

    /**
     * Calculates the [Size] of the content area of the [Component] which is
     * rendered. In other words, the content size is the total size of
     * the component minus the size of the decorations.
     */
    fun calculateContentSize(componentSize: Size): Size
}
