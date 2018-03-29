package org.codetome.zircon.internal.terminal

/**
 * Listener for application lifecycle events.
 */
interface ApplicationListener {
    /**
     * Called when the application is first started.
     */
    fun doCreate()

    /**
     * Called when the application is resized.
     * This can happen at any point during a non-paused state but will never happen before a call to [.doCreate].
     *
     * @param width the new width in pixels
     * @param height the new height in pixels
     */
    fun doResize(width: Int, height: Int)

    /**
     *  Called when the application should render itself.
     */
    fun doRender()

    /**
     * Called when the application is paused, usually when it's not active or visible on screen.
     * An application is also paused before it is destroyed.
     */
    fun doPause() = {}

    /**
     *  Called when the application is resumed from a paused state, usually when it regains focus.
     */
    fun doResume() = {}

    /**
     * Called when the application is destroyed. Preceded by a call to [.doPause].
     */
    fun doDispose()
}