package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.TilesetType
import kotlin.reflect.KClass

// TODO: document this
public interface TilesetFactory<S : Any> {

    /**
     * The type of the surface the textures are drawn upon.
     */
    public val targetType: KClass<S>

    public val supportedTileType: TileType
    public val supportedTilesetType: TilesetType

    /**
     * Creates a [Tileset] for the given [TilesetResource].
     */
    public fun create(resource: TilesetResource): Tileset<S>
}
