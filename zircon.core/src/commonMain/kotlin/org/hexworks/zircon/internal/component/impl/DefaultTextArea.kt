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
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable
import org.hexworks.zircon.internal.component.impl.texteditor.TextEditor
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.AddRowBreak
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.DeleteBack
import org.hexworks.zircon.internal.component.impl.texteditor.transformation.InsertCharacter
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconEvent.RequestCursorAt
import kotlin.math.max

class DefaultTextArea internal constructor(
    initialText: String,
    componentMetadata: ComponentMetadata,
    renderingStrategy: ComponentRenderingStrategy<TextArea>
) : TextArea,
    Scrollable by DefaultScrollable(componentMetadata.size, componentMetadata.size),
    DefaultComponent(
        metadata = componentMetadata,
        renderer = renderingStrategy
    ) {

    private var textEditor = TextEditor.fromText(initialText)

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
        refreshVirtualSpaceSize()
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
        focusedStyle = styleSet {
            foregroundColor = colorTheme.primaryBackgroundColor
            backgroundColor = colorTheme.primaryForegroundColor
        }
    }

    override fun focusGiven() = whenEnabled {
        refreshCursor()
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

    // This component shouldn't respond to the activation key (<Spacebar> probably)
    override fun activated() = Processed
    override fun deactivated() = Processed

    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == TARGET) {
            if (event.isNavigationKey()) {
                Pass
            } else {
                // TODO:
                when (event.code) {
//                    KeyCode.RIGHT -> textBuffer.applyTransformation(MoveCursor(RIGHT))
//                    KeyCode.LEFT -> textBuffer.applyTransformation(MoveCursor(LEFT))
//                    KeyCode.DOWN -> textBuffer.applyTransformation(MoveCursor(DOWN))
//                    KeyCode.UP -> textBuffer.applyTransformation(MoveCursor(UP))
//                    KeyCode.DELETE -> textBuffer.applyTransformation(DeleteCharacter(DEL))
                    KeyCode.BACKSPACE -> textEditor.applyTransformation(DeleteBack)
                    KeyCode.ENTER -> textEditor.applyTransformation(AddRowBreak)
                    KeyCode.HOME -> {
                        // TODO
                    }

                    KeyCode.END -> {
                        // TODO
                    }

                    else -> {
                        event.key.forEach { char ->
                            if (char.isPrintableCharacter()) {
                                textEditor.applyTransformation(InsertCharacter(characterTile { +char }))
                            }
                        }
                    }
                }
                refreshVirtualSpaceSize()
                scrollToCursor()
                refreshCursor()
                Processed
            }
        } else Pass
    }

    private fun scrollToCursor() {
        // 📕 TODO
    }


    private fun refreshCursor() = whenConnectedToRoot { root ->

        val cursorPos = textEditor.cursor + visibleOffset

        root.eventBus.publish(
            event = RequestCursorAt(
                position = cursorPos, // 📕 TODO
                emitter = this
            ),
            eventScope = root.eventScope
        )
    }

    private fun refreshVirtualSpaceSize() {
        val (visibleWidth, visibleHeight) = visibleSize
        val (editorWidth, editorHeight) = textEditor.size
        actualSize = size {
            width = max(visibleWidth, editorWidth)
            height = max(visibleHeight, editorHeight)
        }
    }
}
