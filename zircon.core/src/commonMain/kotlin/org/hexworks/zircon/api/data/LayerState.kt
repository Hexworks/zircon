package org.hexworks.zircon.api.data

import org.hexworks.cobalt.Identifier
import org.hexworks.zircon.api.graphics.Layer

/**
 * Holds the immutable contents of a [Layer] at a given moment in time.
 */
interface LayerState : DrawSurfaceState {

    val id: Identifier
    val position: Position
    val isHidden: Boolean

    operator fun component4() = id

    operator fun component5() = position

    operator fun component6() = isHidden

}
