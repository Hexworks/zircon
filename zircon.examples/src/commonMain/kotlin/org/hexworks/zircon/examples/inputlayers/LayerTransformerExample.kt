package org.hexworks.zircon.examples.inputlayers

import org.hexworks.zircon.api.builder.component.toggleButton
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.hide
import org.hexworks.zircon.api.extensions.show
import org.hexworks.zircon.api.graphics.extensions.fill
import org.hexworks.zircon.api.modifier.TileModifier
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * Two layers: one is transformed tile by tile (a custom [TileModifier] that hides the
 * tile is added or removed), the other is hidden and shown as a whole.
 */
public val layerTransformerExample: Example = Example("Transformer") { context ->
    val origin = Position(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP)
    val layerSize = Size(20, 20)

    val transformingLayer = layer {
        size = layerSize
        offset = origin + Position(1, 5)
    }.apply {
        fill(characterTile {
            character = 'x'
            withStyleSet {
                backgroundColor = Color.TRANSPARENT
                foregroundColor = Color.create(0, 170, 0)
            }
        })
    }

    val hideableLayer = layer {
        size = layerSize
        offset = origin + Position(39, 5)
    }.apply {
        fill(characterTile {
            character = '+'
            withStyleSet {
                backgroundColor = Color.TRANSPARENT
                foregroundColor = Color.create(255, 255, 85)
            }
        })
    }

    val root = vBox { preferredSize = context.contentSize }
    val buttons = hBox { preferredSize = Size(context.contentSize.width, 3); spacing = 1 }
    root.addComponent(buttons)
    buttons.addComponent(toggleButton { +"Transform" }.apply {
        selectedProperty.onChange { (_, selected) ->
            if (selected) {
                transformingLayer.transform { _, tile -> if (tile is CharacterTile) tile.withAddedModifiers(HideModifier) else tile }
            } else {
                transformingLayer.transform { _, tile -> if (tile is CharacterTile) tile.withRemovedModifiers(HideModifier) else tile }
            }
        }
    })
    buttons.addComponent(toggleButton { +"Hide" }.apply {
        selectedProperty.onChange { (_, selected) ->
            if (selected) hideableLayer.hide() else hideableLayer.show()
        }
    })
    ExampleView(root, layers = listOf(transformingLayer, hideableLayer))
}

/** Hides every tile it is applied to: the renderer draws an empty tile instead. */
private object HideModifier : TileModifier<CharacterTile> {

    override val cacheKey: String
        get() = "Modifier.HideModifier"

    override fun canTransform(tile: Tile): Boolean = tile is CharacterTile

    override fun transform(tile: CharacterTile): CharacterTile = tile.withCharacter(' ').withBackgroundColor(Color.TRANSPARENT)
}
