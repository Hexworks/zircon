package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.StyleSet
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
     * Tells whether this [Component] is attached to a parent or not.
     */
    fun isAttached(): Boolean

    /**
     * The [Position] where the content of this [Component] starts
     * relative to the top left corner. In other words the content position
     * is the sum of the offset positions for each decoration.
     */
    fun contentPosition(): Position

    /**
     * Calculates the [Size] of the content of this [Component].
     * In other words the content size is the total size of
     * the component minus the size of the decorations.
     */
    fun contentSize(): Size

    /**
     * Returns the absolute position of this [Component].
     * The absolute position is the position of the top left corner
     * of this component relative to the top left corner of the grid.
     */
    fun absolutePosition(): Position

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
    fun componentStyleSet(): ComponentStyleSet

    /**
     * Sets the styles this [Component] will be displayed with and also
     * applies the style which corresponds to the current state of the [Component].
     */
    fun setComponentStyleSet(componentStyleSet: ComponentStyleSet)

    /**
     * Sets the style of this [Component] from the given `styleSet`
     * and also applies it to all currently present
     * [Tile]s.
     */
    fun applyStyle(styleSet: StyleSet)

    /**
     * Applies a [ColorTheme] to this component and recursively to all its children (if any).
     * @returnThis the [ComponentStyleSet] which the [ColorTheme] was converted to
     */
    fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet

}
