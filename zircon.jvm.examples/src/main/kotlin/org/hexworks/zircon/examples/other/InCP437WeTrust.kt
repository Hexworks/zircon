package org.hexworks.zircon.examples.other


import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.tileset.impl.CP437TileMetadataLoader
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import java.util.*

object InCP437WeTrust {

    private val theme = ColorThemes.solarizedLightCyan()

    // Pick a tileset here, it may be of any size
    private val startingTileset = CP437TilesetResources.acorn8X16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withSize(Size.create(23, 25))
                .withDefaultTileset(startingTileset)
                .build())

        val screen = Screen.create(tileGrid)

        val loader = CP437TileMetadataLoader(16, 16)

        val cp437panel = Components.panel()
                .withSize(Size.create(19, 19))
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


        val mainPanel = Components.vbox()
                .withSize(19, 20)
                .withPosition(2, 1)
                .build()
                .apply {
                    val tilesets: List<BuiltInCP437TilesetResource> = BuiltInCP437TilesetResource.values().filter {
                        it.width == screen.tileset.width && it.height == screen.tileset.height
                    }

                    addComponent(Components.panel()
                            .withSize(19, 1)
                            .build()
                            .apply {
                                addFragment(
                                        Fragments
                                                .multiSelect(contentSize.width, tilesets)
                                                .withDefaultSelected(tilesets.first { it.id == screen.tileset.id })
                                                .withCallback { _, newTileset -> cp437panel.tilesetProperty.updateValue(newTileset); println("Setting tileset $newTileset") }
                                                .withToStringMethod { it.tilesetName }
                                                .build())
                            })

                    addComponent(cp437panel)
                }

        screen.addComponent(mainPanel)

        val btn = Components.checkBox()
                .withText("In CP437 we trust!")
                .withPosition(Position.create(1, 23))

        screen.addComponent(btn.build())

        screen.theme = theme

        screen.display()
    }

}
