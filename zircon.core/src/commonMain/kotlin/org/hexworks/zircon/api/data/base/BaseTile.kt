package org.hexworks.zircon.api.data.base


import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.GraphicalTile
import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.SimpleModifiers.*

/**
 * Base class for all [Tile] implementations.
 */
abstract class BaseTile : Tile {

    override fun fetchBorderData(): Set<Border> = modifiers
        .asSequence()
        .filter { it is Border }
        .map { it as Border }
        .toSet()

    override fun asCharacterTileOrNull(): CharacterTile? = this as? CharacterTile

    override fun asImageTileOrNull(): ImageTile? = this as? ImageTile

    override fun asGraphicalTileOrNull(): GraphicalTile? = this as? GraphicalTile


}
