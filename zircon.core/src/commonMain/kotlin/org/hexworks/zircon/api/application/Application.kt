package org.hexworks.zircon.api.application

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.grid.TileGrid

/**
 * An [Application] enhances a [TileGrid] with continuous rendering,
 * and some additional functionality for starting, pausing, resuming and
 * stopping it.
 */
interface Application {

    val tileGrid: TileGrid

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

    /**
     * Adds a callback which will be called **before** every render.
     * **Note that** this operation should be **very fast** otherwise it will
     * block the rendering. Very fast is `<1ms`.
     */
    fun beforeRender(listener: (RenderData) -> Unit): Subscription

    /**
     * Adds a callback which will be called **after** every render.
     * **Note that** this operation should be **very fast** otherwise it will
     * block the rendering. Very fast is `<1ms`.
     */
    fun afterRender(listener: (RenderData) -> Unit): Subscription
}
