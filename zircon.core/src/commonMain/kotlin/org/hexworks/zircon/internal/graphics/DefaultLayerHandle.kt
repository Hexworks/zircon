package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.LayerHandle
import org.hexworks.zircon.internal.behavior.InternalLayerable

class DefaultLayerHandle(
        private val backend: Layer,
        private val parent: InternalLayerable
) : LayerHandle, Layer by backend {

    override fun removeLayer() = parent.remove(backend)

}