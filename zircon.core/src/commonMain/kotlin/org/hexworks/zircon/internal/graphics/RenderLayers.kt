package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.StackedTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.util.reportError

/**
 * One rendered [Renderable]: its graphics plus the bounds and tileset the tiles draw with.
 * The ints are cached because the per-cell walk in [forEachVisibleTile] runs for every
 * cell of the grid on every frame.
 */
internal class RenderLayer(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    val graphics: TileGraphics,
    val tileset: TilesetResource,
)

/**
 * Renders every [Renderable] into a [RenderLayer], bottom first. A hidden renderable, or
 * one whose renderer throws, leaves a blank layer so the others still draw; the error is
 * reported through [reportError].
 */
internal fun List<Renderable>.toRenderLayers(): List<RenderLayer> = map { renderable ->
    val tg = if (renderable.isHidden) blank(renderable) else try {
        renderable.render()
    } catch (e: Exception) {
        reportError("Renderer of $renderable", e)
        blank(renderable)
    }
    val position = renderable.position
    RenderLayer(position.x, position.y, tg.size.width, tg.size.height, tg, tg.tileset)
}

private fun blank(renderable: Renderable): TileGraphics = FastTileGraphics(
    initialSize = renderable.size,
    initialTileset = renderable.tileset
)

/**
 * Walks the layers at [pos] from the top down and calls [visit] with every tile that can
 * show there, top first, together with the tileset of its layer. Stacked tiles are
 * flattened. The walk stops at the first tile for which [visit] returns `true` (the
 * tile is opaque): nothing below it can show.
 */
internal inline fun List<RenderLayer>.forEachVisibleTile(
    pos: Position,
    visit: (tile: Tile, layerTileset: TilesetResource) -> Boolean
) {
    for (i in size - 1 downTo 0) {
        val layer = this[i]
        val x = pos.x - layer.x
        val y = pos.y - layer.y
        if (x < 0 || y < 0 || x >= layer.width || y >= layer.height) continue
        val tile = layer.graphics.getTileAtOrNull(if (layer.x == 0 && layer.y == 0) pos else Position(x, y))
            ?: continue
        if (tile is StackedTile) {
            val flattened = tile.flatten()
            for (j in flattened.size - 1 downTo 0) {
                if (visit(flattened[j], layer.tileset)) return
            }
        } else if (visit(tile, layer.tileset)) {
            return
        }
    }
}

/** The leaves of a [StackedTile], bottom first; any other tile is its own single leaf. */
internal fun Tile.flatten(): List<Tile> = when (this) {
    is StackedTile -> tiles.flatMap { it.flatten() }
    else -> listOf(this)
}

/**
 * The tileset this tile draws with: its own when it carries one, otherwise the
 * [layerTileset] of the layer it sits on.
 */
internal fun Tile.tilesetOrElse(layerTileset: TilesetResource): TilesetResource =
    if (this is TilesetHolder && tileset.isNotUnknown) tileset else layerTileset
