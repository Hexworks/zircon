package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.behavior.Themeable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.api.uievent.UIEventSource
import org.hexworks.zircon.internal.behavior.Identifiable

/**
 * A [Component] is a graphical object which represents a GUI element and used either to
 * display information to the user or to enable the user to interact with the program.
 *
 * Components are organized into a tree structure of [Component] elements nested in each other.
 * The component hierarchy **always** has a [Container] at its root. A child [Component]
 * is **always** bounded by its parent. Containers are branches in this tree while [Component]s
 * are leaves. So for example a panel which is intended to be able to hold other components
 * like a label or a check box is a [Container] while a label which is only intended to
 * display information is just a [Component].
 *
 * The [Component] abstraction implements the **Composite** design pattern with [Component]
 * and [Container].
 */
interface Component : ComponentEventSource, Identifiable, Movable, Themeable,
        TilesetOverride, UIEventSource {

    /**
     * The absolute position of this [Component], eg: the [Position] relative the
     * top left corner of the grid it is displayed on. This value is context
     * dependent and it is calculated based on what it is attached to.
     */
    val absolutePosition: Position
        get() = position

    /**
     * The relative position is the position of the top left corner
     * of this [Component] relative to the [contentOffset] of
     * its parent.
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

    // TODO: introduce Hideable
    /**
     * Tells whether this [Component] is visible or not.
     */
    var isHidden: Boolean

    /**
     * [Property] which tells whether this [Component] is visible or not.
     */
    val hiddenProperty: Property<Boolean>

    /**
     * The [ComponentStyleSet] of this [Component].
     */
    val componentStyleSet: ComponentStyleSet

    /**
     * Tells whether this [Component] is attached to a parent or not.
     */
    fun isAttached(): Boolean

    /**
     * Detaches this [Component] from its parent (if any).
     */
    fun detach()

    /**
     * Requests that this [Component] be focused.
     */
    fun requestFocus()

    /**
     * Clears focus from this [Component]. Has no effect
     * if this [Component] is not focused.
     */
    fun clearFocus()

}
