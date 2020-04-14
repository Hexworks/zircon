package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.behavior.Movable

/**
 * Represents a [Layer] which is attached to a [Layerable] objects and
 * has additional functionality related to the [Layerable] such as [removeLayer].
 */
interface LayerHandle : Layer, Movable {

    /**
     * Removes the underlying [Layer] from its parent [Layerable].
     */
    fun removeLayer(): Boolean

    /**
     * Moves this [Layer] [level] levels up or down (positive is up, negative is down).
     * @return `true` if the operation was successful `false` if not
     */
    fun moveByLevel(level: Int): Boolean

    /**
     * Moves this [Layer] up by one level.
     * @return `true` if the operation was successful `false` if not
     */
    fun moveOneLevelUp(): Boolean = moveByLevel(1)

    /**
     * Moves this [Layer] down by one level.
     * @return `true` if the operation was successful `false` if not
     */
    fun moveOneLevelDown(): Boolean = moveByLevel(-1)

}
