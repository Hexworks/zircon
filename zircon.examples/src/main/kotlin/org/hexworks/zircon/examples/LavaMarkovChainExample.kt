package org.hexworks.zircon.examples

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.modifier.LavaMarkovChain
import org.hexworks.zircon.api.modifier.Markov

object LavaMarkovChainExample {

    private val tileset = CP437TilesetResources.taffer20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(tileset)
                .debugMode(true)
                .build())

        tileGrid.size().fetchPositions().forEach {
            tileGrid.setTileAt(
                    position = it,
                    tile = LavaMarkovChain.EMPTY.tile().withModifiers(Markov(LavaMarkovChain.EMPTY)))
        }

    }

}
