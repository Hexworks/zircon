package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.CharacterTileStrings
import org.hexworks.zircon.api.ImageDictionaryTilesetResources
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile

object ImageTileExample {

    private val imageDictionary =
        ImageDictionaryTilesetResources.loadTilesetFromFilesystem("zircon.jvm.examples/src/main/resources/image_dictionary")

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        tileGrid.draw(
            CharacterTileStrings.newBuilder()
                .withText("You can see an image tile below...")
                .build()
        )


        val imageTile = Tile.newBuilder()
            .withTileset(imageDictionary)
            .withName("hexworks_logo.png")
            .buildImageTile()

        tileGrid.draw(imageTile, Position.create(30, 20))

    }

}
