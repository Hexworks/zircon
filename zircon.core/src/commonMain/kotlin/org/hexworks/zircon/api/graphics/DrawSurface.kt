package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile

/**
 * A [DrawSurface] is a [TileComposite] which implements draw and mutation operations such as:
 * - [draw]
 * - [fill]
 * - [transform]
 * - [applyStyle] and
 * - [clear]
 * A [DrawSurface] also has its own [tileset].
 */
interface DrawSurface : Clearable, TileComposite, TilesetOverride {

    /**
     * Draws the given [Tile] on this [TileGraphics] at the given [drawPosition].
     * Drawing the empty tile ([Tile.empty]) will result in the deletion of the
     * [Tile] at [drawPosition].
     */
    fun draw(
        tile: Tile,
        drawPosition: Position
    )

    /**
     * Draws the given [tileMap] onto this [TileGraphics]. If the [tileMap] has [Tile]s
     * which are not contained within the [size] of this [TileGraphics] they will be ignored.
     * @param tileMap [Position] -> [Tile] mappings which contains the [Tile]s to draw.
     *                 the [Positions] will be offset with [drawPosition] when drawing
     * @param drawPosition the starting position of the drawing relative to the top left
     *                     corner of this [TileGraphics]. The default is [Positions.zero].
     * @param drawArea the sub-section of the [tileMap] to which the [Tile]s should be drawn
     * Example: If this [DrawSurface] has the size of (3,3), [drawPosition] is (1,1) and
     * [drawArea] is (2,2) the following positions will be overwritten: [(1,1), (2,1), (1,2), (2,2)]
     */
    fun draw(
        tileMap: Map<Position, Tile>,
        drawPosition: Position,
        drawArea: Size
    )

    /**
     * Same as [draw] with 3 parameters, with the difference that [size] will be used for `drawArea`,
     * and [Position.zero] as `drawPosition`
     */
    fun draw(tileMap: Map<Position, Tile>)

    /**
     * Same as [draw] with 3 parameters, with the difference that [size] will be used for `drawArea`.
     */
    fun draw(
        tileMap: Map<Position, Tile>,
        drawPosition: Position
    )

    /**
     * Same as [draw] with `tileMap`, but [TileComposite.tiles] will be use as the [Map].
     */
    fun draw(tileComposite: TileComposite)

    /**
     * Same as [draw] with `tileMap`, but [TileComposite.tiles] will be use as the [Map].
     */
    fun draw(
        tileComposite: TileComposite,
        drawPosition: Position
    )

    /**
     * Same as [draw] with `tileMap`, but [TileComposite.tiles] will be use as the [Map].
     */
    fun draw(
        tileComposite: TileComposite,
        drawPosition: Position,
        drawArea: Size
    )

    /**
     * Transforms all of the [Tile]s in this [TileGraphics] with the given
     * [transformer] and overwrites them with the results of calling
     * [transformer].
     */
    fun transform(transformer: (Position, Tile) -> Tile)

    /**
     * Applies the given [styleSet] to all currently present [Tile]s in this
     * [TileGraphics].
     * If you want to apply a style to a subset only take a look at
     * [TileGraphics.toSubTileGraphics].
     */
    fun applyStyle(styleSet: StyleSet)

    /**
     * Fills the empty parts of this [TileGraphics] with the given [filler] [Tile].
     * A [Position] is considered empty if there is no [Tile] in it.
     */
    fun fill(filler: Tile)
}
