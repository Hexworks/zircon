package org.codetome.zircon.internal.tileset.transformer

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.TileTextureTransformer
import org.codetome.zircon.internal.tileset.impl.DefaultTileTexture

class LibgdxTileTextureCloner : TileTextureTransformer<TextureRegion> {

    override fun transform(texture: TileTexture<TextureRegion>, tile: Tile): TileTexture<TextureRegion> {
        return DefaultTileTexture(
                width = texture.getWidth(),
                height = texture.getHeight(),
                texture = TextureRegion(texture.getTexture())
        )
    }
}
