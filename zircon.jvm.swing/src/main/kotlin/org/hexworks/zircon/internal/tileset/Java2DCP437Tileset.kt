package org.hexworks.zircon.internal.tileset

import com.github.benmanes.caffeine.cache.Caffeine
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.core.platform.factory.UUIDFactory
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.*
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.tileset.impl.CP437TileMetadataLoader
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.modifier.TileCoordinate
import org.hexworks.zircon.internal.resource.TileType.CHARACTER_TILE
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import org.hexworks.zircon.internal.tileset.transformer.*
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class Java2DCP437Tileset(
    private val resource: TilesetResource,
    private val source: BufferedImage
) : Tileset<Graphics2D> {

    override val id: UUID = UUIDFactory.randomUUID()
    override val width: Int
        get() = resource.width
    override val height: Int
        get() = resource.height

    private val lookup = CP437TileMetadataLoader(
        width = resource.width,
        height = resource.height
    )

    private val cache = Caffeine.newBuilder()
        .initialCapacity(100)
        .maximumSize(5000)
        .expireAfterAccess(1, TimeUnit.MINUTES)
        .build<String, TileTexture<BufferedImage>>()

    override val targetType = Graphics2D::class

    init {
        require(resource.tileType == CHARACTER_TILE) {
            "Can't use a ${resource.tileType.name}-based TilesetResource for" +
                    " a CharacterTile-based tileset."
        }
    }

    override fun drawTile(tile: Tile, surface: Graphics2D, position: Position) {
        val texture = fetchTextureForTile(tile, position)
        val x = position.x * width
        val y = position.y * height
        surface.drawImage(texture.texture, x, y, null)
    }

    private fun fetchTextureForTile(tile: Tile, position: Position): TileTexture<BufferedImage> {
        val fixedTile = tile as? CharacterTile ?: kotlin.run {
            throw IllegalArgumentException("A CP437 renderer can only render CharacterTiles. Offending tile: $tile.")
        }
        val key = fixedTile.cacheKey
        val meta = lookup.fetchMetaForTile(fixedTile)
        val maybeRegion = cache.getIfPresent(key)
        return if (maybeRegion != null) {
            maybeRegion
        } else {
            var image: TileTexture<BufferedImage> = DefaultTileTexture(
                width = width,
                height = height,
                texture = source.getSubimage(meta.x * width, meta.y * height, width, height)
            )
            TILE_INITIALIZERS.forEach {
                image = it.transform(image, fixedTile)
            }
            fixedTile.modifiers.filterIsInstance<TextureTransformModifier>().forEach {
                image = TEXTURE_TRANSFORMER_LOOKUP[it::class]?.transform(image, fixedTile) ?: image
            }
            image = applyDebugModifiers(image, fixedTile, position)
            cache.put(key, image)
            image
        }
    }

    private fun applyDebugModifiers(
        image: TileTexture<BufferedImage>,
        tile: Tile,
        position: Position
    ): TileTexture<BufferedImage> {
        val config = RuntimeConfig.config
        var result = image
        if (config.debugMode && config.debugConfig.displayGrid) {
            result = TEXTURE_TRANSFORMER_LOOKUP.getValue(Border::class)
                .transform(image, tile.withAddedModifiers(GRID_BORDER))
        }
        if (config.debugMode && config.debugConfig.displayCoordinates) {
            result = TEXTURE_TRANSFORMER_LOOKUP.getValue(TileCoordinate::class)
                .transform(image, tile.withAddedModifiers(TileCoordinate(position)))
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

        val TEXTURE_TRANSFORMER_LOOKUP: Map<KClass<out TextureTransformModifier>, TextureTransformer<BufferedImage>> =
            mapOf(
                SimpleModifiers.Underline::class to Java2DUnderlineTransformer(),
                SimpleModifiers.VerticalFlip::class to Java2DVerticalFlipper(),
                SimpleModifiers.Blink::class to Java2DNoOpTransformer(),
                SimpleModifiers.HorizontalFlip::class to Java2DHorizontalFlipper(),
                SimpleModifiers.CrossedOut::class to Java2DCrossedOutTransformer(),
                SimpleModifiers.Hidden::class to Java2DHiddenTransformer(),
                Glow::class to Java2DGlowTransformer(),
                Border::class to Java2DBorderTransformer(),
                Crop::class to Java2DCropTransformer(),
                TileCoordinate::class to Java2DTileCoordinateTransformer()
            ).toMap()

    }
}
