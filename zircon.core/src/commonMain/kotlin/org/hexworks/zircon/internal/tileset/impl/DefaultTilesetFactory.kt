package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType
import kotlin.reflect.KClass

internal class DefaultTilesetFactory<S : Any>(
    override val targetType: KClass<S>,
    override val supportedTileType: TileType,
    override val supportedTilesetType: TilesetType,
    private val factoryFunction: (TilesetResource) -> Tileset<S>
) : TilesetFactory<S> {
    override fun create(resource: TilesetResource) = factoryFunction(resource)
}
