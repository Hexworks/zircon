package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.CP437TilesetResources.taffer20x20
import org.hexworks.zircon.api.CharacterTileStrings
import org.hexworks.zircon.api.SwingApplications.startApplication
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.CursorStyle
import org.hexworks.zircon.api.color.TileColor.Companion.fromString
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

object CursorExample {

    private val TERMINAL_WIDTH = 30
    private val TERMINAL_HEIGHT = 10
    private val SIZE = Size.create(TERMINAL_WIDTH, TERMINAL_HEIGHT)

    @JvmStatic
    fun main(args: Array<String>) {
        val config = AppConfig.newBuilder()
            .withCursorColor(fromString("#ff8844"))
            .withBlinkLengthInMilliSeconds(500)
            .withCursorStyle(CursorStyle.FIXED_BACKGROUND)
            .withCursorBlinking(true)
            .withSize(SIZE)
            .withDefaultTileset(taffer20x20())
            .build()
        val app = startApplication(config)
        val grid = app.tileGrid

        // for this example we need the cursor to be visible
        grid.isCursorVisible = true
        val text = "Cursor example..."
        grid.draw(CharacterTileStrings.newBuilder().withText(text).build())
        grid.cursorPosition = Position.create(text.length, 0)
    }
}