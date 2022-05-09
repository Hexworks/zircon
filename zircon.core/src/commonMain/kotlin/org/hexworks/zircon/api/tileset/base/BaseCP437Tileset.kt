package org.hexworks.zircon.api.tileset.base

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TileTransformModifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.util.Cache
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.tileset.impl.CP437TextureMetadata
import org.hexworks.zircon.internal.tileset.impl.CP437TileMetadataLoader
import kotlin.reflect.KClass

/**
 * This base class can be used to implement CP437 tilesets. You are **not required** to use this, it was implemented
 * for convenience only.
 * This implementation contains all the checks and lookups that are necessary for CP437.
 * What you need to implement:
 * - [drawTile]: this is the actual draw operation
 * - [loadTileTexture]: this is the loading of a texture for a given tile.
 * What you don't need to implement
 * - Checking for CharacterTile
 * - Caching
 * - Creating the necessary metadata for the texture
 * @param S the class of the surface that we'll draw upon (Graphics2D for Swing for example)
 * @param T the class of the individual texture tile that can be cached (BufferedImage for Swing for example)
 */
abstract class BaseCP437Tileset<S : Any, T : Any>(
    protected val resource: TilesetResource,
    override val targetType: KClass<S>,
) : Tileset<S> {

    override val id: UUID = UUID.randomUUID()
    override val width: Int
        get() = resource.width
    override val height: Int
        get() = resource.height

    private val cache = Cache.create<TileTexture<T>>()
    private val lookup: CP437TileMetadataLoader = CP437TileMetadataLoader(
        width = resource.width,
        height = resource.height
    )

    init {
        require(resource.tileType == TileType.CHARACTER_TILE) {
            "CP437 tilesets only support ${TileType.CHARACTER_TILE.name}s. The supplied resource's type was ${resource.tileType.name}."
        }
    }

    protected fun fetchTextureForTile(tile: Tile, position: Position): TileTexture<T> {
        require(tile is CharacterTile) {
            "A CP437 renderer can only render ${CharacterTile::class.simpleName}s. Offending tile: $tile."
        }
        var fixedTile: CharacterTile = tile
        fixedTile.modifiers.filterIsInstance<TileTransformModifier<CharacterTile>>().forEach { modifier ->
            if (modifier.canTransform(fixedTile)) {
                fixedTile = modifier.transform(fixedTile)
            }
        }
        val key = fixedTile.cacheKey
        val meta = lookup.fetchMetaForTile(fixedTile)
        return cache.retrieveIfPresentOrNull(key) ?: loadTileTexture(fixedTile, position, meta).apply {
            cache.store(this)
        }
    }

    abstract fun loadTileTexture(
        tile: CharacterTile,
        position: Position,
        meta: CP437TextureMetadata
    ): TileTexture<T>

}
