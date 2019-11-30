package org.hexworks.zircon.examples


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.LibgdxApplications
import org.hexworks.zircon.api.application.AppConfig

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.tileset.impl.CP437TileMetadataLoader

object CP437CharsExample {

    private val theme = ColorThemes.solarizedLightBlue()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = LibgdxApplications.startTileGrid(AppConfig.newBuilder()
                .withSize(Size.create(21, 21))
                .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                .build())

        val screen = Screen.create(tileGrid)

        val loader = CP437TileMetadataLoader(16, 16)
        val cp437panel = Components.panel()
                .withSize(Size.create(19, 19))
                .withPosition(Position.create(1, 1))
                .withDecorations(box(BoxType.SINGLE), shadow())
                .withRendererFunction { tileGraphics, _ ->
                    loader.fetchMetadata().forEach { (char, meta) ->
                        tileGraphics.draw(
                                tile = Tile.defaultTile()
                                        .withCharacter(char)
                                        .withBackgroundColor(theme.primaryBackgroundColor)
                                        .withForegroundColor(theme.primaryForegroundColor),
                                drawPosition = Position.create(meta.x, meta.y)
                                        .plus(Position.offset1x1()))
                    }
                }
                .build()

        screen.addComponent(cp437panel)
        screen.theme = theme

        screen.display()
    }

}
