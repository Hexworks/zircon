package org.codetome.zircon.gui.libgdx.impl

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import org.codetome.zircon.RunTimeStats
import org.codetome.zircon.api.data.AbsolutePosition
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.application.Renderer


@Suppress("UNCHECKED_CAST")
class LibgdxRenderer(private val grid: TileGrid,
                     private val debug: Boolean = false) : Renderer {

    lateinit var batch: SpriteBatch

    private val tilesetLoader = LibgdxTilesetLoader()


    override fun create() {
        batch = SpriteBatch()
    }

    override fun render() {
        if (debug) {
            RunTimeStats.addTimedStatFor("debug.render.time") {
                doRender()
            }
        } else doRender()
    }

    override fun dispose() {
        batch.dispose()
    }

    private fun doRender() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()

        renderTiles(grid.createSnapshot(), tilesetLoader.loadTilesetFrom(grid.tileset()), AbsolutePosition(0, 0))
        grid.getLayers().forEach { layer ->
            renderTiles(
                    tiles = layer.createSnapshot(),
                    tileset = tilesetLoader.loadTilesetFrom(layer.tileset()),
                    offset = layer.getPosition().toAbsolutePosition(tilesetLoader.loadTilesetFrom(grid.tileset())))
        }
        batch.end()
    }

    private fun renderTiles(tiles: Map<Position, Tile>,
                            tileset: Tileset<out Tile, TextureRegion>,
                            offset: AbsolutePosition) {
        tiles.forEach { (pos, tile) ->
            val p = pos.toAbsolutePosition(tileset) + offset
            val x = p.x.toFloat()
            val y = p.y.toFloat()
            val actualTileset: Tileset<Tile, TextureRegion> = if (tile is TilesetOverride) {
                tilesetLoader.loadTilesetFrom(tile.tileset())
            } else {
                tileset
            } as Tileset<Tile, TextureRegion>

            val width = actualTileset.width().toFloat()
            val height = actualTileset.height().toFloat()

            var texture = actualTileset.fetchTextureForTile(tile)
            val drawable = TextureRegionDrawable(texture.getTexture())
            val fr = tile.getForegroundColor().getRed().toFloat().div(255)
            val fg = tile.getForegroundColor().getGreen().toFloat().div(255)
            val fb = tile.getForegroundColor().getBlue().toFloat().div(255)
            val fa = tile.getForegroundColor().getAlpha().toFloat().div(255)

            val br = tile.getBackgroundColor().getRed().toFloat().div(255)
            val bg = tile.getBackgroundColor().getGreen().toFloat().div(255)
            val bb = tile.getBackgroundColor().getBlue().toFloat().div(255)
            val ba = tile.getBackgroundColor().getAlpha().toFloat().div(255)

            val tinted = drawable.tint(Color(fr, fg, fb, fa)) as SpriteDrawable
            tinted.draw(batch,
                    x,
                    y + height,
                    width,
                    height)
        }
    }
}
