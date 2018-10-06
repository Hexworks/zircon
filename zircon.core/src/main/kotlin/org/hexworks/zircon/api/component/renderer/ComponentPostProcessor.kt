package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * A [ComponentPostProcessor] is responsible for post processing after a component render.
 */
abstract class ComponentPostProcessor<T : Component> : DecorationRenderer<ComponentPostProcessorContext<T>>
