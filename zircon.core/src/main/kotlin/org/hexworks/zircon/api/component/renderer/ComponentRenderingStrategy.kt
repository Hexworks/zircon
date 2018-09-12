package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * Strategy for applying [DecorationRenderer]s for
 * [org.hexworks.zircon.api.component.Component]s.
 */
interface ComponentRenderingStrategy<T : Component> {

    val decorationRenderers: List<ComponentDecorationRenderer>
    val componentRenderer: ComponentRenderer<T>

    fun apply(component: T, graphics: TileGraphics)
}
