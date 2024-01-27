package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.size
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import org.hexworks.zircon.api.util.isNavigationKey
import org.hexworks.zircon.api.util.isPrintableCharacter
import org.hexworks.zircon.internal.component.impl.texteditor.EditorState
import org.hexworks.zircon.internal.component.impl.texteditor.TextEditor
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.*
import org.hexworks.zircon.internal.event.ZirconEvent
import kotlin.math.abs
import kotlin.math.max

class DefaultTextArea internal constructor(
    initialText: String,
    componentMetadata: ComponentMetadata,
    renderingStrategy: ComponentRenderingStrategy<TextArea>
) : TextArea,
    Scrollable by Scrollable.create(componentMetadata.size, componentMetadata.size),
    DefaultComponent(
        metadata = componentMetadata,
        renderer = renderingStrategy
    ) {

    private var textEditor = TextEditor.fromText(initialText)

    val editorState: EditorState
        get() = textEditor.state

    override var text: String
        get() = textEditor.text
        set(value) {
            textEditor = TextEditor.fromText(value)
        }

    override var textTiles: List<List<CharacterTile>>
        get() = textEditor.tiles
        set(value) {
            textEditor = TextEditor.fromTiles(value)
        }

    init {
        resizeIfNecessary()
        actualSizeProperty.onChange { (_, newValue) ->
            val lastOkIndex = newValue.height - 1;
            if(lastOkIndex < visibleOffset.y) {
                visibleOffset = visibleOffset.withY(lastOkIndex)
            }
        }
    }

    fun getTileAtOrNull(position: Position): CharacterTile? = textEditor.getTileAtOrNull(position)

    override fun convertColorTheme(colorTheme: ColorTheme) = componentStyleSet {
        defaultStyle = styleSet {
            foregroundColor = colorTheme.secondaryBackgroundColor
            backgroundColor = colorTheme.secondaryForegroundColor
        }
        disabledStyle = styleSet {
            foregroundColor = colorTheme.secondaryForegroundColor
            backgroundColor = transparent()
        }
        // 📙 Note that the following 3 are the same because otherwise the component
        // would react to hover making it flicker when it is already focused.
        focusedStyle = styleSet {
            foregroundColor = colorTheme.primaryBackgroundColor
            backgroundColor = colorTheme.primaryForegroundColor
        }
        highlightedStyle = styleSet {
            foregroundColor = colorTheme.primaryBackgroundColor
            backgroundColor = colorTheme.primaryForegroundColor
        }
        activeStyle = styleSet {
            foregroundColor = colorTheme.primaryBackgroundColor
            backgroundColor = colorTheme.primaryForegroundColor
        }
    }

    override fun focusGiven() = whenEnabled {
        refreshCursorAndScroll(KeyCode.UNKNOWN)
        super.focusGiven()
    }

    override fun focusTaken() = whenEnabled {
        whenConnectedToRoot { root ->
            root.eventBus.publish(
                event = ZirconEvent.HideCursor(this),
                eventScope = root.eventScope
            )
        }
        super.focusTaken()
    }

    // 📙 We need to mark these processed otherwise the component would respond to the
    // activation key (<Spacebar> probably) pressed making it flicker when active
    override fun activated() = Processed
    override fun deactivated() = Processed

    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == TARGET) {
            if (event.isNavigationKey()) {
                // we don't want to override regular component navigation keys (<Tab>, <Shift>+<Tab> by default)
                Pass
            } else {
                // TODO:
                when (event.code) {
                    KeyCode.RIGHT -> textEditor.applyTransformation(MoveCursorRight)
                    KeyCode.LEFT -> textEditor.applyTransformation(MoveCursorLeft)
                    KeyCode.UP -> textEditor.applyTransformation(MoveCursorUp)
                    KeyCode.DOWN -> textEditor.applyTransformation(MoveCursorDown)
                    KeyCode.DELETE -> textEditor.applyTransformation(DeleteAtCursor)
                    KeyCode.BACKSPACE -> textEditor.applyTransformation(DeleteBeforeCursor)
                    KeyCode.ENTER -> textEditor.applyTransformation(AddRowBreak)
                    KeyCode.HOME -> textEditor.applyTransformation(MoveCursorToLineStart)
                    KeyCode.END -> textEditor.applyTransformation(MoveCursorToLineEnd)
                    else -> {
                        event.key.forEach { char ->
                            if (char.isPrintableCharacter()) {
                                textEditor.applyTransformation(InsertCharacter(characterTile { +char }))
                            }
                        }
                    }
                }
                resizeIfNecessary()
                refreshCursorAndScroll(event.code)
                Processed
            }
        } else Pass
    }

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        textEditor.applyTransformation(
            MoveCursorTo(
                event.position - position + visibleOffset
            )
        )
        return Processed
    }

    override fun mouseWheelRotatedUp(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        scrollOneDown()
        return PreventDefault
    }

    override fun mouseWheelRotatedDown(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        scrollOneUp()
        return PreventDefault
    }

    private fun resizeIfNecessary() {
        val (visibleWidth, visibleHeight) = visibleSize
        val (editorWidth, editorHeight) = textEditor.size
        actualSize = size {
            width = max(visibleWidth, editorWidth)
            height = max(visibleHeight, editorHeight)
        }
    }

    /**
     * When editing the contents it is possible that the editor cursor goes out of bounds relative
     * to the bounds of the actual text box. In this case we need to scroll in the appropriate
     * direction and also fix the position at which we'll draw the visible cursor
     */
    private fun refreshCursorAndScroll(code: KeyCode) = whenConnectedToRoot { _ ->
        val cursorPosCandidate = textEditor.cursor - visibleOffset

        val deleting = code == KeyCode.BACKSPACE
        val wentTooFarRight = cursorPosCandidate.x >= visibleSize.width
        val wentTooFarLeft = cursorPosCandidate.x < 0
        val wentTooFarUp = cursorPosCandidate.y == -1
        val wentTooFarDown = cursorPosCandidate.y == visibleSize.height
        val hasXOffset = visibleOffset.x > 0

        when {
            // 📕 == === SPECIAL CASE === ==
            // we've just deleted a character, but we're still scrolled to the right,
            // so we just scroll back and move the cursor right to keep the cursor at the edge
            // while deleting
            deleting && hasXOffset -> {
                scrollOneLeft()
            }
            // 📘 horizontal cases
            wentTooFarRight -> {
                val diff = cursorPosCandidate.x - visibleSize.width
                scrollRightBy(diff + 1)
            }

            wentTooFarLeft -> {
                val overflow = abs(cursorPosCandidate.x)
                scrollLeftBy(overflow)
            }
            // 📘 vertical cases
            wentTooFarUp -> {
                scrollOneUp()
            }

            wentTooFarDown -> {
                scrollOneDown()
            }
        }
    }
}
