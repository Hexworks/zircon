package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.util.markovchain.MarkovChain


data class Markov(
    private val chain: MarkovChain<CharacterTile>
) : TileModifier<CharacterTile> {

    override val cacheKey: String
        get() = "Modifier.Markov.${chain.current().id}"

    override fun canTransform(tile: Tile) = tile is CharacterTile

    override fun transform(tile: CharacterTile): CharacterTile {
        return chain.next().data()
    }

}
