package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.data.Size
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.extensions.scrollOneDown
import org.hexworks.zircon.api.behavior.extensions.scrollOneUp
import org.hexworks.zircon.api.behavior.extensions.scrollTo
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.PreventDefault
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.api.util.isNavigationKey
import org.hexworks.zircon.api.util.isPrintableCharacter
import org.hexworks.zircon.internal.component.extensions.whenConnectedToRoot
import org.hexworks.zircon.internal.component.impl.texteditor.EditorState
import org.hexworks.zircon.internal.component.impl.texteditor.TextEditor
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.AddRowBreak
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.DeleteAtCursor
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.DeleteBeforeCursor
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.InsertCharacter
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.MoveCursorDown
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.MoveCursorLeft
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.MoveCursorRight
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.MoveCursorTo
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.MoveCursorToLineEnd
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.MoveCursorToLineStart
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.MoveCursorUp
import org.hexworks.zircon.internal.event.ZirconEvent
import kotlin.math.abs
import kotlin.math.max

public class DefaultTextArea internal constructor(
    initialText: String,
    componentMetadata: ComponentMetadata,
    renderingStrategy: ComponentRenderingStrategy<TextArea>
) : TextArea,
    DefaultComponent(
        metadata = componentMetadata,
        renderer = renderingStrategy
    ) {

    private var textEditor = TextEditor.fromText(initialText)

    public val editorState: EditorState
        get() = textEditor.state

    // The scrollable part: the visible area is the content area (inside the decorations), so
    // it follows a theme that adds or removes a frame; the actual size grows with the text.
    override val visibleSize: Size
        get() = contentSize
    override val actualSizeProperty: Property<Size> = contentSize.toProperty()
    override var actualSize: Size by actualSizeProperty.asDelegate()
    override val visibleOffsetProperty: Property<Position> = Position.ZERO.toProperty()
    override var visibleOffset: Position by visibleOffsetProperty.asDelegate()

    override var text: String
        get() = textEditor.text
        set(value) {
            textEditor = TextEditor.fromText(value)
            textChanged()
        }

    override var textTiles: List<List<CharacterTile>>
        get() = textEditor.tiles
        set(value) {
            textEditor = TextEditor.fromTiles(value)
            textChanged()
        }

    init {
        resizeIfNecessary()
    }

    public fun getTileAtOrNull(position: Position): CharacterTile? = textEditor.getTileAtOrNull(position)

    override fun focusGiven(): UIEventResponse = whenEnabled {
        scrollCursorIntoView()
        super.focusGiven()
    }

    override fun focusTaken(): UIEventResponse = whenEnabled {
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
    override fun activated(): UIEventResponse = Processed
    override fun deactivated(): UIEventResponse = Processed

    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
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
                scrollCursorIntoView(deleting = event.code == KeyCode.BACKSPACE || event.code == KeyCode.DELETE)
                Processed
            }
        } else Pass
    }

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        textEditor.applyTransformation(
            MoveCursorTo(
                event.position - absolutePosition - contentOffset + visibleOffset
            )
        )
        scrollCursorIntoView()
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

    private fun textChanged() {
        resizeIfNecessary()
        scrollCursorIntoView()
        invalidateRender()
    }

    private fun resizeIfNecessary() {
        val (visibleWidth, visibleHeight) = visibleSize
        val (editorWidth, editorHeight) = textEditor.size
        actualSize = Size(max(visibleWidth, editorWidth), max(visibleHeight, editorHeight))
    }

    /**
     * Scrolls so the editor cursor is inside the visible area, wherever it went (a key moves
     * it one cell, a click or a new text any distance). While [deleting] and scrolled to the
     * right the view follows the shrinking line so the cursor stays at the right edge.
     */
    private fun scrollCursorIntoView(deleting: Boolean = false) {
        val (width, height) = visibleSize
        if (width <= 0 || height <= 0) return
        val cursor = textEditor.cursor
        var (x, y) = visibleOffset
        x = when {
            deleting && x > 0 -> cursor.x - width + 1
            cursor.x < x -> cursor.x
            cursor.x >= x + width -> cursor.x - width + 1
            else -> x
        }
        y = when {
            cursor.y < y -> cursor.y
            cursor.y >= y + height -> cursor.y - height + 1
            else -> y
        }
        scrollTo(Position(x, y))
    }
}
