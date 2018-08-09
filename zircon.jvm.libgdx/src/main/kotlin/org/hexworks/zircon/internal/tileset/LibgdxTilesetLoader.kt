package org.hexworks.zircon.internal.tileset

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.api.util.Identifier
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class LibgdxTilesetLoader : TilesetLoader<TextureRegion>, Closeable {

    private val tilesetCache = mutableMapOf<Identifier, Tileset<TextureRegion>>()

    override fun loadTilesetFrom(resource: TilesetResource): Tileset<TextureRegion> {
        return tilesetCache.getOrPut(resource.id) {
            LOADERS[resource.tileType]?.invoke(resource)
                    ?: throw IllegalArgumentException("Unknown tile type '${resource.tileType}'.§")
        }
    }

    override fun close() {
        tilesetCache.clear()
    }

    companion object {

        private val LOADERS: Map<KClass<out Tile>, (TilesetResource) -> Tileset<TextureRegion>> = mapOf(
                CharacterTile::class to { resource: TilesetResource ->
                    LibgdxTileset(
                            path = resource.path,
                            width = resource.width,
                            height = resource.height
                    )
                })
    }
}
