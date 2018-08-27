package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.internal.resource.BaseTilesetResource

/**
 * Tileset resource class which can be used with [CharacterTile]s.
 */
class CharacterTilesetResource(override val width: Int,
                               override val height: Int,
                               override val path: String) : BaseTilesetResource() {

    override val tileType = CharacterTile::class
}
