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
public interface InternalComponent :
    Component, ComponentEventAdapter, KeyboardEventAdapter, MouseEventAdapter, Renderable, UIEventProcessor {

    /**
     * This is possibly `null` if the component is not attached to a root.
     */
    public var root: RootContainer?
    public val rootProperty: ObservableValue<RootContainer?>

    /**
     * Tells whether this [Component] is attached to a [org.hexworks.zircon.api.component.Container].
     */
    public val isAttached: Boolean
        get() = parent != null


    /**
     * @see root
     */
    public var parent: InternalContainer?
    public val parentProperty: Property<InternalContainer?>
    public val hasParent: ObservableValue<Boolean>

    /**
     * The immediate child [Component]s of this [Component] (if any).
     */
    public val children: ObservableList<out InternalComponent>

    /**
     * The position that was set when the component was originally built. The current position can change
     * during the lifetime of the component, especially if it was added to a parent.
     * We store its [originalPosition] because if we detach a component we need to know what its original
     * settings were.
     * @see org.hexworks.zircon.api.component.AttachedComponent.detach
     */
    public val originalPosition: Position

    /**
     * Tells how the [Component]'s observable properties should be
     * handled when the component is attached.
     * E.g.: should they get updated from the new parent's properties
     */
    public val bindingAction: BindingAction

    override var componentState: ComponentState

    /**
     * Converts the given [ColorTheme] to the equivalent [ComponentStyleSet] representation.
     */
    public fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet

    /**
     * Marks the rendered state of this component stale: the next [render] redraws it
     * instead of returning the cached result. Called for every observable the renderer
     * reads and whenever the component handles a UI event; call it from a custom
     * component when it changes something else its renderer depends on.
     */
    public fun invalidateRender()

}
