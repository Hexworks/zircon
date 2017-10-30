package org.codetome.zircon.api.component

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.FontOverride
import org.codetome.zircon.api.behavior.Positionable
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.internal.behavior.Identifiable
import java.awt.image.BufferedImage
import java.util.function.Consumer

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
interface Component : Positionable, Identifiable, Boundable, FontOverride {

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
     * when the mouse is moved over this component (to a new [org.codetome.zircon.api.Position]).
     */
    fun onMouseMoved(callback: Consumer<MouseAction>)

    /**
     * Gets the styles this [Component] uses.
     */
    fun getComponentStyles() : ComponentStyles

    /**
     * Sets the styles this [Component] should use.
     */
    fun setComponentStyles(componentStyles: ComponentStyles)

    /**
     * Applies a [ColorTheme] to this component and recursively to all its children (if any).
     */
    fun applyTheme(colorTheme: ColorTheme)

}