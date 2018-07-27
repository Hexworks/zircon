package org.codetome.zircon.internal.grid

import org.codetome.zircon.api.behavior.Styleable
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.grid.GridResizeListener
import org.codetome.zircon.internal.behavior.impl.DefaultStyleable

abstract class AbstractTileGrid(styleable: Styleable = DefaultStyleable(StyleSet.defaultStyle()))
    : Styleable by styleable, TileGrid {

    private val resizeListeners = mutableListOf<GridResizeListener>()
    private var lastKnownSize = Size.unknown()

    /**
     * Call this method when the grid has been resized or the initial size of the grid
     * has been discovered. It will trigger all resize listeners,
     * but only if the size has changed from before.
     */
    protected fun onResized(newSize: Size) {
        if (lastKnownSize != newSize) {
            lastKnownSize = newSize
            resizeListeners.forEach { it.onResized(this, lastKnownSize) }
        }
    }

    override fun addResizeListener(listener: GridResizeListener) {
        resizeListeners.add(listener)
    }

    override fun removeResizeListener(listener: GridResizeListener) {
        resizeListeners.remove(listener)
    }
}
