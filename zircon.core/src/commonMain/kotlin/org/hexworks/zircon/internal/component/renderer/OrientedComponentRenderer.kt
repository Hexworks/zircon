package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.data.Orientation
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.DrawWindow

/**
 * One renderer for a component kind that comes in two orientations (sliders, number inputs):
 * draws with [horizontal] or [vertical] after the component's own
 * [orientation][org.hexworks.zircon.api.component.ComponentProperties.orientation], so a theme
 * entry can cover both builders.
 */
@Suppress("UNCHECKED_CAST")
internal class OrientedComponentRenderer<T : Component>(
    private val horizontal: ComponentRenderer<out T>,
    private val vertical: ComponentRenderer<out T>,
) : ComponentRenderer<T> {

    override val rendersOnEveryFrame: Boolean
        get() = horizontal.rendersOnEveryFrame || vertical.rendersOnEveryFrame

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<T>) {
        val renderer = if (context.component.orientation == Orientation.VERTICAL) vertical else horizontal
        (renderer as ComponentRenderer<T>).render(drawWindow, context)
    }
}
