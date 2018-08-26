package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.util.Consumer
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
interface Component : Identifiable, Layer {

    /**
     * Returns the effective [Size] of this [Component] which is the area remaining
     * after the area taken up by its wrappers are subtracted from its boundable size.
     * So if a [Component] has a box around it and it has a boundable size of (5, 5)
     * its inner size will be (3, 3).
     */
    fun getEffectiveSize(): Size

    /**
     * Returns the effective [Position] of this [Component] which is the component's `position`
     * offset by the space taken up by its wrappers.
     * So if a [Component] has a box around it and it has a Position of (2, 3)
     * its effective position will be (3, 4).
     */
    fun getEffectivePosition(): Position

    /**
     * Adds a callback to this [Component] which will be called
     * when the mouse is pressed on this component.
     */
    fun onMousePressed(callback: Consumer<MouseAction>)

    /**
     * Adds a callback to this [Component] which will be called
     * when the mouse is released on this component.
     */
    fun onMouseReleased(callback: Consumer<MouseAction>)

    /**
     * Adds a callback to this [Component] which will be called
     * when the mouse is moved over this component (to a new [org.hexworks.zircon.api.data.Position]).
     */
    fun onMouseMoved(callback: Consumer<MouseAction>)

    /**
     * Gets the styles this [Component] uses.
     */
    fun getComponentStyles(): ComponentStyleSet

    /**
     * Sets the styles this [Component] will be displayed with.
     */
    fun setComponentStyles(componentStyleSet: ComponentStyleSet)

    /**
     * Applies a [ColorTheme] to this component and recursively to all its children (if any).
     */
    fun applyColorTheme(colorTheme: ColorTheme)

}
