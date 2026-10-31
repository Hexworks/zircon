package org.hexworks.zircon.api.grid

import org.hexworks.zircon.api.animation.AnimationRunner
import org.hexworks.zircon.api.behavior.*
import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.behavior.extensions.width
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.uievent.UIEventSource
import org.hexworks.zircon.api.view.ViewContainer
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.api.graphics.LayerHandle
/**
 * The [TileGrid] is the most fundamental interface in Zircon. It is an abstraction
 * that lets you manage a 2D grid composed of [Tile]s. It also supports layering,
 * cursor handling, character printing, event handling, simple [Tile] drawing operations,
 * and many more.
 *
 * You can consider a [TileGrid] as an easy-to-use **facade** for all of your tile grid
 * needs.
 *
 * **Note That** all [TileGrid]s have a [Layer] at index `0` that is used
 * for implementing the [DrawSurface] operations, and it can't be removed from the grid.
 *
 * In short all [TileGrid]s have at least **one** [Layer] in them. Since this [Layer]
 * is not added by the user, no [LayerHandle] is present, and this is what ensures
 * that it cannot be removed.
 *
 * @see AnimationRunner
 * @see Clearable
 * @see Closeable
 * @see DrawSurface
 * @see Layerable
 * @see TypingSupport
 * @see UIEventSource
 * @see ViewContainer
 */
public interface TileGrid : AnimationRunner, Clearable, Closeable, DrawSurface, Layerable,
    TypingSupport, UIEventSource, ViewContainer {

    public val widthInPixels: Int
        get() = tileset.width * width

    public val heightInPixels: Int
        get() = tileset.height * height

    /**
     * Captures what this grid shows right now as one [TileGraphics] of the grid's size:
     * every layer, including the components of a displayed [org.hexworks.zircon.api.screen.Screen],
     * flattened the way a renderer draws them. Each cell holds the topmost opaque tile and
     * the see-through tiles above it (as a [org.hexworks.zircon.api.data.StackedTile] when
     * there is more than one). A tile from a layer that uses another tileset than the grid
     * keeps that tileset. The cursor is not part of the capture.
     *
     * The result is a copy: changing the grid afterwards does not change it.
     */
    public fun captureFrame(): TileGraphics

}
