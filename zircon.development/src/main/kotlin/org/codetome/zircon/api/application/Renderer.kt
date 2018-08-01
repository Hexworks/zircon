package org.codetome.zircon.api.application

interface Renderer {

    /**
     *  Called when the [Renderer] is first created.
     */
    fun create()

    /**
     * Called when the [Renderer] should render itself.
     */
    fun render()

    /**
     * Called when the [Renderer] is destroyed.
     */
    fun dispose()
}
