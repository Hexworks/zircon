package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.tile.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.util.markovchain.MarkovChain

data class Markov(private val chain: MarkovChain<CharacterTile>) : TileTransformModifier<CharacterTile> {

    override fun canTransform(tile: Tile) = tile is CharacterTile

    override fun generateCacheKey(): String {
        return "Modifier.Markov.${chain.current().id}"
    }

    override fun transform(tile: CharacterTile): CharacterTile {
        return chain.next().data().get()
    }

}
