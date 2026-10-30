package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.builder.graphics.characterTileString
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.extensions.draw
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Shows the grid's cursor after a line of text; it is hidden again when the example is left. */
public val cursorExample: Example = Example("Cursor") { context ->
    val canvas = layer {
        size = context.contentSize
        offset = Position(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP)
    }
    val text = "Cursor example..."
    canvas.draw(characterTileString { +text }, Position(2, 2))
    val screen = context.screen
    ExampleView(
        content = context.emptyContent(),
        layers = listOf(canvas),
        onShown = {
            screen.cursorPosition = Position(EXAMPLE_TAB_WIDTH + 2 + text.length, EXAMPLE_TOP + 2)
            screen.isCursorVisible = true
        },
        onHidden = { screen.isCursorVisible = false },
    )
}
