package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder

object ImageTileExample {

    private val imageDictionary = ImageDictionaryTilesetResources.loadFromDirectory("zircon.examples/src/main/resources/image_dictionary")

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        tileGrid.draw(CharacterTileStrings.newBuilder()
                .text("You can see an image tile below...")
                .build())


        val imageTile = Tiles.newBuilder()
                .tileset(imageDictionary)
                .name("hexworks_logo.png")
                .buildImageTile()

        // TODO: fix positioning

        tileGrid.setTileAt(Positions.create(5, 5), imageTile)

    }

}
