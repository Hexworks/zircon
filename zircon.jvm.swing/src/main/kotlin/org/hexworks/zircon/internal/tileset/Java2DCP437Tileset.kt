package org.hexworks.zircon.internal.tileset

import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.*
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.base.BaseCP437Tileset
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.modifier.TileCoordinate
import org.hexworks.zircon.internal.tileset.impl.CP437TextureMetadata
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import org.hexworks.zircon.internal.tileset.transformer.*
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class Java2DCP437Tileset(
    resource: TilesetResource,
    private val source: BufferedImage,
    private val textureTransformers: Map<KClass<out TextureTransformModifier>, TextureTransformer<BufferedImage>>
) : BaseCP437Tileset<Graphics2D, BufferedImage>(
    resource = resource,
    targetType = Graphics2D::class
) {

    override fun drawTile(tile: Tile, surface: Graphics2D, position: Position) {
        val texture = fetchTextureForTile(tile, position)
        val x = position.x * width
        val y = position.y * height
        surface.drawImage(texture.texture, x, y, null)
    }

    override fun loadTileTexture(
        tile: CharacterTile,
        position: Position,
        meta: CP437TextureMetadata
    ): TileTexture<BufferedImage> {
        var image: TileTexture<BufferedImage> = DefaultTileTexture(
            width = width,
            height = height,
            texture = source.getSubimage(meta.x * width, meta.y * height, width, height),
            cacheKey = tile.cacheKey
        )
        TILE_INITIALIZERS.forEach {
            image = it.transform(image, tile)
        }
        tile.modifiers.filterIsInstance<TextureTransformModifier>().forEach {
            image = textureTransformers[it::class]?.transform(image, tile) ?: image
        }
        image = applyDebugModifiers(image, tile, position)
        return image
    }

    private fun applyDebugModifiers(
        image: TileTexture<BufferedImage>,
        tile: Tile,
        position: Position
    ): TileTexture<BufferedImage> {
        val config = RuntimeConfig.config
        var result = image
        val border = textureTransformers[Border::class]
        if (config.debugMode && config.debugConfig.displayGrid && border != null) {
            result = border.transform(image, tile.withAddedModifiers(GRID_BORDER))
        }
        val tileCoordinate = textureTransformers[TileCoordinate::class]
        if (config.debugMode && config.debugConfig.displayCoordinates && tileCoordinate != null) {
            result = tileCoordinate.transform(image, tile.withAddedModifiers(TileCoordinate(position)))
        }
        return result
    }

    companion object {

        private val GRID_BORDER = Border.newBuilder()
            .withBorderColor(ANSITileColor.BRIGHT_MAGENTA)
            .withBorderWidth(1)
            .withBorderType(BorderType.SOLID)
            .build()

        private val TILE_INITIALIZERS = listOf(
            Java2DTextureCloner(),
            Java2DTextureColorizer(),
        )
    }
}
