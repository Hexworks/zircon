package org.hexworks.zircon.internal.renderer

import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.behavior.Closeable

interface Renderer<A: Application> : Closeable {

    /**
     *  Called when the [Renderer] is first created.
     */
    fun create()

    /**
     * Called when the [Renderer] should render.
     */
    fun render()

    /**
     * Called when the [Renderer] is destroyed.
     */
    override fun close()
}
