package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Markov
import org.hexworks.zircon.api.tileset.TileSwitchTransformer

class Java2DMarkovTransformer : TileSwitchTransformer<Markov> {

    override fun transform(tile: Tile, modifier: Markov): Tile {
        return modifier.transform(tile)
    }
}
