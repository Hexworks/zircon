package org.hexworks.zircon.examples.modifiers

import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.Modifiers.blink
import org.hexworks.zircon.api.Modifiers.border
import org.hexworks.zircon.api.Modifiers.crop
import org.hexworks.zircon.api.Modifiers.crossedOut
import org.hexworks.zircon.api.Modifiers.fadeIn
import org.hexworks.zircon.api.Modifiers.fadeInOut
import org.hexworks.zircon.api.Modifiers.fadeOut
import org.hexworks.zircon.api.Modifiers.glow
import org.hexworks.zircon.api.Modifiers.hidden
import org.hexworks.zircon.api.Modifiers.horizontalFlip
import org.hexworks.zircon.api.Modifiers.verticalFlip
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.examples.base.displayScreen
import kotlin.random.Random

object ModifiersExampleKotlin {

    private val random = Random(687483)

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = displayScreen()

        screen.cursorPosition = Position.create(5, 5)

        screen.putTile(randomTile().withAddedModifiers(blink()))
        screen.putTile(randomTile().withAddedModifiers(crossedOut()))
        screen.putTile(randomTile().withAddedModifiers(verticalFlip()))
        screen.putTile(randomTile().withAddedModifiers(horizontalFlip()))
        screen.putTile(randomTile().withAddedModifiers(hidden()))
        screen.putTile(randomTile().withAddedModifiers(glow()))
        screen.putTile(randomTile().withAddedModifiers(border()))
        screen.putTile(randomTile().withAddedModifiers(crop(6, 6, 10, 10)))
        screen.putTile(randomTile().withAddedModifiers(crop(6, 6, 10, 10)))


        screen.cursorPosition = Position.create(5, 10)

        screen.putTile(randomTile().withAddedModifiers(fadeIn()))
        screen.putTile(randomTile().withAddedModifiers(fadeOut()))
        screen.putTile(randomTile().withAddedModifiers(fadeInOut()))
        screen.putTile(randomTile().withAddedModifiers(Modifiers.delay(2000L)))
    }

    private fun randomTile() = Tile.createCharacterTile(
        'x', StyleSet.newBuilder().apply {
            foregroundColor = ANSITileColor.values()[random.nextInt(ANSITileColor.values().size)]
            backgroundColor = ANSITileColor.values()[random.nextInt(ANSITileColor.values().size)]
        }.build()
    )
}












