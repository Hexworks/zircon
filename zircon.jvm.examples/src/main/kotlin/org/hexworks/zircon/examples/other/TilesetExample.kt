package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.CP437TilesetResources.wanderlust16x16
import org.hexworks.zircon.api.SwingApplications.startApplication
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor.Companion.create
import org.hexworks.zircon.api.color.TileColor.Companion.fromString
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.Tile.Companion.empty
import org.hexworks.zircon.api.graphics.Symbols
import java.util.*

object TilesetExample {
    private const val RANDOM_CHAR_COUNT = 50
    private val RANDOM_CHARS = charArrayOf(
        Symbols.FACE_BLACK,
        Symbols.FACE_WHITE,
        Symbols.CLUB,
        Symbols.TRIANGLE_UP_POINTING_BLACK,
        Symbols.SPADES,
        Symbols.BULLET,
        Symbols.DIAMOND
    )
    private const val TERMINAL_WIDTH = 40
    private const val TERMINAL_HEIGHT = 40
    private val SIZE = Size.create(TERMINAL_WIDTH, TERMINAL_HEIGHT)
    private val GRASS_0 = Tile.newBuilder()
        .withCharacter(',')
        .withForegroundColor(fromString("#33cc44"))
        .withBackgroundColor(fromString("#114911"))
        .build()
    private val GRASS_1 = Tile.newBuilder()
        .withCharacter('`')
        .withForegroundColor(fromString("#33bb44"))
        .withBackgroundColor(fromString("#114511"))
        .build()
    private val GRASS_2 = Tile.newBuilder()
        .withCharacter('\'')
        .withForegroundColor(fromString("#33aa44"))
        .withBackgroundColor(fromString("#114011"))
        .build()
    private val GRASSES = arrayOf(GRASS_0, GRASS_1, GRASS_2)
    private val TEXT_COLOR = fromString("#dd6644")
    private val TEXT_BG_COLOR = fromString("#00ff00")
    @JvmStatic
    fun main(args: Array<String>) {

        // Libgdx doesn't support graphic tilesets yet
        val app = startApplication(
            AppConfigBuilder.newBuilder()
                .withDefaultTileset(wanderlust16x16())
                .withSize(SIZE)
                .withDebugMode(true)
                .build()
        )
        val tileGrid = app.tileGrid
        val random = Random()
        for (y in 0 until TERMINAL_HEIGHT) {
            for (x in 0 until TERMINAL_WIDTH) {
                tileGrid.draw(GRASSES[random.nextInt(3)], Position.create(x, y))
            }
        }
        val text = "Tileset Example"
        for (i in 0 until text.length) {
            tileGrid.draw(
                Tile.newBuilder()
                    .withCharacter(text[i])
                    .withForegroundColor(TEXT_COLOR)
                    .withBackgroundColor(TEXT_BG_COLOR)
                    .build(),
                Position.create(i + 2, 1)
            )
        }
        val charCount = RANDOM_CHARS.size
        val ansiCount = ANSITileColor.values().size
        val overlay = LayerBuilder.newBuilder()
            .withSize(tileGrid.size)
            .withFiller(empty().withBackgroundColor(create(0, 0, 0, 50)))
            .build()
        for (i in 0 until RANDOM_CHAR_COUNT) {
            overlay.draw(
                Tile.newBuilder()
                    .withCharacter(RANDOM_CHARS[random.nextInt(charCount)])
                    .withForegroundColor(ANSITileColor.values()[random.nextInt(ansiCount)])
                    .withBackgroundColor(transparent())
                    .build(),
                Position.create(
                    random.nextInt(TERMINAL_WIDTH),
                    random.nextInt(TERMINAL_HEIGHT - 2) + 2
                )
            )
        }
        tileGrid.addLayer(overlay)
    }
}