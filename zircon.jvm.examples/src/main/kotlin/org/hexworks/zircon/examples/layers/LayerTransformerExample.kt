package org.hexworks.zircon.examples.layers


import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.hide
import org.hexworks.zircon.api.extensions.show
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.modifier.TileTransformModifier
import org.hexworks.zircon.examples.base.displayScreen

object LayerTransformerExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = displayScreen()

        val transformingLayer = Layer.newBuilder()
            .withSize(Size.create(20, 20))
            .withOffset(Position.create(1, 5))
            .build().apply {
                fill(
                    Tile.newBuilder()
                        .withBackgroundColor(TileColor.transparent())
                        .withForegroundColor(GREEN)
                        .withCharacter('x')
                        .buildCharacterTile()
                )
            }

        val hideableLayer = Layer.newBuilder()
            .withSize(Size.create(20, 20))
            .withOffset(Position.create(39, 5))
            .build().apply {
                fill(
                    Tile.newBuilder()
                        .withBackgroundColor(TileColor.transparent())
                        .withForegroundColor(YELLOW)
                        .withCharacter('+')
                        .buildCharacterTile()
                )
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
