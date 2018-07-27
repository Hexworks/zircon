package org.codetome.zircon.api.behavior

/**
 * Any object which implements [Clearable] is supposed to support
 * the notion of "clearing".
 * Clearing differs from emptying in that it resets the [Clearable]
 * object to its default state which is visually empty.
 *
 * Clear in the case of a [org.codetome.zircon.api.grid.TileGrid] means
 * resetting all its buffers to contain empty strings.
 */
interface Clearable {

    /**
     * Clears this [Clearable].
     */
    fun clear()
}
