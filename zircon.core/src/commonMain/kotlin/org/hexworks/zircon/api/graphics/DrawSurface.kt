package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile

interface DrawSurface : Clearable, TileComposite, TilesetOverride {

    /**
     * Draws the given [tileMap] onto this [TileGraphics]. If the [tileMap] have [Tile]s
     * which are not contained within the [size] of this [TileGraphics] they will be ignored.
     * @param drawPosition the starting position of the drawing relative to the top left
     *                     corner of this [TileGraphics]. the default is [Positions.zero]
     * @param drawArea the sub-section of the [tileMap] form which the [Tile]s should be drawn
     */
    fun draw(tileMap: Map<Position, Tile>,
             drawPosition: Position,
             drawArea: Size)

    // TODO: delete tile when drawing empty tile
    /**
     * Draws the given [Tile] on this [TileGraphics] at the given [drawPosition].
     * Drawing the empty tile ([Tile.empty]) will result in the deletion of the
     * [Tile] at [drawPosition].
     */
    fun draw(tile: Tile,
             drawPosition: Position)

    /**
     * Draws a [TileComposite] onto this [TileGraphics]. If the [tileComposite] has [Tile]s
     * which are not contained within the [size] of this [TileGraphics] they will be ignored.
     * The [tileComposite] will get drawn to the top left corner of this [TileGraphics].
     */
    fun draw(tileComposite: TileComposite)

    /**
     * Draws a [TileComposite] onto this [TileGraphics]. If the [tileComposite] has [Tile]s
     * which are not contained within the [size] of this [TileGraphics] they will be ignored.
     * @param drawPosition the starting position of the drawing relative to this [TileGraphics]'s top left corner.
     *                 the default is [Positions.zero]
     */
    fun draw(tileComposite: TileComposite,
             drawPosition: Position)

    /**
     * Draws a [TileComposite] onto this [TileGraphics]. If the [tileComposite] has [Tile]s
     * which are not contained within the [drawArea] of this [TileGraphics] they will be ignored.
     * @param drawPosition the starting position of the drawing relative to this [TileGraphics]'s top left corner.
     *                 the default is [Positions.zero]
     * @param drawArea the sub-section of the [tileComposite] form which the [Tile]s should be drawn
     *             the default is the size of this [DrawSurface]
     */
    fun draw(tileComposite: TileComposite,
             drawPosition: Position,
             drawArea: Size)

    /**
     * Draws the given [tileMap] onto this [TileGraphics]. If the [tileMap] have [Tile]s
     * which are not contained within the [size] of this [TileGraphics] they will be ignored.
     */
    fun draw(tileMap: Map<Position, Tile>)

    /**
     * Draws the given [tileMap] onto this [TileGraphics]. If the [tileMap] have [Tile]s
     * which are not contained within the [size] of this [TileGraphics] they will be ignored.
     * @param drawPosition the starting position of the drawing relative to this [TileGraphics]'s top left corner.
     *                 the default is [Positions.zero]
     */
    fun draw(tileMap: Map<Position, Tile>,
             drawPosition: Position)

    /**
     * Transforms the [Tile] at the given [position] using the given
     * [tileTransformer].
     */
    fun transformTileAt(position: Position, tileTransformer: (Tile) -> Tile)

    /**
     * Fills the empty parts of this [TileGraphics] with the given [filler] [Tile].
     * A [Position] is considered empty if there is no [Tile] in it.
     */
    fun fill(filler: Tile)

    /**
     * Creates a **new** [TileImage] from the contents of this
     * [TileGraphics]. The result is detached from the current one
     * which means that changes to one are not reflected in the
     * other.
     */
    fun toTileImage(): TileImage

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
     * Sets a [Tile] at a specific position in the [TileGraphics] to [tile].
     * If the position is outside of the [TileGraphics]'s size, this method has no effect.
     * **Note that** repeated calls to [setTileAt] can be inefficient, depending on the
     * implementation of [TileGraphics] you are using. Please refer to the docs in
     * [TileGraphicsBuilder] for more info.
     */
    @Deprecated("Use draw instead", replaceWith = ReplaceWith("draw(tile, position)"))
    fun setTileAt(position: Position, tile: Tile) = draw(tile, position)
}