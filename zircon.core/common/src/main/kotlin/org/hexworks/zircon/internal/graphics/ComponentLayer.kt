package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable
import kotlin.jvm.Synchronized

class ComponentLayer(initialPosition: Position,
                     initialContents: TileGraphics,
                     private val movable: Movable = DefaultMovable(
                             position = initialPosition,
                             size = initialContents.size)) : ThreadSafeLayer(
        initialPosition = initialPosition,
        initialContents = initialContents,
        movable = movable) {

    // we don't update the state on move in a component
    @Synchronized
    override fun moveTo(position: Position) {
        movable.moveTo(position)
        refreshState()
    }
}
