package org.hexworks.zircon.examples.modifiers

import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.ExampleContext

/** A layer covering the example's content area, positioned on the screen. */
internal fun ExampleContext.contentLayer(): Layer = layer {
    size = contentSize
    offset = Position(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP)
    tileset = screen.tileset
}

/**
 * Draws [text] at [position] on this layer, one tile per character in [color], each with
 * the modifier [modifierFor] builds for its index (none when `null`). Modifiers keep their
 * own timers, so a fresh one per tile (and per call) restarts the effect.
 */
internal fun Layer.drawText(
    text: String,
    position: Position,
    color: Color,
    modifierFor: ((index: Int) -> Modifier)? = null,
) {
    text.forEachIndexed { index, char ->
        var tile: CharacterTile = Tile.defaultTile()
            .withBackgroundColor(Color.TRANSPARENT)
            .withForegroundColor(color)
            .withCharacter(char)
        modifierFor?.let { tile = tile.withModifiers(it(index)) }
        draw(tile, position.withRelativeX(index))
    }
}
