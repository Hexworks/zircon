package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.DrawSurfaceState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.toTileGraphics
import org.hexworks.zircon.api.extensions.toTileImage

/**
 * Represents an object which can be drawn upon. A [DrawSurface] is the most
 * basic interface for all drawable surfaces which exposes simple get and set
 * functions for getting and setting [Tile]s and drawing [TileComposite]s.
 * Each [DrawSurface] can use its own tileset, so it also implements
 * [TilesetOverride].
 */
interface DrawSurface : TileComposite, TilesetOverride {

    /**
     * Holds a snapshot of the current state of this [DrawSurface].
     */
    val state: DrawSurfaceState

    /**
     * Creates a **new** [TileImage] from the contents of this
     * [DrawSurface]. The result is detached from the current one
     * which means that changes to one are not reflected in the
     * other.
     */
    fun toTileImage(): TileImage {
        return if (this is TileImage) {
            this
        } else {
            val (tiles, tileset, size) = state
            tiles.toTileImage(size, tileset)
        }
    }

    /**
     * Creates a **new** [TileGraphics] from the contents of this
     * [DrawSurface]. The result is detached from the current one
     * which means that changes to one are not reflected in the
     * other.
     */
    fun toTileGraphics(): TileGraphics {
        return if (this is TileGraphics) {
            this
        } else {
            val (tiles, tileset, size) = state
            tiles.toTileGraphics(size, tileset)
        }
    }

    /**
     * Creates a **new** [Layer] from the contents of this
     * [DrawSurface]. The result is detached from the current one
     * which means that changes to one are not reflected in the
     * other.
     * @param offset the offset for the new [Layer], `(0, 0)` by default
     */
    fun toLayer(offset: Position = Positions.zero()): Layer {
        return if (this is Layer) {
            this.apply {
                moveTo(offset)
            }
        } else {
            LayerBuilder.newBuilder()
                    .withOffset(offset)
                    .withTileGraphics(toTileGraphics())
                    .build()
        }
    }

    /**
     * Creates a **new** copy from the contents of this
     * [DrawSurface]. The result is detached from the current one
     * which means that changes to one are not reflected in the
     * other.
     */
    fun createCopy(): DrawSurface {
        val (tiles, tileset, size) = state
        return tiles.toTileGraphics(size, tileset)
    }

    /**
     * Returns a copy of this [DrawSurface] resized to a new size and using
     * [Tiles.empty] if the new size is larger than the old and
     * it needs to fill in empty areas. The copy will be independent from the
     * one this method is invoked on, so modifying one will not affect the other.
     */
    fun toResized(newSize: Size): DrawSurface = toResized(newSize, Tiles.empty())

    /**
     * Returns a copy of this image resized to a new size and using
     * the specified [filler] [Tile] if the new size is larger than the old one
     * and we need to fill in empty areas. The copy will be independent from
     * the one this method is invoked on, so modifying one will not affect the other.
     */
    fun toResized(newSize: Size, filler: Tile): DrawSurface {
        val (tiles, tileset) = state
        val newTiles = mutableMapOf<Position, Tile>()
        tiles.forEach { (pos, tile) ->
            if (newSize.containsPosition(pos)) newTiles[pos] = tile
        }
        if (filler != Tiles.empty()) {
            newSize.fetchPositions().subtract(size.fetchPositions()).forEach { pos ->
                newTiles[pos] = filler
            }
        }
        return newTiles.toTileGraphics(newSize, tileset)
    }

    /**
     * Sets a [Tile] at a specific position in the [DrawSurface] to [tile].
     * If the position is outside of the [DrawSurface]'s size, this method has no effect.
     * **Note that** repeated calls to [setTileAt] can be inefficient, depending on the
     * implementation of [DrawSurface] you are using. Please refer to the docs in
     * [TileGraphicsBuilder] for more info.
     */
    @Deprecated("Use draw instead", replaceWith = ReplaceWith("draw(tile, position)"))
    fun setTileAt(position: Position, tile: Tile) = draw(tile, position)

    /**
     * Draws a [TileComposite] onto this [DrawSurface]. If the [tileComposite] has [Tile]s
     * which are not contained within the [size] of this [DrawSurface] they will be ignored.
     * The [tileComposite] will get drawn to the top left corner of this [DrawSurface].
     */
    fun draw(tileComposite: TileComposite) = draw(
            tileComposite = tileComposite,
            drawPosition = Position.zero(),
            drawArea = tileComposite.size)

    /**
     * Draws a [TileComposite] onto this [DrawSurface]. If the [tileComposite] has [Tile]s
     * which are not contained within the [size] of this [DrawSurface] they will be ignored.
     * @param drawPosition the starting position of the drawing relative to this [DrawSurface]'s top left corner.
     *                 the default is [Positions.zero]
     */
    fun draw(tileComposite: TileComposite,
             drawPosition: Position) = draw(
            tileComposite = tileComposite,
            drawPosition = drawPosition,
            drawArea = tileComposite.size)

    /**
     * Draws a [TileComposite] onto this [DrawSurface]. If the [tileComposite] has [Tile]s
     * which are not contained within the [drawArea] of this [DrawSurface] they will be ignored.
     * @param drawPosition the starting position of the drawing relative to this [DrawSurface]'s top left corner.
     *                 the default is [Positions.zero]
     * @param drawArea the sub-section of the [tileComposite] form which the [Tile]s should be drawn
     *             the default is the size of this [DrawSurface]
     */
    fun draw(tileComposite: TileComposite,
             drawPosition: Position,
             drawArea: Size) = draw(
            tileMap = tileComposite.tiles,
            drawPosition = drawPosition,
            drawArea = drawArea)

    /**
     * Draws the given [tileMap] onto this [DrawSurface]. If the [tileMap] have [Tile]s
     * which are not contained within the [size] of this [DrawSurface] they will be ignored.
     */
    fun draw(tileMap: Map<Position, Tile>) = draw(
            tileMap = tileMap,
            drawPosition = Position.zero(),
            drawArea = this.size)

    /**
     * Draws the given [tileMap] onto this [DrawSurface]. If the [tileMap] have [Tile]s
     * which are not contained within the [size] of this [DrawSurface] they will be ignored.
     * @param drawPosition the starting position of the drawing relative to this [DrawSurface]'s top left corner.
     *                 the default is [Positions.zero]
     */
    fun draw(tileMap: Map<Position, Tile>,
             drawPosition: Position) = draw(
            tileMap = tileMap,
            drawPosition = drawPosition,
            drawArea = this.size)

    /**
     * Draws the given [tileMap] onto this [DrawSurface]. If the [tileMap] have [Tile]s
     * which are not contained within the [size] of this [DrawSurface] they will be ignored.
     * @param drawPosition the starting position of the drawing relative to this [DrawSurface]'s top left corner.
     *                 the default is [Positions.zero]
     * @param drawArea the sub-section of the [tileMap] form which the [Tile]s should be drawn
     *             the default is the size of this [DrawSurface]
     */
    fun draw(tileMap: Map<Position, Tile>,
             drawPosition: Position,
             drawArea: Size)

    /**
     * Draws the given [Tile] on this [DrawSurface] at the given [drawPosition].
     * This function has no effect
     */
    fun draw(tile: Tile,
             drawPosition: Position)

    /**
     * Transforms the [Tile] at the given [position]. Has no effect
     * if there is no [Tile] at the given [position].
     */
    fun transformTileAt(position: Position, tileTransformer: (Tile) -> Tile)

    /**
     * Fills the empty parts of this [DrawSurface] with the given [filler] [Tile].
     */
    fun fill(filler: Tile)

    /**
     * Transforms all of the [Tile]s in this [DrawSurface] with the given
     * [transformer] and overwrites them with the results of calling
     * [transformer].
     */
    fun transform(transformer: (Tile) -> Tile) {
        val (tiles) = state
        draw(tiles.map { (pos, tile) ->
            pos to transformer(tile)
        }.toMap())
    }

    /**
     * Applies the given [styleSet] to all currently present [Tile]s in this
     * [DrawSurface].
     * If you want to apply a style to a subset only take a look at
     * [TileGraphics.toSubTileGraphics].
     */
    fun applyStyle(styleSet: StyleSet) {
        transform { it.withStyle(styleSet) }
    }
}
