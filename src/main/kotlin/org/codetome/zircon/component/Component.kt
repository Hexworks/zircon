package org.codetome.zircon.component

import org.codetome.zircon.behavior.Drawable
import org.codetome.zircon.behavior.Positionable
import org.codetome.zircon.component.listener.MouseListener

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
interface Component : Drawable, Positionable {

    /**
     * Adds a [MouseListener] to this [Component] which will
     * be fired when mouse events happen within its bounds.
     */
    fun addMouseListener(mouseListener: MouseListener)

    /**
     * Sets the styles this [Component] should use.
     */
    fun setComponentStyles(componentStyles: ComponentStyles)
}