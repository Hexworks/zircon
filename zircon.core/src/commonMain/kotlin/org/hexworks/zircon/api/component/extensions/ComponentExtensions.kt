package org.hexworks.zircon.api.component.extensions

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.extensions.withPosition
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position

val Component.relativeBounds: Boundable
    get() {
        val parentPosition = with(asInternalComponent()) {
            parent?.contentBounds?.position ?: Position.ZERO
        }
        return bounds.withPosition(position - parentPosition)
    }

