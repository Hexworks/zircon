package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.onSelectionChanged
import org.hexworks.zircon.api.extensions.transform
import org.hexworks.zircon.api.modifier.TileTransformModifier

object LayerTransformerExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val theme = ColorThemes.amigaOs()

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(CP437TilesetResources.taffer20x20())
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val transformingLayer = Layers.newBuilder()
                .withSize(Sizes.create(20, 20))
                .withOffset(Positions.create(1, 5))
                .build()
                .fill(Tiles.newBuilder()
                        .withBackgroundColor(RED)
                        .withForegroundColor(GREEN)
                        .withCharacter('x')
                        .buildCharacterTile())

        val hideableLayer = Layers.newBuilder()
                .withSize(Sizes.create(20, 20))
                .withOffset(Positions.create(39, 5))
                .build()
                .fill(Tiles.newBuilder()
                        .withBackgroundColor(BLUE)
                        .withForegroundColor(YELLOW)
                        .withCharacter('+')
                        .buildCharacterTile())

        val transformToggle = Components.toggleButton()
                .withText("Transform")
                .build().apply {
                    onSelectionChanged {
                        if (it.newValue) {
                            transformingLayer.transform { tile ->
                                tile.withAddedModifiers(HideModifier)
                            }
                        } else {
                            transformingLayer.transform { tile ->
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

        screen.pushLayer(transformingLayer)
        screen.pushLayer(hideableLayer)
    }

}

object HideModifier : TileTransformModifier<CharacterTile> {

    override fun canTransform(tile: Tile) = true

    override fun transform(tile: CharacterTile) = Tiles.empty()

    override fun generateCacheKey() = "Modifier.HideModifier"
}
