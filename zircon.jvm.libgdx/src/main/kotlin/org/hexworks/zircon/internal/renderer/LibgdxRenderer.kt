package org.hexworks.zircon.internal.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.AbsolutePosition
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.RunTimeStats
import org.hexworks.zircon.internal.tileset.LibgdxTilesetLoader


@Suppress("UNCHECKED_CAST")
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
                    tiles = grid.snapshot(),
                    tileset = tilesetLoader.loadTilesetFrom(grid.tileset()),
                    offset = AbsolutePosition(0, 0),
                    batch = batch)
            grid.getLayers().forEach { layer ->
                renderTiles(
                        tiles = layer.snapshot(),
                        tileset = tilesetLoader.loadTilesetFrom(layer.tileset()),
                        offset = layer.position().toAbsolutePosition(tilesetLoader.loadTilesetFrom(grid.tileset())),
                        batch = batch)
            }
            batch.end()
        }
    }

    private fun renderTiles(tiles: Map<Position, Tile>,
                            tileset: Tileset<TextureRegion>,
                            offset: AbsolutePosition,
                            batch: SpriteBatch) {
        tiles.forEach { (pos, tile) ->
            val p = pos.toAbsolutePosition(tileset) + offset
            val x = p.x.toFloat()
            val y = p.y.toFloat()
            val actualTileset: Tileset<TextureRegion> = if (tile is TilesetOverride) {
                tilesetLoader.loadTilesetFrom(tile.tileset())
            } else {
                tileset
            }

            val width = actualTileset.width().toFloat()
            val height = actualTileset.height().toFloat()

            val texture = actualTileset.fetchTextureForTile(tile)
            val drawable = TextureRegionDrawable(texture.getTexture())
            val fr = tile.getForegroundColor().getRed().toFloat().div(255)
            val fg = tile.getForegroundColor().getGreen().toFloat().div(255)
            val fb = tile.getForegroundColor().getBlue().toFloat().div(255)
            val fa = tile.getForegroundColor().getAlpha().toFloat().div(255)

//            val br = tile.backgroundColor().getRed().toFloat().div(255)
//            val bg = tile.backgroundColor().getGreen().toFloat().div(255)
//            val bb = tile.backgroundColor().getBlue().toFloat().div(255)
//            val ba = tile.backgroundColor().getAlpha().toFloat().div(255)

            val tinted = drawable.tint(Color(fr, fg, fb, fa)) as SpriteDrawable
            tinted.draw(batch,
                    x,
                    y + height,
                    width,
                    height)
        }
    }
}
