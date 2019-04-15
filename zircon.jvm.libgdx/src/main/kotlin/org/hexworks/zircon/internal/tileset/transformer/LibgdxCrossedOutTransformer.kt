package org.hexworks.zircon.internal.tileset.transformer

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.internal.renderer.toGDXColor

class LibgdxCrossedOutTransformer : TextureTransformer<TextureRegion> {

    override fun transform(texture: TileTexture<TextureRegion>, tile: Tile): TileTexture<TextureRegion> {
        return texture.also {
            it.texture.let { txt ->
                if (!txt.texture.textureData.isPrepared) {
                    txt.texture.textureData.prepare()
                }
                val pix = txt.texture.textureData.consumePixmap()
                with(pix) {
                    setColor(tile.foregroundColor.toGDXColor())
                    fillRectangle(0, txt.regionHeight / 2, txt.regionWidth, 2)
                }
                TextureRegion(Texture(pix))
            }
        }
    }
}