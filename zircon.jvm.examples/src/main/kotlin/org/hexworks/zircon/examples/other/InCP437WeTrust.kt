package org.hexworks.zircon.examples.other


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.tileset.impl.CP437TileMetadataLoader
import java.util.*

object InCP437WeTrust {

    private val theme = ColorThemes.solarizedLightCyan()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withSize(Size.create(23, 24))
                .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                .build())

        val screen = Screen.create(tileGrid)

        val loader = CP437TileMetadataLoader(16, 16)

        val cp437panel = Components.panel()
                .withSize(Size.create(19, 19))
                .withPosition(Position.create(2, 2))
                .withDecorations(box(BoxType.SINGLE), shadow())
                .withRendererFunction { tileGraphics, _ ->
                    loader.fetchMetadata().forEach { (char, meta) ->
                        tileGraphics.draw(
                                tile = Tile.defaultTile()
                                        .withCharacter(char)
                                        .withBackgroundColor(theme.primaryBackgroundColor)
                                        .withForegroundColor(ANSITileColor.values()[Random().nextInt(ANSITileColor.values().size)]),
                                drawPosition = Position.create(meta.x, meta.y))
                    }
                }.build()

        screen.addComponent(cp437panel)

        val btn = Components.checkBox()
                .withText("In CP437 we trust!")
                .withPosition(Position.create(1, 22))

        screen.addComponent(btn.build())

        screen.theme = theme

        screen.display()
    }

}
