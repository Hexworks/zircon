package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Markov
import org.hexworks.zircon.api.tileset.TileTransformer

class Java2DMarkovTransformer : TileTransformer<Markov, CharacterTile> {

    override fun canTransform(tile: Tile) = tile is CharacterTile

    override fun transform(tile: CharacterTile, modifier: Markov): CharacterTile {
        return modifier.transform(tile)
    }


}
