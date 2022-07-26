package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.CP437TilesetResources.bisasam16x16
import org.hexworks.zircon.api.SwingApplications.startTileGrid
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.color.TileColor.Companion.fromString
import org.hexworks.zircon.api.data.Size.Companion.create
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse.Companion.processed

object TypingExample {

    private const val TERMINAL_WIDTH = 40

    @JvmStatic
    fun main(args: Array<String>) {
        val tileGrid = startTileGrid(
            AppConfigBuilder.newBuilder()
                .withDefaultTileset(bisasam16x16())
                .withSize(create(TERMINAL_WIDTH, 10))
                .withCursorBlinking(true)
                .build()
        )
        tileGrid.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { (_, key, code): KeyboardEvent, phase: UIEventPhase? ->
            val pos = tileGrid.cursorPosition
            if (code == KeyCode.ESCAPE) {
                System.exit(0)
            } else if (code == KeyCode.ENTER) {
                tileGrid.cursorPosition = pos.withRelativeY(1).withX(0)
            } else {
                tileGrid.putTile(Tile.newBuilder().withCharacter(key[0]).build())
            }
            processed()
        }
    }
}