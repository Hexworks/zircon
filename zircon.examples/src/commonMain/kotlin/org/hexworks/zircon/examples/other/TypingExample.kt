package org.hexworks.zircon.examples.other

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * Typing with the grid cursor: typed characters land where the cursor is, Enter starts a
 * new line, Backspace deletes. The cursor is only shown while this example is visible.
 */
public val typingExample: Example = Example("Typing") { context ->
    val screen = context.screen
    val area = Size(context.contentSize.width - 4, context.contentSize.height - 4)
    val origin = Position(EXAMPLE_TAB_WIDTH + 2, EXAMPLE_TOP + 2)
    val page = layer {
        size = area
        offset = origin
    }
    val theme = screen.theme
    var cursor = Position.ZERO
    fun syncCursor() {
        screen.cursorPosition = origin + cursor
    }
    fun put(char: Char) {
        page.draw(characterTile {
            character = char
            withStyleSet {
                foregroundColor = theme.primaryForegroundColor
                backgroundColor = theme.primaryBackgroundColor
            }
        }, cursor)
    }
    var subscription: Subscription? = null
    ExampleView(
        content = context.emptyContent(),
        layers = listOf(page),
        onShown = {
            screen.isCursorVisible = true
            syncCursor()
            subscription = screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event, _ ->
                when {
                    event.code == KeyCode.ENTER -> cursor = Position(0, minOf(cursor.y + 1, area.height - 1))
                    event.code == KeyCode.BACKSPACE -> {
                        if (cursor.x > 0) cursor = cursor.withRelativeX(-1)
                        put(' ')
                    }
                    event.key.length == 1 && event.key[0] >= ' ' && !event.ctrlDown && !event.altDown -> {
                        put(event.key[0])
                        cursor = if (cursor.x + 1 < area.width) cursor.withRelativeX(1)
                        else Position(0, minOf(cursor.y + 1, area.height - 1))
                    }
                    else -> return@handleKeyboardEvents Pass
                }
                syncCursor()
                Processed
            }
        },
        onHidden = {
            subscription?.dispose()
            subscription = null
            screen.isCursorVisible = false
        },
    )
}
