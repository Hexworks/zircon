package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.DrawSurfaceSnapshot
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.toTileGraphics
import org.hexworks.zircon.api.extensions.toTileImage
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder

/**
 * Represents an object which can be drawn upon. A [DrawSurface] is the most
 * basic interface for all drawable surfaces which exposes simple get and set
 * functions for getting and setting [Tile]s and drawing [Drawable]s.
 * Each [DrawSurface] can use its own tileset, so it also implements
 * [TilesetOverride].
 */
interface DrawSurface : TileComposite, TilesetOverride {

    /**
     * Creates a snapshot of the current state of this [DrawSurface].
     * A snapshot is useful to see a consistent state of a [DrawSurface]
     * regardless of potential changes by other threads.
     */
    fun createSnapshot(): DrawSurfaceSnapshot

    /**
     * Sets a [Tile] at a specific position in the [DrawSurface] to [tile].
     * If the position is outside of the [DrawSurface]'s size, this method has no effect.
     * **Note that** repeated calls to [setTileAt] can be inefficient. Please refer
     * to the docs in [TileGraphicsBuilder] for more info.
     */
    fun setTileAt(position: Position, tile: Tile)

    /**
     * Transforms the [Tile] at the given [position]. Has no effect
     * if there is no [Tile] at the given [position].
     */
    fun transformTileAt(position: Position, tileTransformer: (Tile) -> Tile)

    /**
     * Converts this [DrawSurface] to a new [TileImage].
     */
    fun toTileImage(): TileImage {
        return if (this is TileImage) {
            this
        } else {
            val (tiles, tileset, size) = createSnapshot()
            tiles.toTileImage(size, tileset)
        }
    }

    /**
     * Converts this [DrawSurface] to a [TileGraphics].
     */
    fun toTileGraphics(): TileGraphics {
        return if (this is TileGraphics) {
            this
        } else {
            val (tiles, tileset, size) = createSnapshot()
            tiles.toTileGraphics(size, tileset)
        }
    }

    /**
     * Converts this [DrawSurface] to a [Layer]
     */
    fun toLayer(offset: Position): Layer {
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
     * Creates a copy of this [DrawSurface].
     */
    fun createCopy(): DrawSurface {
        val (tiles, tileset, size) = createSnapshot()
        return tiles.toTileGraphics(size, tileset)
    }

    /**
     * Returns a copy of this [DrawSurface] resized to a new size and using
     * [Tiles.empty] if the new size is larger than the old and
     * we need to fill in empty areas. The copy will be independent from the
     * one this method is invoked on, so modifying one will not affect the other.
     */
    fun resize(newSize: Size): DrawSurface = resize(newSize, Tiles.empty())

    /**
     * Returns a copy of this image resized to a new size and using
     * the specified [filler] [Tile] if the new size is larger than the old one
     * and we need to fill in empty areas. The copy will be independent from
     * the one this method is invoked on, so modifying one will not affect the other.
     */
    fun resize(newSize: Size, filler: Tile): DrawSurface {
        val (tiles, tileset) = createSnapshot()
        val result = tiles.filter { newSize.containsPosition(it.key) }
                .toTileGraphics(newSize, tileset)
        if (filler != Tiles.empty()) {
            newSize.fetchPositions().subtract(size.fetchPositions()).map {
                it to filler
            }.toTileImage(newSize, tileset).drawOnto(result)
        }
        return result
    }

    /**
     * Draws a [Drawable] onto this [DrawSurface]. If the destination [DrawSurface] is larger
     * than [drawable], the areas outside of the area that is written to will be untouched.
     * @param position the starting position of the drawing relative to this [DrawSurface]'s top left corner.
     */
    fun draw(drawable: Drawable, position: Position = Position.defaultPosition()) = drawable.drawOnto(this, position)

    /**
     * Fills the empty parts of this [DrawSurface] with the given `filler`.
     */
    fun fill(filler: Tile) {
        val (tiles, tileset) = createSnapshot()
        size.fetchPositions().minus(tiles.keys).map {
            it to filler
        }.toTileImage(size, tileset).drawOnto(this)
    }

    /**
     * Writes the given [text] at the given [position] using the given
     * [styleSet]
     */
    fun putText(text: String,
                position: Position = Position.zero(),
                styleSet: StyleSet = StyleSet.defaultStyle()) {
        val tileset = currentTileset()
        text.mapIndexed { col, char ->
            position.withRelativeX(col) to TileBuilder
                    .newBuilder()
                    .withStyleSet(styleSet)
                    .withCharacter(char)
                    .build()
        }.toTileImage(Sizes.create(text.length, 1), tileset)
                .drawOnto(this)
    }

    /**
     * Transforms all of the [Tile]s in this [DrawSurface] with the given
     * [transformer] and overwrites them with the results of calling
     * [transformer].
     */
    fun transform(transformer: (Tile) -> Tile) {
        val (tiles, tileset) = createSnapshot()
        tiles.map { (pos, tile) ->
            pos to transformer(tile)
        }.toTileImage(size, tileset).drawOnto(this)
    }

    // TODO: fix this, it is awful
    /**
     * Applies the given [styleSet] to all currently present [Tile]s in this
     * [DrawSurface] within the bounds delimited by `offset` and `size`.
     * Offset is used to offset the starting position from the top left position
     * while size is used to determine the region (down and right) to overwrite
     * relative to `offset`.
     * @param keepModifiers whether the modifiers currently present in the
     * target [Tile]s should be kept or not
     */
    fun applyStyle(styleSet: StyleSet,
                   rect: Rect = Rect.create(size = this.size),
                   keepModifiers: Boolean = false,
                   applyToEmptyCells: Boolean = true) {
        val offset = rect.position
        val size = rect.size
        val positions = if (applyToEmptyCells) {
            size.fetchPositions()
        } else {
            createSnapshot().tiles.keys
        }
        positions.forEach { pos ->
            pos.plus(offset).let { fixedPos ->
                getTileAt(fixedPos).map { tile: Tile ->
                    val oldMods = tile.styleSet.modifiers
                    val newTile = if (keepModifiers) {
                        tile.withStyle(styleSet.withAddedModifiers(oldMods))
                    } else {
                        tile.withStyle(styleSet)
                    }
                    setTileAt(fixedPos, newTile)
                }
            }
        }
    }
}
