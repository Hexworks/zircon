package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.binding.bindPlusWith
import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.component.InternalComponentContainer

/**
 * Extends a [InternalLayerable] with additional [renderables] taken form an [InternalComponentContainer]
 * to enable smooth rendering of both.
 */
class ComponentsLayerable(
        componentContainer: InternalComponentContainer,
        private val layerable: InternalLayerable
) : InternalLayerable by layerable {

    override val renderables = componentContainer.renderables.bindPlusWith(layerable.renderables)

}
