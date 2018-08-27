package org.hexworks.zircon.internal.tileset

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.RayShade
import org.hexworks.zircon.api.modifier.SimpleModifiers
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.internal.tileset.transformer.*
import java.awt.Font
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class CharacterTileFontTileset(private val resource: TilesetResource,
                               private val font: Font)
    : Tileset<BufferedImage, Graphics2D> {

    override val id: Identifier = Identifier.randomIdentifier()

    override val sourceType = BufferedImage::class
    override val targetType = Graphics2D::class

    init {
        require(resource.tileType == CharacterTile::class) {
            "Can't use a ${resource.tileType.simpleName}-based TilesetResource for" +
                    " a CharacterTile-based tileset."
        }
    }

    override fun width(): Int {
        return resource.width
    }

    override fun height(): Int {
        return resource.height
    }

    override fun drawTile(tile: Tile, surface: Graphics2D, position: Position) {
        val x = position.x * width()
        val y = position.y * height()
        TODO()
    }

    companion object {

        private val TILE_INITIALIZERS = listOf(
                Java2DTileTextureCloner(),
                Java2DTileTextureColorizer())

        val MODIFIER_TRANSFORMER_LOOKUP = mapOf(
                Pair(SimpleModifiers.Underline::class, Java2DUnderlineTransformer()),
                Pair(SimpleModifiers.VerticalFlip::class, Java2DVerticalFlipper()),
                Pair(SimpleModifiers.HorizontalFlip::class, Java2DHorizontalFlipper()),
                Pair(SimpleModifiers.CrossedOut::class, Java2DCrossedOutTransformer()),
                Pair(SimpleModifiers.Blink::class, NoOpTransformer()),
                Pair(SimpleModifiers.Hidden::class, Java2DHiddenTransformer()),
                Pair(SimpleModifiers.Glow::class, Java2DGlowTransformer()),
                Pair(Border::class, Java2DBorderTransformer()),
                Pair(RayShade::class, Java2DRayShaderTransformer())
        ).toMap()

    }
}
