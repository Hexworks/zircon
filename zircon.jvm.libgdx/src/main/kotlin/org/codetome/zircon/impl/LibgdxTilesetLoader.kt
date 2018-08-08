package org.codetome.zircon.impl

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.behavior.Closeable
import org.codetome.zircon.api.data.CharacterTile
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.tileset.TilesetLoader
import org.codetome.zircon.api.util.Identifier
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class LibgdxTilesetLoader : TilesetLoader<TextureRegion>, Closeable {

    private val tilesetCache = mutableMapOf<Identifier, Tileset<out Tile, TextureRegion>>()

    override fun loadTilesetFrom(resource: TilesetResource<out Tile>): Tileset<out Tile, TextureRegion> {
        return tilesetCache.getOrPut(resource.id) {
            LOADERS[resource.tileType]?.invoke(resource)
                    ?: throw IllegalArgumentException("Unknown tile type '${resource.tileType}'.§")
        }
    }

    override fun close() {
        tilesetCache.clear()
    }

    companion object {

        private val LOADERS: Map<KClass<out Tile>, (TilesetResource<out Tile>) -> Tileset<out Tile, TextureRegion>> = mapOf(
                CharacterTile::class to { resource: TilesetResource<out Tile> ->
                    LibgdxTileset(
                            path = resource.path,
                            width = resource.width,
                            height = resource.height
                    )
                })
    }
}
