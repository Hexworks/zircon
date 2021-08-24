package org.hexworks.zircon.internal.tileset.transformer

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture

class LibgdxTextureCloner : TextureTransformer<TextureRegion> {

    override val targetType = TextureRegion::class

    override fun transform(texture: TileTexture<TextureRegion>, tile: Tile): TileTexture<TextureRegion> {
        return DefaultTileTexture(
            width = texture.width,
            height = texture.height,
            texture = TextureRegion(texture.texture),
            cacheKey = tile.cacheKey
        )
    }
}
