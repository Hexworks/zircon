package org.hexworks.zircon.examples.game

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withRelativeY
import org.hexworks.zircon.api.graphics.LayerHandle
import org.hexworks.zircon.api.graphics.extensions.fill
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** An `@` on its own layer, moved with the arrow keys. */
public val playerMoveExample: Example = Example("Player move") { context ->
    val start = Position(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP) +
        Position(context.contentSize.width / 2, context.contentSize.height / 2)
    val player = layer {
        size = Size.ONE
        offset = start
    }.apply { fill(PLAYER_TILE) }

    // The layer is added by hand so its handle (which can move) is at hand.
    var handle: LayerHandle? = null
    var keys: Subscription? = null
    ExampleView(
        content = context.emptyContent(),
        onShown = {
            handle = context.screen.addLayer(player)
            keys = context.screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event, _ ->
                val layer = handle ?: return@handleKeyboardEvents Pass
                when (event.code) {
                    KeyCode.UP -> layer.moveTo(layer.position.withRelativeY(-1))
                    KeyCode.DOWN -> layer.moveTo(layer.position.withRelativeY(1))
                    KeyCode.LEFT -> layer.moveTo(layer.position.withRelativeX(-1))
                    KeyCode.RIGHT -> layer.moveTo(layer.position.withRelativeX(1))
                    else -> return@handleKeyboardEvents Pass
                }
                Processed
            }
        },
        onHidden = {
            keys?.dispose()
            handle?.removeLayer()
            handle = null
        },
    )
}

private val PLAYER_TILE = characterTile {
    character = '@'
    withStyleSet {
        backgroundColor = DefaultAnsiPalette[ANSIColor.BLACK]
        foregroundColor = DefaultAnsiPalette[ANSIColor.WHITE]
    }
}
