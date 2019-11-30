package org.hexworks.zircon.examples

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Layers
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.color.ANSITileColor.BLUE
import org.hexworks.zircon.api.color.ANSITileColor.GREEN
import org.hexworks.zircon.api.color.ANSITileColor.RED
import org.hexworks.zircon.api.color.ANSITileColor.YELLOW
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.hide
import org.hexworks.zircon.api.extensions.show
import org.hexworks.zircon.api.modifier.TileTransformModifier

object LayerTransformerExample {

    // TODO: transform hide not working
    @JvmStatic
    fun main(args: Array<String>) {

        val theme = ColorThemes.solarizedLightOrange()

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(CP437TilesetResources.taffer20x20())
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val transformingLayer = Layers.newBuilder()
                .withSize(Sizes.create(20, 20))
                .withOffset(Positions.create(1, 5))
                .build().apply {
                    fill(Tiles.newBuilder()
                            .withBackgroundColor(RED)
                            .withForegroundColor(GREEN)
                            .withCharacter('x')
                            .buildCharacterTile())
                }

        val hideableLayer = Layers.newBuilder()
                .withSize(Sizes.create(20, 20))
                .withOffset(Positions.create(39, 5))
                .build().apply {
                    fill(Tiles.newBuilder()
                            .withBackgroundColor(BLUE)
                            .withForegroundColor(YELLOW)
                            .withCharacter('+')
                            .buildCharacterTile())
                }

        val transformToggle = Components.toggleButton()
                .withText("Transform")
                .build().apply {
                    onSelectionChanged {
                        if (it.newValue) {
                            transformingLayer.transform { _, tile ->
                                tile.withAddedModifiers(HideModifier)
                            }
                        } else {
                            transformingLayer.transform { _, tile ->
                                tile.withRemovedModifiers(HideModifier)
                            }
                        }
                    }
                }

        screen.addComponent(transformToggle)

        screen.addComponent(Components.toggleButton()
                .withText("Hide")
                .withPosition(Positions.topRightOf(transformToggle) + Positions.create(1, 0))
                .build().apply {
                    onSelectionChanged {
                        if (it.newValue) {
                            hideableLayer.hide()
                        } else {
                            hideableLayer.show()
                        }
                    }
                })

        screen.applyColorTheme(theme)
        screen.display()

        screen.addLayer(transformingLayer)
        screen.addLayer(hideableLayer)
    }

}

object HideModifier : TileTransformModifier<CharacterTile> {

    override val cacheKey: String
        get() = "Modifier.HideModifier"

    override fun canTransform(tile: Tile) = true

    override fun transform(tile: CharacterTile) = Tiles.empty()

}
