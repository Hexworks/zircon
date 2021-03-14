package org.hexworks.zircon.internal.tileset

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.resource.TileType.CHARACTER_TILE
import org.hexworks.zircon.internal.resource.TileType.GRAPHIC_TILE
import org.hexworks.zircon.internal.resource.TilesetType.*

@Suppress("UNCHECKED_CAST")
class LibgdxTilesetLoader : TilesetLoader<SpriteBatch>, Closeable {

    override val isClosed = false.toProperty()

    private val tilesetCache = mutableMapOf<UUID, Tileset<SpriteBatch>>()

    override fun loadTilesetFrom(resource: TilesetResource): Tileset<SpriteBatch> {
        return tilesetCache.getOrPut(resource.id) {
            LOADERS[resource.getLoaderKey()]?.invoke(resource)
                    ?: throw IllegalArgumentException("Unknown tile type '${resource.tileType}', can't use ${resource.getLoaderKey()}.")
        }
    }

    override fun canLoadResource(resource: TilesetResource): Boolean =
        resource.id in tilesetCache || resource.getLoaderKey() in LOADERS

    override fun close() {
        isClosed.value = true
        tilesetCache.clear()
    }

    companion object {
        fun TilesetResource.getLoaderKey() = "${this.tileType.name}-${this.tilesetType.name}"

        private val LOADERS: Map<String, (TilesetResource) -> Tileset<SpriteBatch>> = mapOf(
                "$CHARACTER_TILE-$CP437_TILESET" to { resource: TilesetResource ->
                    LibgdxCP437Tileset(
                            path = resource.path,
                            width = resource.width,
                            height = resource.height)
                },
                "$GRAPHIC_TILE-$GRAPHIC_TILESET" to { resource: TilesetResource ->
                    LibgdxGraphicalTileset(
                            resource = resource)
                },
                "$CHARACTER_TILE-$TRUE_TYPE_FONT" to { resource: TilesetResource ->
                    LibgdxMonospaceFontTileset(
                            resource = resource)
                }
        )
    }
}
