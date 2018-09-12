package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * A [ComponentRenderer] is responsible for rendering a component's contents onto a [TileGraphics].
 */
abstract class ComponentRenderer<T : Component> : DecorationRenderer<ComponentRenderContext<T>>
