package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.graphics.Renderable

/**
 * Extends an [InternalLayerable] with additional [renderables] taken form an [InternalComponentContainer]
 * to enable smooth rendering of both.
 */
class ComponentsLayerable(
    private val componentContainer: InternalComponentContainer,
    private val layerable: InternalLayerable
) : InternalLayerable by layerable {

    override val renderables: List<Renderable>
        get() = componentContainer.renderables + layerable.renderables

}
