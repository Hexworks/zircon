package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component

/**
 * A [ComponentPostProcessor] is responsible for post processing after a component render.
 */
interface ComponentPostProcessor<T : Component> : DecorationRenderer<ComponentPostProcessorContext<T>>
