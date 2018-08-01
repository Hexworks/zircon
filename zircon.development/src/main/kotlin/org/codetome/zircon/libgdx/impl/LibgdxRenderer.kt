package org.codetome.zircon.libgdx.impl

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import org.codetome.zircon.api.data.AbsolutePosition
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.Tileset
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import org.codetome.zircon.api.application.Renderer


@Suppress("UNCHECKED_CAST")
class LibgdxRenderer(private val grid: TileGrid<out Any, TextureRegion>) : Renderer {

    lateinit var batch: SpriteBatch

    private val tileset = grid.tileset() as Tileset<Any, TextureRegion>


    override fun create() {
        batch = SpriteBatch()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()

        renderTiles(grid.createSnapshot(), tileset, AbsolutePosition(0, 0))
        grid.getLayers().forEach { layer ->
            renderTiles(
                    tiles = layer.createSnapshot(), // TODO: fix cat
                    tileset = layer.tileset() as Tileset<Any, TextureRegion>,
                    offset = layer.getPosition().toAbsolutePosition(tileset))
        }
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }

    private fun renderTiles(tiles: Map<Position, Tile<out Any>>,
                            tileset: Tileset<Any, TextureRegion>,
                            offset: AbsolutePosition) {
        tiles.forEach { (pos, tile) ->
            val actualTile = tile as Tile<Any>
            val p = pos.toAbsolutePosition(tileset) + offset
            val x = p.x.toFloat()
            val y = p.y.toFloat()
            val actualTileset: Tileset<Any, TextureRegion> = if (actualTile is TilesetOverride<*, *>) {
                actualTile.tileset() as Tileset<Any, TextureRegion>
            } else {
                tileset
            }

            val width = tileset.width().toFloat()
            val height = tileset.height().toFloat()

            var texture = actualTileset.fetchTextureForTile(actualTile)
            val drawable = TextureRegionDrawable(texture.getTexture())
            val fr = actualTile.getForegroundColor().getRed().toFloat().div(255)
            val fg = actualTile.getForegroundColor().getGreen().toFloat().div(255)
            val fb = actualTile.getForegroundColor().getBlue().toFloat().div(255)
            val fa = actualTile.getForegroundColor().getAlpha().toFloat().div(255)

            val br = actualTile.getBackgroundColor().getRed().toFloat().div(255)
            val bg = actualTile.getBackgroundColor().getGreen().toFloat().div(255)
            val bb = actualTile.getBackgroundColor().getBlue().toFloat().div(255)
            val ba = actualTile.getBackgroundColor().getAlpha().toFloat().div(255)

            val tinted = drawable.tint(Color(fr, fg, fb, fa)) as SpriteDrawable
            tinted.draw(batch,
                    x,
                    y + height,
                    width,
                    height)
        }
    }
}
