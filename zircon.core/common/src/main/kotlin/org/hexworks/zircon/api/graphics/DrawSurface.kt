package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Snapshot
import org.hexworks.zircon.api.data.Tile

/**
 * Represents an object which can be drawn upon.
 * A [DrawSurface] is the most basic interface for all drawable surfaces
 * which exposes simple get and set functions for getting and setting
 * [Tile]s and drawing [Drawable]s. Each [DrawSurface] can use its own
 * tileset, so it also implements [TilesetOverride].
 */
interface DrawSurface : TileComposite, TilesetOverride {

    /**
     * Sets a [Tile] at a specific position in the [DrawSurface] to `tile`.
     * If the position is outside of the [DrawSurface]'s size, this method has no effect.
     * Note that if this [DrawSurface] already has the given [Tile] on the supplied [Position]
     * nothing will change.
     */
    fun setTileAt(position: Position, tile: Tile)

    /**
     * Transforms the [Tile] at the given [position]. Has no effect
     * if there is no [Tile] at the given [position].
     */
    fun transformTileAt(position: Position, fn: (Tile) -> Tile)

    /**
     * Creates a snapshot of the current state of this [DrawSurface].
     * A snapshot is useful to see a consistent state of a [DrawSurface]
     * regardless of potential changes by other threads.
     */
    fun createSnapshot(): Snapshot

    /**
     * Draws a [Drawable] onto this [DrawSurface]. If the destination [DrawSurface] is larger than
     * this [Drawable], the areas outside of the area that is written to will be untouched.
     * @param position the starting position of the drawing relative to the [DrawSurface]'s top left corner.
     */
    fun draw(drawable: Drawable, position: Position = Position.defaultPosition()) {
        drawable.drawOnto(this, position)
    }
}
