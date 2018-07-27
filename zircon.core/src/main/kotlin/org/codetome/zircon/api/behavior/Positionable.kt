package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.data.Position

/**
 * Represents an object which is positionable within its parent.
 * For example a [org.codetome.zircon.api.component.Component] can be positioned
 * relative to is parent or an [org.codetome.zircon.api.graphics.Layer]
 * can be positioned within a [org.codetome.zircon.api.grid.TileGrid].
 * Note that once positioned a [Positionable] can't be moved.
 * If you want re-positionable objects @see [Movable].
 */
interface Positionable {

    /**
     * Returns the (absolute) offset of this [Positionable]. Default is (0, 0).
     * The `offset` of a position is its position relative to the
     * text gui window's top left corner. An offset of (0, 0) denotes
     * that corner.
     * Only override the default if it is applicable in your context
     * (in [org.codetome.zircon.api.graphics.Layer] for example).
     */
    fun getPosition(): Position = Position.defaultPosition()

}
