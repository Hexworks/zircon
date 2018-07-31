package org.codetome.zircon.api.application

interface Renderer {
    /**
     *  Called when the [Renderer] is first created.
     */
    fun create() {}

    /**
     * Called when the [Renderer] is resized. This can happen at any
     * point during a non-paused state but will never happen
     * before a call to [Renderer.create].
     *
     * @param width the new width in pixels
     * @param height the new height in pixels
     */
    fun resize(width: Int, height: Int) {}

    /**
     * Called when the [Renderer] should render itself.
     */
    fun render() {}

    /**
     * Called when the [Renderer] is paused, usually when it's not active or
     * visible on screen. An application is also paused before it is destroyed.
     */
    fun pause() {}

    /**
     * Called when the [Renderer] is resumed from a paused state,
     * usually when it regains focus.
     */
    fun resume() {}

    /**
     * Called when the [Renderer] is destroyed.
     * Preceded by a call to [Renderer.pause].
     */
    fun dispose() {}
}
