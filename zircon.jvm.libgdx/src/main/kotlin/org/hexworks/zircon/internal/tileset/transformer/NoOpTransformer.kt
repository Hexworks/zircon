package org.hexworks.zircon.internal.tileset.transformer

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.TextureTransformer
import kotlin.reflect.KClass

class NoOpTransformer : TextureTransformer<TextureRegion> {
    override val targetType = TextureRegion::class
    override fun transform(texture: TileTexture<TextureRegion>, tile: Tile) = texture
}
