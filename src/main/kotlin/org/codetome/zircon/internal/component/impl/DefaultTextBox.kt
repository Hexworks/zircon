package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.TextBox
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.util.TextUtils
import org.codetome.zircon.internal.behavior.Scrollable
import org.codetome.zircon.internal.behavior.impl.DefaultScrollable
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.event.Subscription
import org.codetome.zircon.internal.util.TextBuffer
import java.awt.image.BufferedImage
import java.util.*

class DefaultTextBox @JvmOverloads constructor(text: String,
                                               initialSize: Size,
                                               initialFont: Font,
                                               position: Position,
                                               componentStyles: ComponentStyles,
                                               scrollable: Scrollable = DefaultScrollable(initialSize, initialSize))
    : TextBox, Scrollable by scrollable, DefaultComponent(
        initialSize = initialSize,
        position = position,
        componentStyles = componentStyles,
        wrappers = listOf(),
        initialFont = initialFont) {

    private val textBuffer = TextBuffer(text)
    private val subscriptions = mutableListOf<Subscription<*>>()

    init {
        setText(text)
        refreshDrawSurface()
        refreshVirtualSpaceSize()
    }

    override fun getText() = textBuffer.getText() // TODO: line sep?

    override fun setText(text: String) =
            if (this.textBuffer.toString() == text) {
                false
            } else {
                textBuffer.setText(text)
                true
            }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Optional<Input>): Boolean {
        clearSubscriptions()
        getDrawSurface().applyColorsFromStyle(getComponentStyles().giveFocus())
        EventBus.emit(EventType.RequestCursorAt, getCursorPosition().withRelative(getPosition()))
        EventBus.emit(EventType.ComponentChange)
        subscriptions.add(EventBus.subscribe<KeyStroke>(EventType.KeyPressed, { (keyStroke) ->
            val cursorPos = getCursorPosition()
            val (offsetCols, offsetRows) = getVisibleOffset()
            val currColIdx = cursorPos.column + offsetCols
            val prevColIdx = Math.max(currColIdx - 1, 0)
            val currRowIdx = cursorPos.row + offsetRows
            val prevRowIdx = offsetRows + cursorPos.row - 1
            val nextRowIdx = offsetRows + cursorPos.row + 1
            val maybePrevRow = textBuffer.getRow(prevRowIdx)
            val maybeNextRow = textBuffer.getRow(nextRowIdx)
            val maybeCurrRow = textBuffer.getRow(currRowIdx)
            val nextChar = textBuffer.getCharAt(getVisibleOffset() + getCursorPosition())
            // TODO: this should be a state machine
            // refactor this later
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
                if (isCursorAtTheStartOfTheLine()) {
                    if (getVisibleOffset().column > 0) {
                        // we can still scroll left because there are hidden parts of the left section
                        scrollOneLeft()
                        refreshDrawSurface()
                    } else if (maybePrevRow.isPresent) {
                        scrollUpToEndOfPreviousLine(maybePrevRow.get())
                    }
                } else {
                    moveCursorBackward()
                }
            } else if (keyStroke.inputTypeIs(InputType.ArrowDown)) {
                maybeNextRow.map { nextRow ->
                    if (isCursorAtTheLastRow()) {
                        scrollOneDown()
                    } else {
                        putCursorAt(getCursorPosition().withRelativeRow(1))
                    }
                    scrollLeftToRowEnd(nextRow)
                }
            } else if (keyStroke.inputTypeIs(InputType.ArrowUp)) {
                maybePrevRow.map { prevRow ->
                    scrollCursorOrScrollableOneUp()
                    scrollLeftToRowEnd(prevRow)
                }
            } else if (keyStroke.inputTypeIs(InputType.Delete)) {
                textBuffer.getRow(currRowIdx).map { row ->
                    if (currColIdx == row.length) { // end of the line
                        // this is necessary because if there is no next row and we
                        // delete the current row we won't be able to type anything (since the row is deleted)
                        maybeNextRow.map { nextRow ->
                            val nextRowContent = nextRow.toString()
                            if (row.isBlank()) {
                                deleteRowIfNotLast(currRowIdx)
                            } else {
                                deleteRowIfNotLast(nextRowIdx)
                            }
                            row.append(nextRowContent)
                        }
                    } else {
                        row.deleteCharAt(currColIdx)
                    }
                    refreshDrawSurface()
                    refreshVirtualSpaceSize()
                }
            } else if (keyStroke.inputTypeIs(InputType.Backspace)) {
                textBuffer.getRow(currRowIdx).map { row ->
                    if (currColIdx == 0) { // start of the line
                        maybePrevRow.map { prevRow ->
                            val currRowContent = row.toString()
                            deleteRowIfNotLast(currRowIdx)
                            scrollUpToEndOfPreviousLine(prevRow)
                            prevRow.append(currRowContent)
                        }
                    } else {
                        row.deleteCharAt(currColIdx - 1)
                        scrollCursorOrScrollableOneLeft()
                    }
                    refreshDrawSurface()
                    refreshVirtualSpaceSize()
                }
            } else if (keyStroke.inputTypeIs(InputType.Enter)) {
                val newRowIdx = currRowIdx + 1
                textBuffer.addNewRowAt(newRowIdx)
                textBuffer.getRow(currRowIdx).map { oldRow ->
                    textBuffer.getRow(newRowIdx).map { newRow ->
                        newRow.append(oldRow.substring(currColIdx).trim())
                    }
                    oldRow.delete(currColIdx, oldRow.length)
                }
                refreshVirtualSpaceSize()
                if (isCursorAtTheLastRow()) {
                    scrollOneDown()
                } else {
                    putCursorAt(getCursorPosition().withRelativeRow(1))
                }
                scrollLeftTo(0)
                putCursorAt(getCursorPosition().withColumn(0))
                refreshDrawSurface()
            } else if (keyStroke.inputTypeIs(InputType.Home)) {
                scrollLeftTo(0)
                putCursorAt(getCursorPosition().withColumn(0))
            } else if (keyStroke.inputTypeIs(InputType.End)) {
                scrollRightToRowEnd(maybeCurrRow.get())
                putCursorAt(getCursorPosition().withColumn(getBoundableSize().columns - 1))
            } else if (TextUtils.isPrintableCharacter(keyStroke.getCharacter())) {
                textBuffer.getRow(currRowIdx).map {
                    if (isCursorAtTheEndOfTheLine()) {
                        it.append(keyStroke.getCharacter())
                    } else {
                        it.insert(currColIdx, keyStroke.getCharacter())
                    }
                    refreshDrawSurface()
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

    override fun takeFocus(input: Optional<Input>) {
        subscriptions.forEach {
            EventBus.unsubscribe(it)
        }
        getDrawSurface().applyColorsFromStyle(getComponentStyles().reset())
        EventBus.emit(EventType.HideCursor)
        EventBus.emit(EventType.ComponentChange)
    }

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStylesBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getDarkBackgroundColor())
                        .backgroundColor(colorTheme.getDarkForegroundColor())
                        .build())
                .focusedStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getBrightBackgroundColor())
                        .backgroundColor(colorTheme.getBrightForegroundColor())
                        .build())
                .build())
    }

    private fun scrollUpToEndOfPreviousLine(prevRow: StringBuilder) {
        // we move the cursor up
        scrollCursorOrScrollableOneUp()
        // we set the position to be able to see the end of the row
        scrollRightToRowEnd(prevRow)
        // we fix the cursor if the row does not fill the visible space
        putCursorAt(getCursorPosition()
                .withColumn(Math.min(prevRow.length, getCursorPosition().column)))
        refreshDrawSurface()
        refreshVirtualSpaceSize()
    }

    private fun scrollCursorOrScrollableOneLeft() {
        if (isCursorAtTheStartOfTheLine()) {
            scrollOneLeft()
        } else {
            moveCursorBackward()
        }
    }

    private fun scrollCursorOrScrollableOneUp() {
        if (isCursorAtTheFirstRow()) {
            scrollOneUp()
        } else {
            putCursorAt(getCursorPosition().withRelativeRow(-1))
        }
    }

    private fun scrollLeftToRowEnd(row: StringBuilder) {
        val visibleCharCount = row.length - getVisibleOffset().column
        if (row.length > getVisibleOffset().column) {
            if (visibleCharCount < getCursorPosition().column) {
                putCursorAt(getCursorPosition().withColumn(row.length - getVisibleOffset().column))
            }
        } else {
            scrollLeftTo(row.length)
            putCursorAt(getCursorPosition().withColumn(0))
        }
        refreshDrawSurface()
    }

    private fun scrollRightToRowEnd(row: StringBuilder) {
        scrollRightTo(Math.max(0, row.length - getBoundableSize().columns))
        putCursorAt(getCursorPosition().withColumn(getBoundableSize().columns - 1))
        refreshDrawSurface()
    }

    private fun deleteRowIfNotLast(bufferRowIdx: Int) {
        if (textBuffer.getSize() > 1) {
            textBuffer.deleteRowAt(bufferRowIdx)
        }
    }

    private fun clearSubscriptions() {
        subscriptions.forEach {
            EventBus.unsubscribe(it)
        }
        subscriptions.clear()
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