package org.hexworks.zircon.internal.renderer.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.StackedTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.modifier.TileTransformModifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.graphics.FastTileGraphics
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.platform.util.SystemUtils

/**
 * @param T the type of the draw surface (Swing, for example will use `Graphics2D`)
 */
abstract class BaseRenderer<T : Any, A : Application>(
    protected val tileGrid: InternalTileGrid,
    private val tilesetLoader: TilesetLoader<T>
) : Renderer<A> {

    private val isClosed = false.toProperty()
    private val gridPositions = tileGrid.size.fetchPositions().toList()
    private var blinkOn = true
    private var lastBlink: Long = SystemUtils.getCurrentTimeMs()
    private val config = tileGrid.config
    private var lastRender: Long = lastBlink

    override val closedValue: ObservableValue<Boolean>
        get() = isClosed

    final override fun close() {
        isClosed.value = true
        doClose()
    }

    final override fun render() {
        if (closed.not()) {
            val now = SystemUtils.getCurrentTimeMs()
            processInputEvents()
            tileGrid.updateAnimations(now, tileGrid)
            handleBlink(now)
            doRender(now)
            lastRender = now
        }
    }

    // TODO: use a drawing strategy here
    protected fun drawTiles(graphics: T) {
        val layers = fetchLayers()
        val tiles = mutableListOf<Pair<Tile, TilesetResource>>()
        gridPositions.forEach { pos ->
            tiles@ for (i in layers.size - 1 downTo 0) {
                val (layerPos, layer) = layers[i]
                val toRender = layer.getTileAtOrNull(pos - layerPos)?.tiles() ?: listOf()
                for (j in toRender.size - 1 downTo 0) {
                    val tile = toRender[j]
                    val tileset = tile.finalTileset(layer)
                    tiles.add(0, tile to tileset)
                    if (tile.isOpaque) {
                        break@tiles
                    }
                }
            }

            var idx = 1
            for ((tile, tileset) in tiles) {
                var finalTile = tile
                if (shouldDrawCursor() && tileGrid.cursorPosition == pos && idx == tiles.size) {
                    finalTile = finalTile.withBackgroundColor(finalTile.foregroundColor)
                        .withForegroundColor(finalTile.backgroundColor)
                }
                renderTile(
                    graphics = graphics,
                    position = pos,
                    tile = finalTile,
                    tileset = tilesetLoader.loadTilesetFrom(tileset)
                )
                idx++
            }
            tiles.clear()
        }
    }

    private fun shouldDrawCursor(): Boolean {
        return tileGrid.isCursorVisible &&
                (config.isCursorBlinking.not() || config.isCursorBlinking && blinkOn)
    }

    protected abstract fun processInputEvents()

    protected abstract fun doRender(now: Long)

    protected open fun doClose() {
    }

    private fun renderTile(
        graphics: T,
        position: Position,
        tile: Tile,
        tileset: Tileset<T>
    ) {
        if (tile.isNotEmpty) {
            var finalTile = tile
            finalTile.modifiers.filterIsInstance<TileTransformModifier<CharacterTile>>().forEach { modifier ->
                if (modifier.canTransform(finalTile)) {
                    (finalTile as? CharacterTile)?.let {
                        finalTile = modifier.transform(it)
                    }
                }
            }
            finalTile = if (tile.isBlinking && blinkOn) {
                tile.withBackgroundColor(tile.foregroundColor)
                    .withForegroundColor(tile.backgroundColor)
            } else {
                tile
            }
            (
                    (finalTile as? TilesetHolder)?.let {
                        tilesetLoader.loadTilesetFrom(it.tileset)
                    } ?: tileset
                    ).drawTile(
                    tile = finalTile,
                    surface = graphics,
                    position = position
                )
        }
    }

    private fun handleBlink(now: Long) {
        if (now > lastBlink + config.blinkLengthInMilliSeconds) {
            blinkOn = !blinkOn
            lastBlink = now
        }
    }

    /**
     * Returns all the layers that should be rendered.
     */
    private fun fetchLayers(): List<Pair<Position, TileGraphics>> {
        return tileGrid.renderables.map { renderable ->
            val tg = FastTileGraphics(
                initialSize = renderable.size,
                initialTileset = renderable.tileset,
            )
            if (!renderable.isHidden) {
                renderable.render(tg)
            }
            renderable.position to tg
        }
    }

    private fun Tile.finalTileset(graphics: TileGraphics): TilesetResource {
        return if (this is TilesetHolder) {
            tileset
        } else graphics.tileset
    }

    private fun Tile.tiles(): List<Tile> = if (this is StackedTile) {
        tiles.flatMap { it.tiles() }
    } else listOf(this)
}