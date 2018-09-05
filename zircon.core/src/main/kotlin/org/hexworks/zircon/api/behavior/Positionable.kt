package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Position

/**
 * Represents an object which is positionable within its parent.
 * For example a [org.hexworks.zircon.api.component.Component] can be positioned
 * relative to is parent or an [org.hexworks.zircon.api.graphics.Layer]
 * can be positioned within a [org.hexworks.zircon.api.grid.TileGrid].
 * Note that once positioned a [Positionable] can't be moved.
 * If you want re-positionable objects @see [Movable].
 */
interface Positionable {

    /**
     * Returns the position of this [Positionable]. Default is (0, 0).
     * The position of a [Positionable] is its position relative to the
     * gui window's top left corner. An offset of (0, 0) denotes
     * that corner.
     * Only override the default if it is applicable in your context
     * (in [org.hexworks.zircon.api.graphics.Layer] for example).
     */
    fun position(): Position = Position.defaultPosition()

}
