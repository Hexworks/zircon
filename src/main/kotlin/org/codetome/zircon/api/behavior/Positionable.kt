package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.Position

/**
 * Represents an object which is positionable within its parent.
 * For example a [org.codetome.zircon.component.Component] can be positioned
 * relative to is parent or an [org.codetome.zircon.graphics.layer.Layer]
 * can be positioned within a [org.codetome.zircon.terminal.Terminal].
 * Note that once positioned a [Positionable] can't be moved.
 * @see [Movable]
 */
interface Positionable {

    /**
     * Returns the offset of this [Positionable]. Default is (0, 0).
     * Only override the default if it is applicable in your context
     * (in [org.codetome.zircon.graphics.layer.Layer] for example).
     */
    fun getPosition(): Position = Position.DEFAULT_POSITION

}