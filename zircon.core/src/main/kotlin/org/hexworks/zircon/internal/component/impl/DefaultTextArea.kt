package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.CursorHandler
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.event.Subscription
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Math
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.api.util.TextUtils
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.util.TextBuffer
import org.hexworks.zircon.platform.extension.delete
import org.hexworks.zircon.platform.extension.deleteCharAt
import org.hexworks.zircon.platform.extension.insert

class DefaultTextArea constructor(
        private val renderingStrategy: ComponentRenderingStrategy<TextArea>,
        text: String,
        tileset: TilesetResource,
        size: Size,
        position: Position,
        componentStyleSet: ComponentStyleSet,
        scrollable: Scrollable = DefaultScrollable(size, size),
        cursorHandler: CursorHandler = DefaultCursorHandler(size))
    : TextArea, Scrollable by scrollable, CursorHandler by cursorHandler, DefaultComponent(
        position = position,
        size = size,
        tileset = tileset,
        componentStyles = componentStyleSet,
        renderer = renderingStrategy) {

    private val textBuffer = TextBuffer(text)
    private val subscriptions = mutableListOf<Subscription<*>>()
    private var enabled = true
    private var focused = false

    init {
        setText(text)
        refreshVirtualSpaceSize()
        render()
    }

    override fun getText() = textBuffer.getText() // TODO: line sep?

    override fun textBuffer() = textBuffer

    override fun setText(text: String): Boolean {
        val isChanged = if (this.textBuffer.toString() == text) {
            false
        } else {
            textBuffer.setText(text)
            true
        }
        render()
        return isChanged
    }

    override fun acceptsFocus() = enabled

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryBackgroundColor())
                        .backgroundColor(colorTheme.secondaryForegroundColor())
                        .build())
                .disabledStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor())
                        .backgroundColor(colorTheme.secondaryBackgroundColor())
                        .build())
                .focusedStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.primaryBackgroundColor())
                        .backgroundColor(colorTheme.primaryForegroundColor())
                        .build())
                .build().also {
                    setComponentStyleSet(it)
                    render()
                }
    }

    override fun enable() {
        if (enabled.not()) {
            enabled = true
            if (focused) {
                enableFocusedComponent()
            }
        }
    }

    override fun disable() {
        if (enabled) {
            enabled = false
            if (focused) {
                disableTyping()
            }
        }
        componentStyleSet().applyDisabledStyle()
        render()
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        focused = true
        if (enabled) {
            enableFocusedComponent()
        }
        return enabled
    }

    override fun takeFocus(input: Maybe<Input>) {
        focused = false
        disableTyping()
        componentStyleSet().reset()
        render()
    }

    override fun render() {
        renderingStrategy.render(this, tileGraphics())
    }

    private fun enableFocusedComponent() {
        cancelSubscriptions()
        componentStyleSet().applyFocusedStyle()
        render()
        enableTyping()
    }

    private fun disableTyping() {
        cancelSubscriptions()
        EventBus.broadcast(ZirconEvent.HideCursor)
    }

    private fun enableTyping() {
        EventBus.broadcast(ZirconEvent.RequestCursorAt(cursorPosition().withRelative(position())))
        subscriptions.add(EventBus.subscribe<ZirconEvent.KeyPressed> { (keyStroke) ->
            val cursorPos = cursorPosition()
            val (offsetCols, offsetRows) = visibleOffset()
            val currColIdx = cursorPos.x + offsetCols
            val currRowIdx = cursorPos.y + offsetRows
            val prevRowIdx = offsetRows + cursorPos.y - 1
            val nextRowIdx = offsetRows + cursorPos.y + 1
            val maybePrevRow = textBuffer.getRow(prevRowIdx)
            val maybeNextRow = textBuffer.getRow(nextRowIdx)
            val maybeCurrRow = textBuffer.getRow(currRowIdx)
            val nextChar = textBuffer.getCharAt(visibleOffset() + cursorPosition())
            // TODO: this should be a state machine
            // refactor this later
            if (keyStroke.inputTypeIs(InputType.ArrowRight)) {
                if (nextChar.isPresent) {
                    if (isCursorAtTheEndOfTheLine()) {
                        scrollOneRight()
                        render()
                    } else {
                        moveCursorForward()
                    }
                }
            } else if (keyStroke.inputTypeIs(InputType.ArrowLeft)) {
                if (isCursorAtTheStartOfTheLine()) {
                    if (visibleOffset().x > 0) {
                        // we can still scroll left because there are hidden parts of the left section
                        scrollOneLeft()
                        render()
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
                        putCursorAt(cursorPosition().withRelativeY(1))
                    }
                    scrollLeftToRowEnd(nextRow)
                }
            } else if (keyStroke.inputTypeIs(InputType.ArrowUp)) {
                maybePrevRow.map { prevRow ->
                    scrollCursorOrScrollableOneUp()
                    scrollLeftToRowEnd(prevRow)
                }
            } else if (keyStroke.inputTypeIs(InputType.Delete)) {
                textBuffer.getRow(currRowIdx).map { row: StringBuilder ->
                    if (currColIdx == row.length) { // end of the line
                        // this is necessary because if there is no next yLength and we
                        // delete the current yLength we won't be able to type anything (since the yLength is deleted)
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
                    render()
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
                    render()
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
                    putCursorAt(cursorPosition().withRelativeY(1))
                }
                scrollLeftBy(0)
                putCursorAt(cursorPosition().withX(0))
                render()
            } else if (keyStroke.inputTypeIs(InputType.Home)) {
                scrollLeftBy(0)
                putCursorAt(cursorPosition().withX(0))
            } else if (keyStroke.inputTypeIs(InputType.End)) {
                scrollRightToRowEnd(maybeCurrRow.get())
                putCursorAt(cursorPosition().withX(size().xLength - 1))
            } else if (TextUtils.isPrintableCharacter(keyStroke.getCharacter())) {
                textBuffer.getRow(currRowIdx).map {
                    if (isCursorAtTheEndOfTheLine()) {
                        it.append(keyStroke.getCharacter())
                    } else {
                        it.insert(currColIdx, keyStroke.getCharacter().toString())
                    }
                    render()
                    refreshVirtualSpaceSize()
                    if (isCursorAtTheEndOfTheLine()) {
                        scrollOneRight()
                    } else {
                        moveCursorForward()
                    }
                    render()
                }
            }
            EventBus.broadcast(ZirconEvent.RequestCursorAt(cursorPosition() + position()))
        })
    }

    private fun scrollUpToEndOfPreviousLine(prevRow: StringBuilder) {
        // we move the cursor up
        scrollCursorOrScrollableOneUp()
        // we set the position to be able to see the end of the yLength
        scrollRightToRowEnd(prevRow)
        // we fix the cursor if the yLength does not fill the visible space
        putCursorAt(cursorPosition()
                .withX(Math.min(prevRow.length, cursorPosition().x)))
        refreshVirtualSpaceSize()
        render()
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
            putCursorAt(cursorPosition().withRelativeY(-1))
        }
    }

    private fun scrollLeftToRowEnd(row: StringBuilder) {
        val visibleCharCount = row.length - visibleOffset().x
        if (row.length > visibleOffset().x) {
            if (visibleCharCount < cursorPosition().x) {
                putCursorAt(cursorPosition().withX(row.length - visibleOffset().x))
            }
        } else {
            scrollLeftBy(row.length)
            putCursorAt(cursorPosition().withX(0))
        }
        render()
    }

    private fun scrollRightToRowEnd(row: StringBuilder) {
        scrollRightBy(Math.max(0, row.length - size().xLength))
        putCursorAt(cursorPosition().withX(size().xLength - 1))
        render()
    }

    private fun deleteRowIfNotLast(bufferRowIdx: Int) {
        if (textBuffer.getSize() > 1) {
            textBuffer.deleteRowAt(bufferRowIdx)
        }
    }

    private fun cancelSubscriptions() {
        subscriptions.forEach {
            EventBus.unsubscribe(it)
        }
        subscriptions.clear()
    }

    private fun refreshVirtualSpaceSize() {
        val (visibleCols, visibleRows) = size()
        val (textCols, textRows) = textBuffer.getBoundingBoxSize()
        if (textCols >= visibleCols && textRows >= visibleRows) {
            setActualSize(textBuffer.getBoundingBoxSize())
        }
    }
}
