package org.hexworks.zircon.internal.component

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.behavior.Focusable
import org.hexworks.zircon.internal.uievent.ComponentEventAdapter
import org.hexworks.zircon.internal.uievent.KeyboardEventAdapter
import org.hexworks.zircon.internal.uievent.MouseEventAdapter
import org.hexworks.zircon.internal.uievent.UIEventProcessor

/**
 * A [InternalComponent] is a specialization of the [Component] interface which adds
 * functionality which will be used by Zircon internally. This makes it possible to have
 * a clean API for [Component]s but enables Zircon and the developers of custom [Component]s
 * to interact with them in a more meaningful manner.
 */
interface InternalComponent : Component, ComponentEventAdapter, Focusable, KeyboardEventAdapter, MouseEventAdapter, UIEventProcessor {

    /**
     * The [org.hexworks.zircon.api.graphics.TileGraphics] which this
     * component uses for drawing.
     */
    val graphics: TileGraphics

    // TODO: make val
    override fun isAttached(): Boolean = fetchParent().isPresent

    /**
     * Attaches this [Component] to the given parent [Container].
     * Note that if this component is already attached to a [Container]
     * it will be removed from that one.
     */
    fun attachTo(parent: InternalContainer)

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
     * Returns the this [Component] and its children (if any)
     * flattened into an [Iterable] of [Layer]s.
     */
    fun toFlattenedLayers(): Iterable<Layer>

    /**
     * Returns the this [Component] and its children (if any)
     * flattened into an [Iterable] of [InternalComponent]s.
     */
    fun toFlattenedComponents(): Iterable<InternalComponent>

    /**
     * Returns the parent of this [Component] (if any).
     */
    fun fetchParent(): Maybe<InternalContainer>

    /**
     * Recursively traverses the parents of this [InternalComponent]
     * until the root is reached and returns them.
     */
    fun calculatePathFromRoot(): Iterable<InternalComponent>

    /**
     * Renders this component to the underlying [TileGraphics].
     */
    fun render()

}
