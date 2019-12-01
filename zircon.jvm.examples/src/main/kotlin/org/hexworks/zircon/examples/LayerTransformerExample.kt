package org.hexworks.zircon.examples


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.ANSITileColor.BLUE
import org.hexworks.zircon.api.color.ANSITileColor.GREEN
import org.hexworks.zircon.api.color.ANSITileColor.RED
import org.hexworks.zircon.api.color.ANSITileColor.YELLOW
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.hide
import org.hexworks.zircon.api.extensions.show
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.modifier.TileTransformModifier
import org.hexworks.zircon.api.screen.Screen

object LayerTransformerExample {

    // TODO: transform hide not working
    @JvmStatic
    fun main(args: Array<String>) {

        val theme = ColorThemes.solarizedLightOrange()

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(CP437TilesetResources.taffer20x20())
                .build())

        val screen = Screen.create(tileGrid)

        val transformingLayer = Layer.newBuilder()
                .withSize(Size.create(20, 20))
                .withOffset(Position.create(1, 5))
                .build().apply {
                    fill(Tile.newBuilder()
                            .withBackgroundColor(RED)
                            .withForegroundColor(GREEN)
                            .withCharacter('x')
                            .buildCharacterTile())
                }

        val hideableLayer = Layer.newBuilder()
                .withSize(Size.create(20, 20))
                .withOffset(Position.create(39, 5))
                .build().apply {
                    fill(Tile.newBuilder()
                            .withBackgroundColor(BLUE)
                            .withForegroundColor(YELLOW)
                            .withCharacter('+')
                            .buildCharacterTile())
                }

        val transformToggle = Components.toggleButton()
                .withText("Transform")
                .build().apply {
                    selectedProperty.onChange {
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
                .withPosition(Position.topRightOf(transformToggle) + Position.create(1, 0))
                .build().apply {
                    selectedProperty.onChange {
                        if (it.newValue) {
                            hideableLayer.hide()
                        } else {
                            hideableLayer.show()
                        }
                    }
                })

        screen.theme = theme
        screen.display()

        screen.addLayer(transformingLayer)
        screen.addLayer(hideableLayer)
    }

}

object HideModifier : TileTransformModifier<CharacterTile> {

    override val cacheKey: String
        get() = "Modifier.HideModifier"

    override fun canTransform(tile: Tile) = true

    override fun transform(tile: CharacterTile) = Tile.empty()

}
