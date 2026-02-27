package org.hexworks.zircon.internal.component

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.BindingAction
import org.hexworks.cobalt.databinding.api.value.ObservableValue

import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.impl.RootContainer
import org.hexworks.zircon.internal.graphics.Renderable
import org.hexworks.zircon.internal.uievent.ComponentEventAdapter
import org.hexworks.zircon.internal.uievent.KeyboardEventAdapter
import org.hexworks.zircon.internal.uievent.MouseEventAdapter
import org.hexworks.zircon.internal.uievent.UIEventProcessor

/**
 * A [InternalComponent] represents the internal API of the [Component] interface which adds
 * functionality which Zircon will use internally. This makes it possible to have
 * a clean API for [Component]s but enables Zircon and the developers of custom [Component]s
 * to interact with them in a more meaningful manner.
 */
interface InternalComponent :
    Component, ComponentEventAdapter, KeyboardEventAdapter, MouseEventAdapter, Renderable, UIEventProcessor {

    /**
     * This is possibly `null` if the component is not attached to a root.
     */
    var root: RootContainer?
    val rootValue: ObservableValue<RootContainer?>

    /**
     * @see root
     */
    var parent: InternalContainer?
    val parentProperty: Property<InternalContainer?>
    val hasParent: ObservableValue<Boolean>

    /**
     * The immediate child [Component]s of this [Component] (if any).
     */
    val children: ObservableList<out InternalComponent>

    /**
     * Contains `this` component and all of its descendants
     */
    val flattenedTree: Collection<InternalComponent>
        get() = listOf(this) + children.map { it.asInternalComponent() }.flatMap { it.flattenedTree }

    /**
     * The position that was set when the component was originally built. The current position can change
     * during the lifetime of the component, especially if it was added to a parent.
     */
    //! TODO: figure out why we need this
    val originalPosition: Position

    val isAttached: Boolean
        get() = parent != null
    val isAttachedToRoot: Boolean
        get() = root != null

    /**
     * Tells how the [Component]'s observable properties should be
     * handled when the component is attached.
     * E.g.: should they get updated from the new parent's properties
     */
    val bindingAction: BindingAction

    override var componentState: ComponentState

    /**
     * Converts the given [ColorTheme] to the equivalent [ComponentStyleSet] representation.
     */
    fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet

    /**
     * Runs [fn] only if this [Component] [isAttachedToRoot] or it **is** a root.
     */
    fun whenConnectedToRoot(fn: (root: RootContainer) -> Unit) {
        if (isAttachedToRoot || this is RootContainer) {
            root?.let(fn)
        }
    }

}
