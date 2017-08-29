package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.Position

/**
 * Represents an object which is positionable within its parent.
 * For example a [org.codetome.zircon.api.component.Component] can be positioned
 * relative to is parent or an [org.codetome.zircon.api.graphics.Layer]
 * can be positioned within a [org.codetome.zircon.api.terminal.Terminal].
 * Note that once positioned a [Positionable] can't be moved.
 * @see [Movable]
 */
interface Positionable {

    /**
     * Returns the offset of this [Positionable]. Default is (0, 0).
     * Only override the default if it is applicable in your context
     * (in [org.codetome.zircon.api.graphics.Layer] for example).
     */
    fun getPosition(): Position = Position.DEFAULT_POSITION

}