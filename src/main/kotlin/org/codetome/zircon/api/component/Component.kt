package org.codetome.zircon.api.component

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.internal.behavior.Focusable
import org.codetome.zircon.api.behavior.Positionable
import org.codetome.zircon.internal.behavior.Identifiable
import org.codetome.zircon.internal.component.listener.MouseListener
import java.util.*

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
interface Component : Positionable, Identifiable, Boundable {

    /**
     * Adds a [MouseListener] to this [Component] which will
     * be fired when mouse events happen within its bounds.
     */
    fun addMouseListener(mouseListener: MouseListener)

    /**
     * Gets the styles this [Component] uses.
     */
    fun getComponentStyles() : ComponentStyles

    /**
     * Sets the styles this [Component] should use.
     */
    fun setComponentStyles(componentStyles: ComponentStyles)

    /**
     * Applies the [Theme] to this component and recursively to all its children (if any).
     */
    fun applyTheme(theme: Theme)

}