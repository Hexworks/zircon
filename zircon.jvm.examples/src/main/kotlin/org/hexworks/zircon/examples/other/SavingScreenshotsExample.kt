package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.CP437TilesetResources.rexPaint20x20
import org.hexworks.zircon.api.SwingApplications.createTilesetLoader
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position.Companion.create
import org.hexworks.zircon.api.data.Tile.Companion.newBuilder
import java.awt.Transparency
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object SavingScreenshotsExample {

    @JvmStatic
    fun main(args: Array<String>) {

        // you create an image and the corresponding graphics
        val image = BufferedImage(800, 600, Transparency.TRANSLUCENT)
        val graphics = image.createGraphics()

        // you load the tileset you want
        val loader = createTilesetLoader()
        val tileset = loader.loadTilesetFrom(rexPaint20x20())

        // you draw tiles on the image using the graphics
        tileset.drawTile(
            newBuilder()
                .withCharacter('a')
                .withBackgroundColor(ANSITileColor.RED)
                .withForegroundColor(ANSITileColor.WHITE)
                .build(), graphics, create(0, 0)
        )
        tileset.drawTile(
            newBuilder()
                .withCharacter('b')
                .withBackgroundColor(ANSITileColor.GREEN)
                .withForegroundColor(ANSITileColor.YELLOW)
                .build(), graphics, create(1, 0)
        )

        // and you write the result into a file
        ImageIO.write(image, "png", File("saved.png"))
    }
}