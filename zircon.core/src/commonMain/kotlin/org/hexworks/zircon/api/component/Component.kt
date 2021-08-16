package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.api.uievent.UIEventSource
import org.hexworks.zircon.internal.behavior.Focusable
import org.hexworks.zircon.internal.component.InternalComponent

/**
 * A [Component] is a graphical text GUI element and can be used either to
 * display information to the user or to enable the user to interact with the program.
 *
 * Components are organized into a tree structure of [Component] elements nested in each other.
 * The component hierarchy **always** has a [Container] at its root. A child [Component]
 * is **always** bounded by its parent. Containers are branches in this tree while [Component]s
 * are leaves. So for example a panel which is intended to be able to hold other components
 * like a label or a check box is a [Container] while a label which is only intended to
 * display information is just a [Component].
 *
 * [Component]s also support UI event handling through [UIEventSource] and [ComponentEventSource]
 * and can handle focus.
 *
 * The [Component] abstraction implements the **Composite** design pattern with [Component]
 * and [Container].
 *
 * @see Container
 * @see ComponentContainer
 * @see UIEventSource
 * @see ComponentEventSource
 * @see Focusable
 */
interface Component : ComponentEventSource, ComponentProperties, Focusable, Movable, UIEventSource {

    /**
     * The absolute position of this [Component], eg: the [Position] relative to the
     * top left corner of the grid it is displayed on. This value is context
     * dependent and it is calculated based on the parent it is attached to.
     */
    val absolutePosition: Position

    /**
     * The relative position is the position of the top left corner of this [Component]
     * relative to the [contentOffset] of its parent.
     */
    val relativePosition: Position

    /**
     * The position of the top left corner of the **content area** (where the component
     * is rendered without the decorations) relative to the top left corner of this
     * [Component]. In other words the content offset is the sum of the offset positions
     * for each decoration.
     */
    val contentOffset: Position

    /**
     * The [Size] of the content of this [Component]. In other words the content
     * size is the total size of the component minus the size of its decorations.
     */
    val contentSize: Size

    /**
     * The bounds of this [Component] relative to its parent.
     */
    val relativeBounds: Rect

    /**
     * The current style based on [componentStyleSet] according to the current [componentState].
     */
    val currentStyle: StyleSet
        get() = componentStyleSet.fetchStyleFor(componentState)

    val componentStateValue: ObservableValue<ComponentState>
    val componentState: ComponentState

    /**
     * The [ComponentStyleSet] of this [Component]. Note that if you set
     * it by hand it will take precedence over the [ComponentStyleSet] provided
     * by [theme].
     */
    var componentStyleSet: ComponentStyleSet
    val componentStyleSetProperty: Property<out ComponentStyleSet>

    /**
     * Returns this [Component] as an [InternalComponent] which represents
     * the internal API of [Component].
     */
    fun asInternalComponent(): InternalComponent

    /**
     * Clears any custom [componentStyleSet] (if present).
     */
    fun clearCustomStyle()

    fun resetState()

    companion object
}
