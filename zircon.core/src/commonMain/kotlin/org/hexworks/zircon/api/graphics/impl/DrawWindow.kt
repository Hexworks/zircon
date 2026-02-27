package org.hexworks.zircon.api.graphics.impl

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.builder.graphics.characterTileString
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position.Companion.ZERO
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.data.extensions.min
import org.hexworks.zircon.api.data.extensions.toSize
import org.hexworks.zircon.api.extensions.isEmpty
import org.hexworks.zircon.api.extensions.isNotEmpty
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.extensions.toDrawWindow

/**
 * This function can be used to create an editable "window" over the underlying [TileGraphics].
 * Writing is restricted to the area represented by `rect` so if `rect` consists
 * of Position(1, 1) and Size(2, 2), the resulting [TileGraphics] will have a size
 * of (2, 2) and writing to it will write to the original graphics' surface, offset
 * by Position(1, 1). Note that the contents of the two objects are shared so edits
 * will be visible in both.
 */
//! TODO: move this to internal?
class DrawWindow(
    boundable: Boundable,
    private val backend: TileGraphics
) : TileComposite {

    override val size = boundable.size
    override val tiles: Map<Position, Tile>
        get() {
            val result = mutableMapOf<Position, Tile>()
            positions.forEach { pos ->
                val tile = backend.getTileAtOrNull(pos + offset)
                if (tile != null && tile.isNotEmpty) {
                    result[pos] = tile
                }
            }
            return result
        }

    private val offset = boundable.position

    private val positions = size.fetchPositions().toSet()
    private val offsetPositions = size.fetchPositions().map { pos -> pos + offset }.toSet()

    init {
        require(size <= backend.size) {
            "The size of a sub tile graphics can't be bigger than the original tile graphics."
        }
        require(offset.toSize() + size <= backend.size) {
            "sub tile graphics offset ($offset) and size ($size)" +
                    " is too big for backend size '${backend.size}'."
        }
    }

    /**
     * Returns the [Tile] stored at a particular position or `null`
     * if there is no such [Tile].
     */
    override fun getTileAtOrNull(position: Position): Tile? {
        return backend.getTileAtOrNull(position + offset)
    }

    /**
     * Returns the [Tile] stored at a particular position or calls [orElse]
     * if there is no such [Tile].
     */
    override fun getTileAtOrElse(position: Position, orElse: (Position) -> Tile): Tile {
        return getTileAtOrNull(position) ?: orElse(position)
    }

    fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        backend.draw(tileMap, drawPosition + offset, drawArea)
    }

    fun draw(tile: Tile, drawPosition: Position = ZERO) {
        backend.draw(tile, drawPosition + offset)
    }

    fun draw(tileComposite: TileComposite, drawPosition: Position, drawArea: Size) {
        draw(tileComposite.tiles, drawPosition, drawArea)
    }

    fun draw(tileComposite: TileComposite) {
        draw(tileComposite.tiles, Position.DEFAULT_POSITION, size)
    }

    fun draw(tileComposite: TileComposite, drawPosition: Position = ZERO) {
        draw(tileComposite.tiles, drawPosition, size)
    }

    fun draw(tileMap: Map<Position, Tile>) {
        draw(tileMap, Position.DEFAULT_POSITION, size)
    }

    fun draw(tileMap: Map<Position, Tile>, drawPosition: Position = ZERO) {
        draw(tileMap, drawPosition, size)
    }

    fun fill(filler: Tile) {
        offsetPositions.forEach { pos ->
            val tile = backend.tiles[pos]
            if (tile == null || tile.isEmpty) {
                backend.draw(filler, pos)
            }
        }
    }

    fun transform(transformer: (Position, Tile) -> Tile) {
        positions.forEach { pos ->
            backend.getTileAtOrNull(pos + offset)?.let { tile ->
                draw(transformer(pos, tile), pos)
            }
        }
    }

    fun applyStyle(styleSet: StyleSet) {
        transform { _, tile ->
            tile.withStyle(styleSet)
        }
    }

    fun clear() {
        offsetPositions.forEach { pos ->
            backend.draw(Tile.empty(), pos)
        }
    }

    /**
     * Fills this [DrawWindow] with the given [text] and [style].
     * Overwrites any existing content.
     */
    fun fillWithText(
        text: String,
        style: StyleSet,
        textWrap: TextWrap = TextWrap.WRAP
    ) {
        val size = this.size
        clear()
        draw(
            characterTileString {
                this.text = text
                this.size = size
                this.textWrap = textWrap
            }
        )
        applyStyle(style)
    }

    fun toDrawWindow(rect: Boundable) = backend.toDrawWindow(
        Boundable.create(
            position = offset + rect.position,
            size = size.min(rect.size)
        )
    )

}
