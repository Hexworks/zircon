package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.CP437TilesetResources.taffer20x20
import org.hexworks.zircon.api.CharacterTileStrings
import org.hexworks.zircon.api.Modifiers.underline
import org.hexworks.zircon.api.SwingApplications.startApplication
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.TileColor.Companion.fromString
import org.hexworks.zircon.api.data.Position.Companion.zero
import org.hexworks.zircon.api.data.Size.Companion.create
import org.hexworks.zircon.api.graphics.TextWrap

object CharacterTileStringExample {

    private const val TERMINAL_WIDTH = 42
    private const val TERMINAL_HEIGHT = 16
    private val SIZE = create(TERMINAL_WIDTH, TERMINAL_HEIGHT)

    @JvmStatic
    fun main(args: Array<String>) {
        val app = startApplication(
            AppConfig.newBuilder()
                .withDefaultTileset(taffer20x20())
                .withSize(SIZE)
                .withDebugMode(true)
                .build()
        )
        val tileGrid = app.tileGrid
        val text = "This is some text which is too long to fit on one line..."
        val tcs = CharacterTileStrings.newBuilder()
            .withForegroundColor(fromString("#eeffee"))
            .withBackgroundColor(fromString("#223344"))
            .withTextWrap(TextWrap.WRAP)
            .withSize(tileGrid.size)
            .withModifiers(underline())
            .withText(text)
            .build()
        tileGrid.draw(tcs, zero(), tileGrid.size)
    }
}