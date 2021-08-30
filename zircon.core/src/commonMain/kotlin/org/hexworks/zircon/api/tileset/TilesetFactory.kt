package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.resource.TilesetResource

import org.hexworks.zircon.internal.resource.TilesetType
import org.hexworks.zircon.internal.resource.TileType
import kotlin.reflect.KClass
import kotlin.jvm.JvmStatic

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
