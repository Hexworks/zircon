package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.api.uievent.UIEventSource
import org.hexworks.zircon.internal.behavior.Identifiable

/**
 * A [Component] is a GUI element which is used either to display information to the user
 * or to enable the user to interact with the program.
 * Components are basically a tree structure of GUI elements nested in each other.
 * The component hierarchy **always** has a [Container] as its root. A child [Component]
 * is **always** bounded by its parent. Containers are branches in this tree while components
 * are leaves. So for example a panel which is intended to be able to hold other components
 * like a label or a check box is a [Container] while a label which is only intended to
 * display information is a [Component].
 */
interface Component : Identifiable, Layer, UIEventSource, ComponentEventSource {

    /**
     * The [Position] where the content of this [Component] starts
     * relative to the top left corner. In other words the content position
     * is the sum of the offset positions for each decoration.
     */
    val contentPosition: Position

    /**
     * The [Size] of the content of this [Component].
     * In other words the content size is the total size of
     * the component minus the size of the decorations.
     */
    val contentSize: Size

    /**
     * The absolute position of this [Component].
     * The absolute position is the position of the top left corner
     * of this component relative to the top left corner of the grid.
     */
    val absolutePosition: Position

    /**
     * The styles this [Component] uses.
     */
    var componentStyleSet: ComponentStyleSet

    /**
     * The styles property this [Component] uses.
     */
    val componentStyleSetProperty: Property<ComponentStyleSet>


    /**
     * The visibility of this [Component]
     */
    var isVisible: Visibility

    /**
     * The visibility property of this [Component]
     */
    val visibilityProperty: Property<Visibility>


    /**
     * Tells whether this [Component] is attached to a parent or not.
     */
    fun isAttached(): Boolean

    /**
     * Detaches this [Component] from its parent (if any).
     */
    fun detach()

    /**
     * Applies a [ColorTheme] to this component and recursively to all its children (if any).
     * @return the [ComponentStyleSet] which the [ColorTheme] was converted to
     */
    fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet

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
