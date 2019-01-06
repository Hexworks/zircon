package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.input.MouseAction
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
    override var isEnabled = true
        private set

    init {
        this.text = initialText
        refreshVirtualSpaceSize()
    }

    override fun textBuffer() = textBuffer

    override fun acceptsFocus() = isEnabled

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
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
                .build().also {
                    componentStyleSet = it
                    render()
                }
    }

    override fun enable() {
        isEnabled = true
        componentStyleSet.reset()
        render()
    }

    override fun disable() {
        isEnabled = false
        componentStyleSet.applyDisabledStyle()
        render()
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        return if (isEnabled) {
            componentStyleSet.applyFocusedStyle()
            render()
            refreshCursor()
            true
        } else false
    }

    override fun takeFocus(input: Maybe<Input>) {
        componentStyleSet.reset()
        render()
        Zircon.eventBus.publish(
                event = ZirconEvent.HideCursor,
                eventScope = ZirconScope)
    }

    override fun mouseEntered(action: MouseAction) {
        if (isEnabled) {
            componentStyleSet.applyMouseOverStyle()
            render()
        }
    }

    override fun mouseExited(action: MouseAction) {
        if (isEnabled) {
            componentStyleSet.reset()
            render()
        }
    }

    override fun mousePressed(action: MouseAction) {
        if (isEnabled) {
            componentStyleSet.applyActiveStyle()
            render()
        }
    }

    override fun mouseReleased(action: MouseAction) {
        if (isEnabled) {
            componentStyleSet.applyMouseOverStyle()
            render()
        }
    }

    override fun keyStroked(keyStroke: KeyStroke) {
        if (isEnabled) {
            if (isNavigationKey(keyStroke)) {
                return
            }
            when {
                keyStroke.inputTypeIs(InputType.ArrowRight) -> {
                    textBuffer.applyTransformation(MoveCursor(RIGHT))
                }
                keyStroke.inputTypeIs(InputType.ArrowLeft) -> {
                    textBuffer.applyTransformation(MoveCursor(LEFT))
                }
                keyStroke.inputTypeIs(InputType.ArrowDown) -> {
                    textBuffer.applyTransformation(MoveCursor(DOWN))
                }
                keyStroke.inputTypeIs(InputType.ArrowUp) -> {
                    textBuffer.applyTransformation(MoveCursor(UP))
                }
                keyStroke.inputTypeIs(InputType.Delete) -> {
                    textBuffer.applyTransformation(DeleteCharacter(DEL))
                }
                keyStroke.inputTypeIs(InputType.Backspace) -> {
                    textBuffer.applyTransformation(DeleteCharacter(BACKSPACE))
                }
                keyStroke.inputTypeIs(InputType.Enter) -> {
                    textBuffer.applyTransformation(AddRowBreak())
                }
                keyStroke.inputTypeIs(InputType.Home) -> {
                    // TODO:
                }
                keyStroke.inputTypeIs(InputType.End) -> {
                    // TODO:
                }
                TextUtils.isPrintableCharacter(keyStroke.getCharacter()) -> {
                    textBuffer.applyTransformation(InsertCharacter(keyStroke.getCharacter()))
                }
            }
            refreshVirtualSpaceSize()
            scrollToCursor()
            refreshCursor()
            render()
        }
    }

    private fun isNavigationKey(keyStroke: KeyStroke) =
            keyStroke.inputType() == InputType.Tab || keyStroke.inputType() == InputType.ReverseTab

    override fun render() {
        renderingStrategy.render(this, graphics)
    }

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
                        .withRelative(absolutePosition + contentPosition)),
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

}
