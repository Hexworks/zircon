package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.ANSITileColor.MAGENTA
import org.hexworks.zircon.api.color.ANSITileColor.YELLOW
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.modifier.TileTransformModifier

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        val grid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withSize(1, 1)
                .build())

        grid.fill(Tile.defaultTile().withBackgroundColor(YELLOW))

        grid.addLayer(Layer.newBuilder()
                .withSize(1, 1)
                .build().apply {
                    fill(Tile.defaultTile()
                            .withCharacter('x')
                            .withBackgroundColor(MAGENTA)
                            .withModifiers(HideModifier))
                })
    }


    object HideModifier : TileTransformModifier<CharacterTile> {

        override val cacheKey: String
            get() = "Modifier.HideModifier"

        override fun canTransform(tile: Tile) = true

        override fun transform(tile: CharacterTile) = Tile.newBuilder()
                .withCharacter('x')
                .withForegroundColor(TileColor.create(125, 0, 0, 125))
                .withBackgroundColor(TileColor.create(0, 0, 0, 0))
                .buildCharacterTile()

    }
}
