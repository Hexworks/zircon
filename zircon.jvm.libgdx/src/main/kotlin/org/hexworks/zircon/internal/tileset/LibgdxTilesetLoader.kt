package org.hexworks.zircon.internal.tileset

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.resource.TileType.CHARACTER_TILE
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.TilesetType.CP437_TILESET
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.api.util.Identifier

@Suppress("UNCHECKED_CAST")
class LibgdxTilesetLoader : TilesetLoader<SpriteBatch>, Closeable {

    private val tilesetCache = mutableMapOf<Identifier, Tileset<SpriteBatch>>()

    override fun loadTilesetFrom(resource: TilesetResource): Tileset<SpriteBatch> {
        return tilesetCache.getOrPut(resource.id) {
            LOADERS[resource.getLoaderKey()]?.invoke(resource)
                    ?: throw IllegalArgumentException("Unknown tile type '${resource.tileType}'.§")
        }
    }

    override fun close() {
        tilesetCache.clear()
    }

    companion object {

        fun TilesetResource.getLoaderKey() = "${this.tilesetType.name}-${this.tileType.name}"

        private val LOADERS: Map<String, (TilesetResource) -> Tileset<SpriteBatch>> = mapOf(
                "$CHARACTER_TILE-$CP437_TILESET" to { resource: TilesetResource ->
                    LibgdxTileset(
                            path = resource.path,
                            width = resource.width,
                            height = resource.height
                    )
                })
    }
}
