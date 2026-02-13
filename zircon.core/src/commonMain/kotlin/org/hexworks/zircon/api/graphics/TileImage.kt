package org.hexworks.zircon.api.graphics

import kotlinx.collections.immutable.PersistentMap
import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * An immutable [TileComposite]. It is completely in memory, but it can be drawn onto
 * [TileGraphics] objects and its derivatives. Also supports operations for combining with
 * other [TileImage]s and converting to [TileGraphics].
 */
interface TileImage : TileComposite, TilesetHolder {

    /**
     * The [Tile]s this [TileComposite] contains. Note that a [TileImage]
     * uses a [PersistentMap] to store the tiles to enable fast creation
     * of new [TileImage]s.
     */
    override val tiles: PersistentMap<Position, Tile>

}
