package org.codetome.zircon.api.grid

import org.codetome.zircon.api.behavior.*
import org.codetome.zircon.internal.behavior.ShutdownHook

/**
 * This is the main grid interface, at the lowest level supported. You can write your own
 * implementation of this if you want to target an exotic graphical environment (like SWT),
 * but you should probably wrap [org.codetome.zircon.internal.grid.virtual.VirtualTileGrid]
 * and delegate to it instead of implementing this interface directly.
 *
 * This interface abstracts a grid at a more fundamental level, expressing methods for not
 * only printing titles but also changing colors, moving the cursor to new positions,
 * enable special modifiers and get notified when the grid's size has changed.
 *
 * If you want to write an application that has a very precise control of the grid,
 * this is the interface you should be programming against.
 */
interface TileGrid<T: Any, S: Any>
    : Closeable, Clearable, DrawSurface<T>, InputEmitter, Layerable, ShutdownHook, Styleable, TilesetOverride<T, S>, TypingSupport<T> {

    fun widthInPixels() = tileset().width() * getBoundableSize().xLength

    fun heightInPixels() = tileset().height() * getBoundableSize().yLength

}
