package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * A [ComponentRenderer] is responsible for rendering a component's contents onto a [TileGraphics].
 * It is a specialization of the [DecorationRenderer] as the mechanism is the same, but the
 * [ComponentRenderer] will draw the innermost part of the component (its content).
 */
fun interface ComponentRenderer<T : Component> : DecorationRenderer<ComponentRenderContext<T>>
