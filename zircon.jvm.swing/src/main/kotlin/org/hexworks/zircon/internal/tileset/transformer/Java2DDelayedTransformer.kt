package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Delay
import org.hexworks.zircon.api.tileset.TileTransformer

class Java2DDelayedTransformer : TileTransformer<Delay, CharacterTile> {

    override fun canTransform(tile: Tile) = tile is CharacterTile

    override fun transform(tile: CharacterTile, modifier: Delay): CharacterTile {
        return modifier.transform(tile)
    }


}
