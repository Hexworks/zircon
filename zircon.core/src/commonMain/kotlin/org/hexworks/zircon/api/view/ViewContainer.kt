package org.hexworks.zircon.api.view

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.view.DefaultViewContainer

/**
 * A [ViewContainer] handles the displaying of [View]s. [View]s are similar to
 * [Fragment]s, but instead of being reusable groups of [Component]s they are
 * used for creating reusable [Screen]s.
 */
interface ViewContainer {

    /**
     * Docks a [View] to this [ViewContainer]. When a [View] is [dock]ed
     * the previous [View] will be undocked and [View.onUndock] will be
     * called on it. After that [View.onDock] will be called on the
     * new [view].
     */
    fun dock(view: View)

    companion object {

        fun create(): ViewContainer {
            return DefaultViewContainer()
        }
    }
}
