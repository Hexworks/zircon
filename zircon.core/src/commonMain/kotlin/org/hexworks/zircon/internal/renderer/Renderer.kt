package org.hexworks.zircon.internal.renderer

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.application.RenderData
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.internal.renderer.impl.BaseRenderer

/**
 * A [Renderer] is responsible for rendering the tiles on the user's screen.
 * It implements the template method pattern, and it works the following way:
 *
 * - [create] initializes the renderer and returns the UI view object
 * - [beforeRender] is called before rendering
 * - [render] renders the tile grid contents onto the actual screen
 * - [afterRender] is called after the rendering is done.
 * - [close] is called when the renderer should be destroyed.
 *
 * If you're using an [Application] you won't have to deal with the renderer
 * directly. If not you'll have to call these methods yourself.
 *
 * You can use [BaseRenderer] as a base class if you want to write your own
 * renderer implementation.
 *
 * @param C The type of the render context
 * @param A The type of the [Application]
 * @param V The type of the view object returned from [create]
 */
interface Renderer<C : Any, A : Application, V> : Closeable {

    /**
     *  Called when the [Renderer] is first created. Returns
     *  the UI View object that this [Renderer] will draw upon.
     */
    fun create(): V

    /**
     * Called when the [Renderer] should render with the appropriate
     * framework-specific object that can be used for rendering.
     */
    fun render(context: C)

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
     * Called when the [Renderer] is destroyed.
     */
    override fun close()
}
