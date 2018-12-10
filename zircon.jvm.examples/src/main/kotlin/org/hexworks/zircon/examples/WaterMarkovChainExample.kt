package org.hexworks.zircon.examples

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols

object WaterMarkovChainExample {

    private val tileset = CP437TilesetResources.taffer20x20()

    private val waterColor0 = TileColor.fromString("#002e45")
    private val waterColor1 = TileColor.fromString("#004e65")
    private val waterColor2 = TileColor.fromString("#006e85")
    private val waterColor3 = TileColor.fromString("#008ea5")
    private val waterColor4 = TileColor.fromString("#00aec5")

    private val waveColor0 = TileColor.fromString("#2365aa")
    private val waveColor1 = TileColor.fromString("#2375bb")
    private val waveColor2 = TileColor.fromString("#2385cc")
    private val waveColor3 = TileColor.fromString("#2395dd")
    private val waveColor4 = TileColor.fromString("#23a5ee")



    private val defaultWater = Tile.empty()
            .withForegroundColor(waveColor0)
            .withBackgroundColor(waterColor0)
            .withCharacter(Symbols.BLOCK_SPARSE)

    private val smallWave = Tile.empty()
            .withForegroundColor(waveColor1)
            .withBackgroundColor(waterColor1)
            .withCharacter('~')

    private val mediumWave = Tile.empty()
            .withForegroundColor(waveColor2)
            .withBackgroundColor(waterColor2)
            .withCharacter(Symbols.APPROXIMATION)


    private val largeWave = Tile.empty()
            .withForegroundColor(waveColor3)
            .withBackgroundColor(waterColor3)
            .withCharacter(Symbols.TRIPLE_BAR)


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(80, 40))
                .withDebugMode(true)
                .build())

        val positions0 = Sizes.create(100, 1).fetchPositions().iterator()

        tileGrid.setTileAt(positions0.next(), defaultWater)
        tileGrid.setTileAt(positions0.next(), smallWave)
        tileGrid.setTileAt(positions0.next(), mediumWave)
        tileGrid.setTileAt(positions0.next(), largeWave)
        tileGrid.setTileAt(positions0.next(), mediumWave)
        tileGrid.setTileAt(positions0.next(), smallWave)
        tileGrid.setTileAt(positions0.next(), defaultWater)

//        tileGrid.size.fetchPositions().forEach {
//            tileGrid.setTileAt(it, defaultWater.withModifiers(Markov(MarkovChain.create(initialNode))))
//        }


    }

}












