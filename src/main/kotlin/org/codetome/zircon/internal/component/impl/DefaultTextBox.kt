package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.TextBox
import org.codetome.zircon.api.component.Theme
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.util.TextUtils
import org.codetome.zircon.internal.behavior.Scrollable
import org.codetome.zircon.internal.behavior.impl.DefaultScrollable
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.event.Subscription
import org.codetome.zircon.internal.util.TextBuffer
import java.util.*

class DefaultTextBox @JvmOverloads constructor(text: String,
                                               initialSize: Size,
                                               position: Position,
                                               componentStyles: ComponentStyles,
                                               scrollable: Scrollable = DefaultScrollable(initialSize, initialSize))
    : TextBox, Scrollable by scrollable, DefaultComponent(
        initialSize = initialSize,
        position = position,
        componentStyles = componentStyles,
        wrappers = listOf()) {

    private val textBuffer = TextBuffer(text)
    private val subscriptions = mutableListOf<Subscription<*>>()

    init {
        setText(text)
        refreshDrawSurface()
        refreshVirtualSpaceSize()
        EventBus.subscribe<MouseAction>(EventType.MousePressed(getId()), {
            EventBus.emit(EventType.RequestFocusAt, getId())
        })
    }

    override fun getText() = textBuffer.getText() // TODO: line sep?

    override fun setText(text: String) {
        textBuffer.setText(text)
    }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Optional<Input>): Boolean {
        getDrawSurface().applyStyle(getComponentStyles().giveFocus())
        EventBus.emit(EventType.RequestCursorAt, getCursorPosition().withRelative(getPosition()))
        EventBus.emit(EventType.ComponentChange)
        subscriptions.add(EventBus.subscribe<KeyStroke>(EventType.KeyPressed, { (keyStroke) ->
            val cursorPos = getCursorPosition()
            val (offsetCols, offsetRows) = getVisibleOffset()
            val bufferRowIdx = cursorPos.row + offsetRows
            val bufferColIdx = cursorPos.column + offsetCols
            val prevRowIdx = offsetRows + cursorPos.row - 1
            val prevRow = textBuffer.getRow(prevRowIdx)
            val nextRowIdx = offsetRows + cursorPos.row + 1
            val nextRow = textBuffer.getRow(nextRowIdx)
            val prevColIdx = Math.max(bufferColIdx - 1, 0)
            val nextChar = textBuffer.getCharAt(getVisibleOffset() + getCursorPosition())

            if (keyStroke.inputTypeIs(InputType.ArrowRight)) {
                if (nextChar.isPresent) {
                    if (isCursorAtTheEndOfTheLine()) {
                        scrollOneRight()
                        refreshDrawSurface()
                    } else {
                        moveCursorForward()
                    }
                }
            } else if (keyStroke.inputTypeIs(InputType.ArrowLeft)) {
                if (isCursorAtTheStartOfTheLine() && getVisibleOffset().column > 0) {
                    scrollOneLeft()
                    refreshDrawSurface()
                } else {
                    moveCursorBackward()
                }
            } else if (keyStroke.inputTypeIs(InputType.ArrowDown)) {
                if (nextRow.isPresent) {
                    if (isCursorAtTheLastRow()) {
                        scrollOneDown()
                    } else {
                        putCursorAt(getCursorPosition().withRelativeRow(1))
                    }
                    putCursorAt(getCursorPosition().withColumn(Math.min(nextRow.get().length, cursorPos.column)))
                    refreshDrawSurface()
                }
            } else if (keyStroke.inputTypeIs(InputType.ArrowUp)) {
                if (prevRow.isPresent) {
                    if (isCursorAtTheFirstRow()) {
                        scrollOneUp()
                    } else {
                        putCursorAt(getCursorPosition().withRelativeRow(-1))
                    }
                    putCursorAt(getCursorPosition().withColumn(Math.min(prevRow.get().length, cursorPos.column)))
                    refreshDrawSurface()
                }
            } else if (keyStroke.inputTypeIs(InputType.Delete)) {
                textBuffer.getRow(bufferRowIdx).map {
                    if (it.isBlank()) {
                        deleteRowIfNotLast(bufferRowIdx)
                        refreshDrawSurface()
                        refreshVirtualSpaceSize()
                    }
                    if (bufferColIdx < it.length) {
                        it.deleteCharAt(bufferColIdx)
                        refreshDrawSurface()
                        refreshVirtualSpaceSize()
                    }
                }
            } else if (keyStroke.inputTypeIs(InputType.Backspace)) {
                textBuffer.getRow(bufferRowIdx).map {
                    if (it.isBlank()) {
                        deleteRowIfNotLast(bufferRowIdx)
                        refreshDrawSurface()
                        refreshVirtualSpaceSize()
                    }
                    if (bufferColIdx != 0 && prevColIdx < it.length) {
                        it.deleteCharAt(prevColIdx)
                        if (isCursorAtTheStartOfTheLine()) {
                            scrollOneLeft()
                        } else {
                            moveCursorBackward()
                        }
                        refreshDrawSurface()
                        refreshVirtualSpaceSize()
                    }
                }
            } else if (keyStroke.inputTypeIs(InputType.Enter)) {
                textBuffer.addNewRowAt(bufferRowIdx + 1)
                refreshVirtualSpaceSize()
                refreshDrawSurface()
                if (isCursorAtTheLastRow()) {
                    scrollOneDown()
                } else {
                    putCursorAt(getCursorPosition().withRelativeRow(1))
                }
            } else if (TextUtils.isPrintableCharacter(keyStroke.getCharacter())) {
                textBuffer.getRow(bufferRowIdx).map {
                    it.insert(bufferColIdx, keyStroke.getCharacter())
                    refreshVirtualSpaceSize()
                    if (isCursorAtTheEndOfTheLine()) {
                        scrollOneRight()
                    } else {
                        moveCursorForward()
                    }
                    refreshDrawSurface()
                }
            }
            EventBus.emit(EventType.RequestCursorAt, getCursorPosition() + getPosition())
            EventBus.emit(EventType.ComponentChange)
        }))
        return true
    }

    private fun deleteRowIfNotLast(bufferRowIdx: Int) {
        if (textBuffer.getSize() > 1) {
            textBuffer.deleteRowAt(bufferRowIdx)
        }
    }

    override fun takeFocus(input: Optional<Input>) {
        subscriptions.forEach {
            EventBus.unsubscribe(it)
        }
        subscriptions.clear()
        getDrawSurface().applyStyle(getComponentStyles().reset())
        EventBus.emit(EventType.HideCursor)
        EventBus.emit(EventType.ComponentChange)
    }

    override fun applyTheme(theme: Theme) {
        setComponentStyles(ComponentStylesBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(theme.getDarkBackgroundColor())
                        .backgroundColor(theme.getDarkForegroundColor())
                        .build())
                .focusedStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(theme.getBrightBackgroundColor())
                        .backgroundColor(theme.getBrightForegroundColor())
                        .build())
                .build())
    }

    private fun refreshDrawSurface() {
        getBoundableSize().fetchPositions().forEach { pos ->
            val fixedPos = pos + getVisibleOffset()
            getDrawSurface().setCharacterAt(pos, textBuffer.getCharAt(fixedPos).orElse(' '))
        }
    }

    private fun refreshVirtualSpaceSize() {
        setVirtualSpaceSize(textBuffer.getBoundingBoxSize())
    }
}