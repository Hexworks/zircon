package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.util.markovchain.MarkovChain

data class Markov(private val chain: MarkovChain<out Tile>) : TileSwitchModifier {

    override fun generateCacheKey(): String {
        return "Modifier.Markov.${chain.current().id}"
    }

    override fun transform(tile: Tile): Tile {
        return chain.next().data().get()
    }

}
