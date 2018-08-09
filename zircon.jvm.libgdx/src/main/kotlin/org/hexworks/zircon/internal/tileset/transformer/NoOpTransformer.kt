package org.hexworks.zircon.internal.tileset.transformer

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.TileTextureTransformer

class NoOpTransformer : TileTextureTransformer<TextureRegion> {
    override fun transform(texture: TileTexture<TextureRegion>, tile: Tile) = texture
}
