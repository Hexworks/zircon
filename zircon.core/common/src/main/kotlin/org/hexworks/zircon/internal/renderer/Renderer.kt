package org.hexworks.zircon.internal.renderer

import org.hexworks.zircon.api.behavior.Closeable

interface Renderer : Closeable {

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
