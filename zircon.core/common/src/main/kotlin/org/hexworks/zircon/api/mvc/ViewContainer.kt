package org.hexworks.zircon.api.mvc

import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.grid.TileGrid

/**
 * A [ViewContainer] handles the displaying of [View]s.
 */
interface ViewContainer {

    val tileGrid: TileGrid

    /**
     * Docks a [View] to this [Application]. When a [View] is [dock]ed
     * the previous [View] will be undocked and [View.onUndock] will be
     * called on it. After that [View.onDock] will be called on the
     * new [view].
     */
    fun dock(view: View)
}
