package org.hexworks.zircon.api.application

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.application.InternalApplication

/**
 * An [Application] enhances a [TileGrid] with continuous rendering,
 * and some additional functionality such as rendering callbacks.
 */
interface Application : Closeable {

    /**
     * The tile grid that's being rendered by this [Application].
     */
    val tileGrid: TileGrid

    /**
     * Starts this application. Starting means that it starts rendering
     * either continuously or using render hint events. This is an implementation
     * detail, see the [Application] base classes for more info.
     */
    suspend fun start()

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

    /**
     * Exposes the internal API of this [Application].
     */
    fun asInternal(): InternalApplication
}
