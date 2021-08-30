package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.*
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType

class DefaultTilesetLoader<T : Any>(
    private val factories: Map<Pair<TileType, TilesetType>, TilesetFactory<T>>,
) : TilesetLoader<T>, Closeable {

    override val closedValue: Property<Boolean> = false.toProperty()

    private val tilesetCache = mutableMapOf<UUID, Tileset<T>>()

    override fun loadTilesetFrom(resource: TilesetResource): Tileset<T> {
        return tilesetCache.getOrPut(resource.id) {
            val key = resource.getLoaderKey()
            require(factories.containsKey(key)) {
                "No tileset factory found for key $key"
            }
            factories.getValue(key).create(resource)
        }
    }

    override fun canLoadResource(resource: TilesetResource): Boolean =
        resource.id in tilesetCache || resource.getLoaderKey() in factories

    override fun close() {
        closedValue.value = true
        tilesetCache.clear()
    }

    companion object {
        fun TilesetResource.getLoaderKey() = tileType to tilesetType
    }
}
