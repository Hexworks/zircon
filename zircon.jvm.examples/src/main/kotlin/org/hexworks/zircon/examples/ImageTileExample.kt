package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*

object ImageTileExample {

    private val imageDictionary = ImageDictionaryTilesetResources.loadFromDirectory("zircon.jvm.examples/src/main/resources/image_dictionary")

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        tileGrid.draw(CharacterTileStrings.newBuilder()
                .withText("You can see an image tile below...")
                .build())


        val imageTile = Tiles.newBuilder()
                .withTileset(imageDictionary)
                .withName("hexworks_logo.png")
                .buildImageTile()

        // TODO: fix positioning

        tileGrid.setTileAt(Positions.create(30, 20), imageTile)

    }

}
