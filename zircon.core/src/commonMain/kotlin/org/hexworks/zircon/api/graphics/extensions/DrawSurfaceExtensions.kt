package org.hexworks.zircon.api.graphics.extensions

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position.Companion.ZERO
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.extensions.isNotEmpty
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * Same as [draw] with 3 parameters, with the difference that [size] will be used for `drawArea`,
 * and [Position.zero] as `drawPosition`
 */
public fun DrawSurface.draw(tileMap: Map<Position, Tile>): Unit = draw(
    tileMap = tileMap,
    drawPosition = ZERO,
    drawArea = this.size
)

/**
 * Same as [draw] with 3 parameters, with the difference that [size] will be used for `drawArea`.
 */
public fun DrawSurface.draw(
    tileMap: Map<Position, Tile>,
    drawPosition: Position
): Unit = draw(
    tileMap = tileMap,
    drawPosition = drawPosition,
    drawArea = this.size
)

/**
 * Same as [draw] with `tileMap`, but [TileComposite.tiles] will be use as the [Map].
 */
public fun DrawSurface.draw(tileComposite: TileComposite): Unit = draw(
    tileComposite = tileComposite,
    drawPosition = ZERO,
    drawArea = tileComposite.size
)

/**
 * Same as [draw] with `tileMap`, but [TileComposite.tiles] will be use as the [Map].
 */
public fun DrawSurface.draw(
    tileComposite: TileComposite,
    drawPosition: Position
): Unit = draw(
    tileComposite = tileComposite,
    drawPosition = drawPosition,
    drawArea = tileComposite.size
)

/**
 * Same as [draw] with `tileMap`, but [TileComposite.tiles] will be used as the [Map].
 */
public fun DrawSurface.draw(
    tileComposite: TileComposite,
    drawPosition: Position,
    drawArea: Size
): Unit = draw(
    tileMap = tileComposite.tiles,
    drawPosition = drawPosition,
    drawArea = drawArea
)

/**
 * Applies the given [styleSet] to all currently present [Tile]s in this
 * [DrawSurface].
 * If you want to apply a style to a subset only take a look at
 * [TileGraphics.toDrawWindow].
 */
public fun DrawSurface.applyStyle(styleSet: StyleSet) {
    transform { _, tile ->
        tile.withStyle(styleSet)
    }
}

/**
 * Fills the empty parts of this [DrawSurface] with the given [filler] [Tile].
 * A [Position] is considered empty if there is no [Tile] in it.
 */
public fun DrawSurface.fill(filler: Tile) {
    if (filler.isNotEmpty) {
        for (pos in size.fetchPositions()) {
            if (getTileAtOrNull(pos)?.isNotEmpty != true) {
                draw(filler, pos)
            }
        }
    }
}


/**
 * Draws the outline of a box onto this [DrawSurface] using the glyphs of the given [boxType]
 * and the given [style]. The box occupies the area starting at [position] with the given [size],
 * which is the whole surface by default. Only the border is drawn, the interior is left untouched.
 */
public fun DrawSurface.drawBox(
    boxType: BoxType,
    style: StyleSet = StyleSet.defaultStyle(),
    position: Position = ZERO,
    size: Size = this.size
): Unit = draw(boxTiles(boxType, style, size), position, size)

/**
 * Same as [DrawSurface.drawBox], for a [DrawWindow].
 */
public fun DrawWindow.drawBox(
    boxType: BoxType,
    style: StyleSet = StyleSet.defaultStyle(),
    position: Position = ZERO,
    size: Size = this.size
): Unit = draw(boxTiles(boxType, style, size), position, size)

/**
 * Creates the [Tile]s of the outline of a box of the given [size] using the glyphs of
 * [boxType] and the given [style], keyed by their position relative to the top left corner.
 */
public fun boxTiles(
    boxType: BoxType,
    style: StyleSet = StyleSet.defaultStyle(),
    size: Size
): Map<Position, Tile> {
    if (size.width < 1 || size.height < 1) return emptyMap()
    val vertical = characterTile {
        styleSet = style
        character = boxType.vertical
    }
    val horizontal = vertical.withCharacter(boxType.horizontal)
    val right = size.width - 1
    val bottom = size.height - 1
    val tiles = mutableMapOf<Position, Tile>()
    for (x in 1 until right) {
        tiles[Position(x, 0)] = horizontal
        tiles[Position(x, bottom)] = horizontal
    }
    for (y in 1 until bottom) {
        tiles[Position(0, y)] = vertical
        tiles[Position(right, y)] = vertical
    }
    tiles[Position(0, 0)] = vertical.withCharacter(boxType.topLeft)
    tiles[Position(right, 0)] = vertical.withCharacter(boxType.topRight)
    tiles[Position(0, bottom)] = vertical.withCharacter(boxType.bottomLeft)
    tiles[Position(right, bottom)] = vertical.withCharacter(boxType.bottomRight)
    return tiles
}
