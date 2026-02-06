package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.util.markovchain.MarkovChain


data class Markov<T : Tile>(
    private val chain: MarkovChain<T>
) : TileModifier<T> {

    override val cacheKey: String
        get() = "Modifier.Markov.${chain.current().id}"

    override fun canTransform(tile: Tile) = true

    override fun transform(tile: T): T {
        return chain.next().data()
    }

}
