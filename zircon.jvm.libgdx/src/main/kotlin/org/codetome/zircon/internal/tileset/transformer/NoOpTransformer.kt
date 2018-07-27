package org.codetome.zircon.internal.tileset.transformer

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.TileTextureTransformer

class NoOpTransformer : TileTextureTransformer<TextureRegion> {
    override fun transform(texture: TileTexture<TextureRegion>, tile: Tile) = texture
}
