@file:Suppress("unused")

package org.codetome.zircon.api.grid

import org.codetome.zircon.api.behavior.*
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.internal.behavior.ShutdownHook

/**
 * This is the main grid interface, at the lowest level supported. You can write your own
 * implementation of this if you want to target an exotic graphical environment (like SWT),
 * but you should probably wrap [org.codetome.zircon.internal.grid.virtual.VirtualTileGrid]
 * and delegate to it instead of implementing this interface directly.
 *
 * The normal way you interact in Java with a grid is through the standard output (SystemUtils.out)
 * and standard error (SystemUtils.err) and it's usually through printing characters only.
 * This interface abstracts a grid at a more fundamental level, expressing methods for not only printing
 * title but also changing colors, moving the cursor to new positions, enable special modifiers and get
 * notified when the grid's size has changed.
 *
 * If you want to write an application that has a very precise control of the grid, this is the
 * interface you should be programming against.
 */
interface TileGrid
    : Closeable, Clearable, Styleable, TypingSupport, Layerable, DrawSurface, InputEmitter, TilesetOverride, ShutdownHook {

    /**
     * Adds a [GridResizeListener] to be called when the grid has changed size.
     */
    fun addResizeListener(listener: GridResizeListener)

    /**
     * Removes a [GridResizeListener] from the list of listeners to be notified when
     * the grid has changed size.
     */
    fun removeResizeListener(listener: GridResizeListener)

    /**
     * Changes the visible size of the grid. If you call this method with a size
     * that is different from the current size of the grid, the resize event
     * will be fired on all listeners.
     */
    fun setSize(newSize: Size)

    /**
     * Draws the contents of this [TileGrid] onto the screen.
     */
    fun flush()

}

