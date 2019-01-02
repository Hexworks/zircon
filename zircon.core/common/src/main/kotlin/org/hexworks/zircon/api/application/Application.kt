package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.mvc.ViewContainer

/**
 * An [Application] enhances a [TileGrid] with continuous rendering,
 * and some additional functionality for starting, pausing, resuming and
 * stopping it.
 */
interface Application : ViewContainer {

    /**
     * Initializes this [Application] and starts continuous rendering.
     */
    fun start()

    /**
     * Pauses rendering.
     */
    fun pause()

    /**
     * Resumes rendering.
     */
    fun resume()

    /**
     * Stops this [Application] and frees all of its resources.
     * Once an [Application] is stopped it can't be started again.
     */
    fun stop()
}
