package org.hexworks.zircon.api.graphics.extensions

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position.Companion.ZERO
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.extensions.isNotEmpty
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * Same as [draw] with 3 parameters, with the difference that [size] will be used for `drawArea`,
 * and [Position.zero] as `drawPosition`
 */
fun DrawSurface.draw(tileMap: Map<Position, Tile>) = draw(
    tileMap = tileMap,
    drawPosition = ZERO,
    drawArea = this.size
)

/**
 * Same as [draw] with 3 parameters, with the difference that [size] will be used for `drawArea`.
 */
fun DrawSurface.draw(
    tileMap: Map<Position, Tile>,
    drawPosition: Position
) = draw(
    tileMap = tileMap,
    drawPosition = drawPosition,
    drawArea = this.size
)

/**
 * Same as [draw] with `tileMap`, but [TileComposite.tiles] will be use as the [Map].
 */
fun DrawSurface.draw(tileComposite: TileComposite) = draw(
    tileComposite = tileComposite,
    drawPosition = ZERO,
    drawArea = tileComposite.size
)

/**
 * Same as [draw] with `tileMap`, but [TileComposite.tiles] will be use as the [Map].
 */
fun DrawSurface.draw(
    tileComposite: TileComposite,
    drawPosition: Position
) = draw(
    tileComposite = tileComposite,
    drawPosition = drawPosition,
    drawArea = tileComposite.size
)

/**
 * Same as [draw] with `tileMap`, but [TileComposite.tiles] will be used as the [Map].
 */
fun DrawSurface.draw(
    tileComposite: TileComposite,
    drawPosition: Position,
    drawArea: Size
) = draw(
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
fun DrawSurface.applyStyle(styleSet: StyleSet) {
    transform { _, tile ->
        tile.withStyle(styleSet)
    }
}

/**
 * Fills the empty parts of this [DrawSurface] with the given [filler] [Tile].
 * A [Position] is considered empty if there is no [Tile] in it.
 */
fun DrawSurface.fill(filler: Tile) {
    if (filler.isNotEmpty) {
        for (pos in size.fetchPositions()) {
            draw(filler, pos)
        }
    }
}

