package org.hexworks.zircon.api.graphics.impl

import org.hexworks.zircon.api.builder.graphics.characterTileString
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.isEmpty
import org.hexworks.zircon.api.extensions.isNotEmpty
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * This function can be used to create an editable "window" over the underlying [TileGraphics].
 * Writing is restricted to the area represented by `rect` so if `rect` consists
 * of Position(1, 1) and Size(2, 2), the resulting [TileGraphics] will have a size
 * of (2, 2) and writing to it will write to the original graphics' surface, offset
 * by Position(1, 1). Note that the contents of the two objects are shared so edits
 * will be visible in both.
 */
class DrawWindow(
    rect: Rect,
    private val backend: TileGraphics
) : TileComposite {

    override val size = rect.size
    override val width = size.width
    override val height = size.height
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

    private val offset = rect.position

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

    fun draw(tile: Tile, drawPosition: Position) {
        backend.draw(tile, drawPosition + offset)
    }

    fun draw(tileComposite: TileComposite, drawPosition: Position, drawArea: Size) {
        draw(tileComposite.tiles, drawPosition, drawArea)
    }

    fun draw(tileComposite: TileComposite) {
        draw(tileComposite.tiles, Position.defaultPosition(), size)
    }

    fun draw(tileComposite: TileComposite, drawPosition: Position) {
        draw(tileComposite.tiles, drawPosition, size)
    }

    fun draw(tileMap: Map<Position, Tile>) {
        draw(tileMap, Position.defaultPosition(), size)
    }

    fun draw(tileMap: Map<Position, Tile>, drawPosition: Position) {
        draw(tileMap, drawPosition, size)
    }

    fun fill(filler: Tile) {
        offsetPositions.forEach { pos ->
            val tile = backend.tiles[pos];
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
        transform { _, tile -> tile.withStyle(styleSet) }
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

    fun toDrawWindow(rect: Rect) = backend.toDrawWindow(
        Rect.create(
            position = offset + rect.position,
            size = size.min(rect.size)
        )
    )

}
