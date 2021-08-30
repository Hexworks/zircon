@file:Suppress("DuplicatedCode")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.modifier.SimpleModifiers
import org.hexworks.zircon.examples.base.displayScreen

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = displayScreen()

        val transformingLayer = Layer.newBuilder()
            .withSize(Size.create(20, 20))
            .withOffset(Position.create(1, 5))
            .build().apply {
                fill(
                    Tile.newBuilder()
                        .withBackgroundColor(ANSITileColor.RED)
                        .withForegroundColor(ANSITileColor.GREEN)
                        .withCharacter('x')
                        .withModifiers(SimpleModifiers.Hidden)
                        .buildCharacterTile()
                )
            }

        screen.addLayer(transformingLayer)

    }

}

