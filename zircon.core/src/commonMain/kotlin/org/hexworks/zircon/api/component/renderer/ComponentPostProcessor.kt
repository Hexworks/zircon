package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component

/**
 * A [ComponentPostProcessor] is responsible for post-processing after a component render.
 * It is a specialization of the [DecorationRenderer] as the mechanism is the same, but the
 * post-processor will be executed last, and it can modify the final appearance of the component.
 */
interface ComponentPostProcessor<T : Component> : DecorationRenderer<ComponentPostProcessorContext<T>>
