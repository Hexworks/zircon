package org.hexworks.zircon.api.view

import org.hexworks.zircon.internal.mvc.DefaultViewContainer
import kotlin.jvm.JvmStatic

/**
 * A [ViewContainer] handles the displaying of [View]s.
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

        @JvmStatic
        fun create(): ViewContainer {
            return DefaultViewContainer()
        }
    }
}
