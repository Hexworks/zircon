package org.hexworks.zircon.api.grid

import org.hexworks.zircon.api.animation.AnimationRunner
import org.hexworks.zircon.api.behavior.*
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.uievent.UIEventSource
import org.hexworks.zircon.api.view.ViewContainer

/**
 * The [TileGrid] is the most fundamental interface in Zircon.
 * It is an abstraction which lets you manage a 2D grid composed of [Tile]s.
 * It also supports layering, cursor handling, character printing, event handling,
 * simple [Tile] drawing operations and many more.
 *
 * You can consider a [TileGrid] as an easy to use **facade** for all of your tile grid
 * needs.
 *
 * **Note That** all [TileGrid]s have a [Layer] at index `0` which is used
 * for implementing the [DrawSurface] operations and it can't be removed from the grid.
 *
 * In short all [TileGrid]s have at least **one** [Layer] in them.
 *
 * @see AnimationRunner
 * @see Clearable
 * @see Closeable
 * @see DrawSurface
 * @see Layerable
 * @see ShutdownHook
 * @see TypingSupport
 * @see UIEventSource
 * @see ViewContainer
 */
interface TileGrid : AnimationRunner, Clearable, Closeable, DrawSurface, Layerable,
    ShutdownHook, TypingSupport, UIEventSource, ViewContainer {

    val widthInPixels: Int
        get() = tileset.width * width

    val heightInPixels: Int
        get() = tileset.height * height

}
