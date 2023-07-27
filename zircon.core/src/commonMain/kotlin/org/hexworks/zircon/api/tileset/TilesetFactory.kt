package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType
import kotlin.reflect.KClass

// TODO: document this
interface TilesetFactory<S : Any> {

    /**
     * The type of the surface the textures are drawn upon.
     */
    val targetType: KClass<S>

    val supportedTileType: TileType
    val supportedTilesetType: TilesetType

    /**
     * Creates a [Tileset] for the given [TilesetResource].
     */
    fun create(resource: TilesetResource): Tileset<S>
}
