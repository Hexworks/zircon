package org.hexworks.zircon.examples.modifiers


import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Delay
import org.hexworks.zircon.examples.base.Defaults
import org.hexworks.zircon.examples.base.displayDefaultScreen

object DelayedExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = displayDefaultScreen()

        val text = "This text is typed like on a typewriter"

        screen.cursorPosition = Position.create(1, 1)
        text.forEachIndexed { index, c ->
            val delayTime = 250 + index * 250
            screen.putTile(Tile.defaultTile()
                    .withBackgroundColor(TileColor.transparent())
                    .withForegroundColor(ColorThemes.nord().accentColor)
                    .withCharacter(c)
                    .withModifiers(Delay(delayTime.toLong())))
        }

    }

}












