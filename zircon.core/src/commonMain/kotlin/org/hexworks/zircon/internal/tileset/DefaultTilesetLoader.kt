package org.hexworks.zircon.internal.tileset

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.TilesetType
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.util.reportErrorOnce
import kotlin.reflect.KClass

public class DefaultTilesetLoader<T : Any>(
    factories: List<TilesetFactory<T>>,
    private val targetType: KClass<T>,
) : TilesetLoader<T>, Closeable {

    override val closedProperty: Property<Boolean> = false.toProperty()

    private val factoryLookup: Map<Pair<TileType, TilesetType>, TilesetFactory<T>> =
        factories.associateBy { it.supportedTileType to it.supportedTilesetType }
    private val tilesetCache = mutableMapOf<UUID, Tileset<T>>()

    override fun canLoadTileset(resource: TilesetResource): Boolean =
        resource.id in tilesetCache || resource.loaderKey in factoryLookup

    override fun loadTilesetFrom(resource: TilesetResource): Tileset<T> {
        return tilesetCache.getOrPut(resource.id) {
            val factory = factoryLookup[resource.loaderKey]
            if (factory == null) {
                // Reported once; the tiles of this resource stay blank instead of failing every frame.
                reportErrorOnce(
                    key = resource.id,
                    source = "Tileset '${resource.path}'",
                    error = IllegalArgumentException("No tileset factory found for key ${resource.loaderKey}")
                )
                NoOpTileset(resource, targetType)
            } else factory.create(resource)
        }
    }

    override fun close() {
        closedProperty.value = true
        tilesetCache.clear()
    }

    public companion object {
        public val TilesetResource.loaderKey: Pair<TileType, TilesetType>
            get() = tileType to tilesetType
    }
}
