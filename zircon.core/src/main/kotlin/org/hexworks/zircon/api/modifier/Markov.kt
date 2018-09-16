package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.Tile

data class Markov(val markovData: MarkovData) : TileSwitchModifier {

    private var current = markovData.first()

    override fun generateCacheKey(): String {
        return "Modifier.Markov.$markovData"
    }

    override fun transform(tile: Tile): Tile {
        current = current.next()
        return current.tile()
    }

}
