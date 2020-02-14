package org.hexworks.zircon.internal.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.behavior.Focusable
import org.hexworks.zircon.internal.component.impl.RootContainer
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentMoved
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
interface InternalComponent : Component, ComponentEventAdapter, Focusable,
        KeyboardEventAdapter, MouseEventAdapter, UIEventProcessor {

    val isAttached: Boolean

    var parent: Maybe<InternalContainer>
    val parentProperty: Property<Maybe<InternalContainer>>

    val hasParent: ObservableValue<Boolean>

    /**
     * The immediate child [Component]s of this [Component].
     */
    val children: Iterable<InternalComponent>

    /**
     * All descendant [Component]s of this [Component].
     */
    val descendants: Iterable<InternalComponent>

    /**
     * Returns the flattened component tree rooted at this component. Similar to [descendants]
     * with the exception that **this** [Component] is also included in the result.
     */
    val flattenedTree: Iterable<InternalComponent>
        get() = listOf(this) + descendants

    /**
     * The [TileGraphics] through which this [InternalComponent] can be drawn upon.
     */
    val graphics: TileGraphics

    /**
     * The [LayerState] (s) representing the contents of this
     * [InternalComponent]. Apart from the [graphics] a component
     * can have multiple layers representing its content. Layers
     * are ordered from top to bottom with regards to the Z axis.
     */
    val layerStates: Iterable<LayerState>

    /**
     * Tells whether this [Component] is attached to a [RootContainer] or not.
     */
    fun isAttachedToRoot(): Boolean = calculatePathFromRoot()
            .filterIsInstance<RootContainer>()
            .isNotEmpty()

    /**
     * Moves this [InternalComponent] to the given [position].
     * If [signalComponentChange] is `true` this function will send
     * a [ComponentMoved] event.
     */
    fun moveTo(position: Position, signalComponentChange: Boolean)

    /**
     * Returns the innermost [InternalComponent] for a given [Position].
     * This means that if you call this method on a [Container] and it
     * contains a [InternalComponent] which intersects with `position` the
     * component will be returned instead of the container itself.
     * If no [InternalComponent] intersects with the given `position` an
     * empty [Maybe] is returned.
     */
    fun fetchComponentByPosition(absolutePosition: Position): Maybe<out InternalComponent>

    /**
     * Recursively traverses the parents of this [InternalComponent]
     * until the root is reached and returns them.
     */
    fun calculatePathFromRoot(): List<InternalComponent>

    /**
     * Renders this component to the underlying [TileGraphics].
     */
    fun render()

    /**
     * Converts the given [ColorTheme] to the equivalent [ComponentStyleSet] representation.
     */
    fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet

}
