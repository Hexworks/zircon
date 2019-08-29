package org.hexworks.zircon.examples

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.tileset.impl.CP437TileMetadataLoader

object CustomTileExample {

    data class CustomTile(val characterTile: CharacterTile) : CharacterTile by characterTile

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        tileGrid.draw(CustomTile(Tiles.defaultTile().withCharacter('x')), Positions.zero())

        val loader = CP437TileMetadataLoader(16, 16)

        val data = loader.fetchMetaForTile(Tiles.defaultTile().withCharacter(Symbols.INVERSE_BULLET))

        println(data)
    }

}
