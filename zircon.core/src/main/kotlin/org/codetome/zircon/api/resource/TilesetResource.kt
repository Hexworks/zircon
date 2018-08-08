package org.codetome.zircon.api.resource

import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.internal.behavior.Identifiable
import kotlin.reflect.KClass

interface TilesetResource<T : Tile> : Identifiable {

    val tileType: KClass<T>
    val width: Int
    val height: Int
    val path: String

    fun size() = Size.create(width, height)

    fun isCompatibleWith(other: TilesetResource<out Tile>): Boolean {
        return other.tileType == tileType &&
                other.width == width &&
                other.height == height
    }
}
