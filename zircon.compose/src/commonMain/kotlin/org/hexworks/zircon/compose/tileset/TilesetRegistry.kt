package org.hexworks.zircon.compose.tileset

import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.TilesetType
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.compose.FrameData
import org.hexworks.zircon.compose.shader.AtlasInfo
import kotlin.reflect.KClass

/**
 * Creates and caches [ComposeTileset]s and hands out atlas ids. Creation is synchronous
 * (no IO), so it can happen in the middle of a frame; the atlas bytes are loaded by
 * [loadPending], which the composable drives from a coroutine.
 */
public class TilesetRegistry(
    extraFactories: List<TilesetFactory<FrameData>> = emptyList(),
) : TilesetLoader<FrameData> {

    private val factories: Map<Pair<TileType, TilesetType>, TilesetFactory<FrameData>> =
        (defaultFactories + extraFactories).associateBy { it.supportedTileType to it.supportedTilesetType }

    private val byResource = mutableMapOf<String, ComposeTileset>()
    private var nextAtlasId = 1

    /** Tilesets created so far. */
    public val tilesets: Collection<ComposeTileset> get() = byResource.values

    /** Every loaded sprite sheet, by atlas id. */
    public fun atlases(): Map<Int, AtlasInfo> = buildMap {
        for (tileset in byResource.values) putAll(tileset.atlases)
    }

    override fun canLoadTileset(resource: TilesetResource): Boolean =
        keyOf(resource) in byResource || (resource.tileType to resource.tilesetType) in factories

    override fun loadTilesetFrom(resource: TilesetResource): Tileset<FrameData> =
        byResource.getOrPut(keyOf(resource)) {
            val factory = factories[resource.tileType to resource.tilesetType]
                ?: error("No Compose tileset for ${resource.tileType}/${resource.tilesetType} ($resource)")
            val tileset = factory.create(resource) as? ComposeTileset
                ?: error("Tileset factories for the Compose renderer must create ComposeTilesets")
            tileset.allocateAtlasId = { nextAtlasId++ }
            tileset
        }

    /**
     * Loads every tileset that is not loaded yet. Returns `true` if anything was loaded.
     */
    public suspend fun loadPending(): Boolean {
        var loaded = false
        for (tileset in byResource.values.toList()) {
            if (!tileset.isLoaded) {
                tileset.load()
                loaded = true
            }
        }
        return loaded
    }

    private fun keyOf(resource: TilesetResource) = "${resource.path}@${resource.width}x${resource.height}"

    public companion object {
        private val defaultFactories: List<TilesetFactory<FrameData>> = listOf(
            factory(TileType.CHARACTER_TILE, TilesetType.CP437Tileset) { CP437Tileset(it) },
            factory(TileType.CHARACTER_TILE, TilesetType.TrueTypeFont) { TrueTypeTileset(it) },
            factory(TileType.GRAPHICAL_TILE, TilesetType.GraphicalTileset) { GraphicalTileset(it) },
        )

        private fun factory(
            tileType: TileType,
            tilesetType: TilesetType,
            create: (TilesetResource) -> ComposeTileset,
        ): TilesetFactory<FrameData> = object : TilesetFactory<FrameData> {
            override val targetType: KClass<FrameData> = FrameData::class
            override val supportedTileType: TileType = tileType
            override val supportedTilesetType: TilesetType = tilesetType
            override fun create(resource: TilesetResource): Tileset<FrameData> = create(resource)
        }
    }
}
