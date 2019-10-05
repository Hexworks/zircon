package org.hexworks.zircon.api.grid

import org.hexworks.zircon.api.animation.AnimationHandler
import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.behavior.ShutdownHook
import org.hexworks.zircon.api.behavior.TypingSupport
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.uievent.UIEventSource

/**
 * The [TileGrid] is the most fundamental interface in Zircon.
 * It is an abstraction which lets you manage a 2D grid composed of [Tile]s.
 * It also supports layering (see [Layerable] for more info), cursor handling
 * and character printing through [TypingSupport], event handling through [UIEventSource]
 * and simple [Tile] drawing operations through [TileGraphics] and animation handling
 * with [AnimationHandler].
 * You can consider a [TileGrid] as an easy to use **facade** for all your tile grid
 * needs.
 * **Note That** all [TileGrid]s have a [Layer] at index `0` which is used
 * for implementing the [TileGraphics] operations and it can't be removed from the grid.
 * In short all [TileGrid]s have at least **one** [Layer] in them.
 */
interface TileGrid
    : AnimationHandler, Clearable, Closeable, DrawSurface, Layerable,
        ShutdownHook, TypingSupport, UIEventSource {

    val widthInPixels: Int
        get() = tileset.width * width

    val heightInPixels: Int
        get() = tileset.height * height

}
