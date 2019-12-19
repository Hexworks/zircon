package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.util.TextUtils
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable
import org.hexworks.zircon.internal.component.impl.textedit.EditableTextBuffer
import org.hexworks.zircon.internal.component.impl.textedit.cursor.MovementDirection.*
import org.hexworks.zircon.internal.component.impl.textedit.transformation.AddRowBreak
import org.hexworks.zircon.internal.component.impl.textedit.transformation.DeleteCharacter
import org.hexworks.zircon.internal.component.impl.textedit.transformation.DeleteCharacter.DeleteKind.BACKSPACE
import org.hexworks.zircon.internal.component.impl.textedit.transformation.DeleteCharacter.DeleteKind.DEL
import org.hexworks.zircon.internal.component.impl.textedit.transformation.InsertCharacter
import org.hexworks.zircon.internal.component.impl.textedit.transformation.MoveCursor
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import kotlin.math.min

@Suppress("DuplicatedCode")
class DefaultTextArea constructor(
        initialText: String,
        componentMetadata: ComponentMetadata,
        private val renderingStrategy: ComponentRenderingStrategy<TextArea>)
    : TextArea,
        Scrollable by DefaultScrollable(componentMetadata.size, componentMetadata.size),
        DefaultComponent(
                componentMetadata = componentMetadata,
                renderer = renderingStrategy) {

    override var text: String
        get() = textBuffer.getText()
        set(value) {
            textBuffer = EditableTextBuffer.create(value)
            render()
        }

    private var textBuffer = EditableTextBuffer.create(initialText)

    init {
        this.text = initialText
        refreshVirtualSpaceSize()
    }

    override fun textBuffer() = textBuffer

    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(colorTheme.secondaryBackgroundColor)
                    .withBackgroundColor(colorTheme.secondaryForegroundColor)
                    .build())
            .withDisabledStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(colorTheme.secondaryForegroundColor)
                    .withBackgroundColor(TileColor.transparent())
                    .build())
            .withFocusedStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(colorTheme.primaryBackgroundColor)
                    .withBackgroundColor(colorTheme.primaryForegroundColor)
                    .build())
            .build()

    override fun focusGiven() = whenEnabled {
        componentStyleSet.applyFocusedStyle()
        refreshCursor()
        render()
    }

    override fun focusTaken() = whenEnabled {
        componentStyleSet.reset()
        Zircon.eventBus.publish(
                event = ZirconEvent.HideCursor,
                eventScope = ZirconScope)
        render()
    }

    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            if (isNavigationKey(event)) {
                Pass
            } else {
                when (event.code) {
                    KeyCode.RIGHT -> textBuffer.applyTransformation(MoveCursor(RIGHT))
                    KeyCode.LEFT -> textBuffer.applyTransformation(MoveCursor(LEFT))
                    KeyCode.DOWN -> textBuffer.applyTransformation(MoveCursor(DOWN))
                    KeyCode.UP -> textBuffer.applyTransformation(MoveCursor(UP))
                    KeyCode.DELETE -> textBuffer.applyTransformation(DeleteCharacter(DEL))
                    KeyCode.BACKSPACE -> textBuffer.applyTransformation(DeleteCharacter(BACKSPACE))
                    KeyCode.ENTER -> textBuffer.applyTransformation(AddRowBreak())
                    KeyCode.HOME -> {
                        // TODO
                    }
                    KeyCode.END -> {
                        // TODO
                    }
                    else -> {
                        event.key.forEach { char ->
                            if (TextUtils.isPrintableCharacter(char)) {
                                textBuffer.applyTransformation(InsertCharacter(char))
                            }
                        }
                    }
                }
                refreshVirtualSpaceSize()
                scrollToCursor()
                refreshCursor()
                render()
                Processed
            }
        } else Pass
    }

    private fun isNavigationKey(event: KeyboardEvent) =
            event == TAB || event == REVERSE_TAB

    private fun scrollToCursor() {
        val bufferCursorPos = textBuffer.cursor.position
        when {
            bufferCursorOverflowsLeft(bufferCursorPos) -> {
                val delta = visibleOffset.x - bufferCursorPos.x
                scrollLeftBy(delta)
            }
            bufferCursorPosOverlapsRight(bufferCursorPos) -> {
                val delta = bufferCursorPos.x - visibleOffset.x - visibleSize.width
                scrollRightBy(delta + 1)
            }
            bufferCursorOverflowsUp(bufferCursorPos) -> {
                val delta = visibleOffset.y - bufferCursorPos.y
                scrollUpBy(delta)
            }
            bufferCursorPosOverlapsDown(bufferCursorPos) -> {
                val delta = bufferCursorPos.y - visibleOffset.y - visibleSize.height
                scrollDownBy(delta + 1)
            }
        }
        render()
    }

    private fun bufferCursorPosOverlapsDown(bufferCursorPos: Position) =
            bufferCursorPos.y >= visibleOffset.y + visibleSize.height

    private fun bufferCursorOverflowsUp(bufferCursorPos: Position) =
            bufferCursorPos.y < visibleOffset.y

    private fun bufferCursorPosOverlapsRight(bufferCursorPos: Position) =
            bufferCursorPos.x >= visibleOffset.x + visibleSize.width

    private fun bufferCursorOverflowsLeft(bufferPos: Position) =
            bufferPos.x < visibleOffset.x

    private fun refreshCursor() {
        var pos = textBuffer.cursor.position
                .minus(visibleOffset)
        pos = pos.withX(min(pos.x, contentSize.width))
        pos = pos.withY(min(pos.y, contentSize.height))
        Zircon.eventBus.publish(
                event = ZirconEvent.RequestCursorAt(pos
                        .withRelative(position + contentOffset)),
                eventScope = ZirconScope)
    }

    private fun refreshVirtualSpaceSize() {
        val (actualWidth, actualHeight) = actualSize
        val (bufferWidth, bufferHeight) = textBuffer.getBoundingBoxSize()
        if (bufferWidth >= actualWidth) {
            actualSize = actualSize.withWidth(bufferWidth)
        }
        if (bufferHeight >= actualHeight) {
            actualSize = actualSize.withHeight(bufferHeight)
        }
    }

    companion object {
        val TAB = KeyboardEvent(
                type = KeyboardEventType.KEY_RELEASED,
                key = "\t",
                code = KeyCode.TAB)

        val REVERSE_TAB = KeyboardEvent(
                type = KeyboardEventType.KEY_RELEASED,
                key = "\t",
                code = KeyCode.TAB,
                shiftDown = true)

    }
}
