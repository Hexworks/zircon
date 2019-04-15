package org.hexworks.zircon.internal.tileset.transformer

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Crop
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture


class LibgdxCropTransformer: TextureTransformer<TextureRegion> {

    override fun transform(texture: TileTexture<TextureRegion>, tile: Tile): TileTexture<TextureRegion> {
        val (x, y, width, height) = tile.modifiers.first { it is Crop } as Crop
        val newTexture = texture.texture.apply { setRegion(x, y, width, height) }
        return DefaultTileTexture(
                width = texture.width,
                height = texture.height,
                texture = newTexture)
    }
}
