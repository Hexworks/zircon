package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType

class DefaultTilesetLoader<T : Any>(
    factories: List<TilesetFactory<T>>,
) : TilesetLoader<T>, Closeable {

    override val closedValue: Property<Boolean> = false.toProperty()

    private val factoryLookup: Map<Pair<TileType, TilesetType>, TilesetFactory<T>> =
        factories.associateBy { it.supportedTileType to it.supportedTilesetType }
    private val tilesetCache = mutableMapOf<UUID, Tileset<T>>()

    override fun canLoadTileset(resource: TilesetResource): Boolean =
        resource.id in tilesetCache || resource.loaderKey in factoryLookup

    override fun loadTilesetFrom(resource: TilesetResource): Tileset<T> {
        return tilesetCache.getOrPut(resource.id) {
            val key = resource.loaderKey
            require(factoryLookup.containsKey(key)) {
                "No tileset factory found for key $key"
            }
            factoryLookup.getValue(key).create(resource)
        }
    }

    override fun close() {
        closedValue.value = true
        tilesetCache.clear()
    }

    companion object {
        val TilesetResource.loaderKey
            get() = tileType to tilesetType
    }
}
