package org.hexworks.zircon.examples

import org.hexworks.zircon.api.ImageDictionaryTilesetResources
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder
import org.hexworks.zircon.api.data.AbsolutePosition
import org.hexworks.zircon.api.data.ImageTile

object ImageTileExample {

    val imageDictionary = ImageDictionaryTilesetResources.loadFromDirectory("zircon.examples/src/main/resources/image_dictionary")

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        tileGrid.draw(CharacterTileStringBuilder.newBuilder()
                .text("You can see an image tile below...")
                .build())


        val imageTile = Tiles.newBuilder()
                .tileset(imageDictionary)
                .name("hexworks_logo.png")
                .buildImageTile()

        tileGrid.setTileAt(Positions.create(5, 5), imageTile)

    }

}
