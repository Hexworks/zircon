package org.hexworks.zircon.internal.component

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.kotlin.map
import org.hexworks.zircon.api.listener.InputListener
import org.hexworks.zircon.api.listener.KeyStrokeListener
import org.hexworks.zircon.api.listener.MouseListener
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.Focusable

/**
 * A [InternalComponent] is a specialization of the [Component] interface which adds
 * functionality which will be used by Zircon internally. This makes it possible to have
 * a clean API for [Component]s but enables Zircon and the developers of custom [Component]s
 * to interact with them in a more meaningful manner.
 */
interface InternalComponent : Component, Focusable, InputListener, KeyStrokeListener, MouseListener {

    /**
     * The [org.hexworks.zircon.api.graphics.TileGraphics] which this
     * component uses for drawing.
     */
    val graphics: TileGraphics

    override fun isAttached(): Boolean = fetchParent().isPresent

    /**
     * Returns the innermost [InternalComponent] for a given [Position].
     * This means that if you call this method on a [Container] and it
     * contains a [InternalComponent] which intersects with `position` the
     * component will be returned instead of the container itself.
     * If no [InternalComponent] intersects with the given `position` an
     * empty [Maybe] is returned.
     */
    fun fetchComponentByPosition(position: Position): Maybe<out InternalComponent>

    /**
     * Returns the parent of this [Component] (if any).
     */
    fun fetchParent(): Maybe<Container>

    /**
     * Attaches this [Component] to the given parent [Container].
     * Note that if this component is already attached to a [Container]
     * it will be removed from that one.
     */
    fun attachTo(parent: Container)

    /**
     * Renders this component to the underlying [TileGraphics].
     */
    fun render()

    override fun detach() {
        fetchParent().map {
            it.removeComponent(this)
        }
    }

}
