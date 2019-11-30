package org.hexworks.zircon.examples

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.tileset.impl.CP437TileMetadataLoader

object CustomTileExample {

    data class CustomTile(val characterTile: CharacterTile) : CharacterTile by characterTile

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        tileGrid.draw(CustomTile(Tile.defaultTile().withCharacter('x')), Position.zero())

        val loader = CP437TileMetadataLoader(16, 16)

        val data = loader.fetchMetaForTile(Tile.defaultTile().withCharacter(Symbols.INVERSE_BULLET))

        println(data)
    }

}
