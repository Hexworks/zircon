package org.hexworks.zircon.internal.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.hexworks.zircon.api.data.PixelPosition
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.kotlin.map
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.RunTimeStats
import org.hexworks.zircon.internal.tileset.LibgdxTilesetLoader


@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class LibgdxRenderer(private val grid: TileGrid,
                     private val debug: Boolean = false) : Renderer {

    private var maybeBatch: Maybe<SpriteBatch> = Maybe.empty()

    private val tilesetLoader = LibgdxTilesetLoader()

    override fun create() {
        maybeBatch = Maybe.of(SpriteBatch())
    }

    override fun render() {
        if (debug) {
            RunTimeStats.addTimedStatFor("debug.render.time") {
                doRender()
            }
        } else doRender()
    }

    override fun close() {
        maybeBatch.map(SpriteBatch::dispose)
    }

    private fun doRender() {
        maybeBatch.map { batch ->
            Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
            batch.begin()

            renderTiles(
                    tiles = grid.createSnapshot(),
                    tileset = grid.tileset(),
                    offset = PixelPosition(0, 0),
                    batch = batch)
            grid.getLayers().forEach { layer ->
                renderTiles(
                        tiles = layer.createSnapshot(),
                        tileset = grid.tileset(),
                        offset = layer.position().toPixelPosition(grid.tileset()),
                        batch = batch)
            }
            batch.end()
        }
    }

    private fun renderTiles(tiles: Map<Position, Tile>,
                            tileset: TilesetResource,
                            offset: PixelPosition,
                            batch: SpriteBatch) {
        tiles.forEach { (pos, tile) ->
            val actualTileset = tilesetLoader.loadTilesetFrom(tileset)

            actualTileset.drawTile(tile, batch, pos)
        }
    }
}
