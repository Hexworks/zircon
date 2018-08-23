package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.internal.behavior.Identifiable
import kotlin.reflect.KClass

/**
 * Contains metadata about a tileset for a given [Tile] type.
 */
class CP437TilesetResource(override val width: Int,
                           override val height: Int,
                           override val path: String) : BaseTilesetResource() {

    override val id: Identifier = Identifier.randomIdentifier()

    override val tileType = CharacterTile::class

    override fun size() = Size.create(width, height)

    override fun isCompatibleWith(other: TilesetResource): Boolean {
        return other.tileType == tileType &&
                other.width == width &&
                other.height == height
    }
}
