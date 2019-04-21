package org.hexworks.zircon.examples

import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor.GREEN
import org.hexworks.zircon.api.color.ANSITileColor.RED
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.onSelectionChanged
import org.hexworks.zircon.api.modifier.TileTransformModifier
import org.hexworks.zircon.api.resource.BuiltInTrueTypeFontResource

object LayerTransformerExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val theme = ColorThemes.amigaOs()

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(CP437TilesetResources.taffer20x20())
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val layer = Layers.newBuilder()
                .withSize(Sizes.create(20, 20))
                .withOffset(Positions.create(5, 5))
                .build()
                .fill(Tiles.newBuilder()
                        .withBackgroundColor(RED)
                        .withForegroundColor(GREEN)
                        .withCharacter('x')
                        .buildCharacterTile())

        screen.addComponent(Components.toggleButton()
                .withText("Hide")
                .build().apply {
                    onSelectionChanged {
                        if (it.newValue) {
                            layer.fetchPositions().forEach { pos ->
                                layer.getAbsoluteTileAt(pos).map { tile ->
                                    layer.setAbsoluteTileAt(pos, tile
                                            .withAddedModifiers(HideModifier))
                                }
                            }
                        } else {
                            layer.fetchPositions().forEach { pos ->
                                layer.getAbsoluteTileAt(pos).map { tile ->
                                    layer.setAbsoluteTileAt(pos, tile
                                            .withRemovedModifiers(HideModifier))
                                }
                            }
                        }
                    }
                })

        screen.applyColorTheme(theme)
        screen.display()

        screen.pushLayer(layer)

    }

}

object HideModifier : TileTransformModifier<CharacterTile> {

    override fun canTransform(tile: Tile) = true

    override fun transform(tile: CharacterTile) = Tiles.empty()

    override fun generateCacheKey() = "Modifier.HideModifier"
}
