package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.behavior.CursorHandler
import org.codetome.zircon.api.behavior.Scrollable
import org.codetome.zircon.api.builder.component.ComponentStyleSetBuilder
import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.TextBox
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.InputType
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.util.Math
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.api.util.TextUtils
import org.codetome.zircon.internal.behavior.impl.DefaultCursorHandler
import org.codetome.zircon.internal.behavior.impl.DefaultScrollable
import org.codetome.zircon.internal.event.InternalEvent
import org.codetome.zircon.api.event.EventBus
import org.codetome.zircon.api.event.Subscription
import org.codetome.zircon.internal.util.TextBuffer

class DefaultTextBox constructor(
        text: String,
        initialSize: Size,
        initialTileset: TilesetResource<out Tile>,
        position: Position,
        componentStyleSet: ComponentStyleSet,
        scrollable: Scrollable = DefaultScrollable(initialSize, initialSize),
        cursorHandler: CursorHandler = DefaultCursorHandler(initialSize))
    : TextBox, Scrollable by scrollable, CursorHandler by cursorHandler, DefaultComponent(
        size = initialSize,
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = listOf(),
        tileset = initialTileset) {

    private val textBuffer = TextBuffer(text)
    private val subscriptions = mutableListOf<Subscription<*>>()
    private var enabled = true
    private var focused = false

    init {
        setText(text)
        refreshDrawSurface()
        refreshVirtualSpaceSize()
    }

    override fun getText() = textBuffer.getText() // TODO: line sep?

    override fun setText(text: String): Boolean {
        val isChanged = if (this.textBuffer.toString() == text) {
            false
        } else {
            textBuffer.setText(text)
            true
        }

        refreshDrawSurface()
        return isChanged
    }

    override fun acceptsFocus() = enabled

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getDarkBackgroundColor())
                        .backgroundColor(colorTheme.getDarkForegroundColor())
                        .build())
                .disabledStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getDarkForegroundColor())
                        .backgroundColor(colorTheme.getDarkBackgroundColor())
                        .build())
                .focusedStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getBrightBackgroundColor())
                        .backgroundColor(colorTheme.getBrightForegroundColor())
                        .build())
                .build())
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
        getDrawSurface().applyStyle(getComponentStyles().disable())
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
        getDrawSurface().applyStyle(getComponentStyles().reset())
    }

    private fun enableFocusedComponent() {
        cancelSubscriptions()
        getDrawSurface().applyStyle(getComponentStyles().giveFocus())
        enableTyping()
    }

    private fun disableTyping() {
        cancelSubscriptions()
        EventBus.broadcast(InternalEvent.HideCursor)
    }

    private fun enableTyping() {
        EventBus.broadcast(InternalEvent.RequestCursorAt(getCursorPosition().withRelative(getPosition())))
        subscriptions.add(EventBus.subscribe<InternalEvent.KeyPressed> { (keyStroke) ->
            val cursorPos = getCursorPosition()
            val (offsetCols, offsetRows) = getVisibleOffset()
            val currColIdx = cursorPos.x + offsetCols
            val currRowIdx = cursorPos.y + offsetRows
            val prevRowIdx = offsetRows + cursorPos.y - 1
            val nextRowIdx = offsetRows + cursorPos.y + 1
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
                    if (getVisibleOffset().x > 0) {
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
                        putCursorAt(getCursorPosition().withRelativeY(1))
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
                    putCursorAt(getCursorPosition().withRelativeY(1))
                }
                scrollLeftBy(0)
                putCursorAt(getCursorPosition().withX(0))
                refreshDrawSurface()
            } else if (keyStroke.inputTypeIs(InputType.Home)) {
                scrollLeftBy(0)
                putCursorAt(getCursorPosition().withX(0))
            } else if (keyStroke.inputTypeIs(InputType.End)) {
                scrollRightToRowEnd(maybeCurrRow.get())
                putCursorAt(getCursorPosition().withX(getBoundableSize().xLength - 1))
            } else if (TextUtils.isPrintableCharacter(keyStroke.getCharacter())) {
                textBuffer.getRow(currRowIdx).map {
                    if (isCursorAtTheEndOfTheLine()) {
                        it.append(keyStroke.getCharacter())
                    } else {
                        it.insert(currColIdx, keyStroke.getCharacter().toString())
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
            EventBus.broadcast(InternalEvent.RequestCursorAt(getCursorPosition() + getPosition()))
        })
    }

    private fun scrollUpToEndOfPreviousLine(prevRow: StringBuilder) {
        // we move the cursor up
        scrollCursorOrScrollableOneUp()
        // we set the position to be able to see the end of the yLength
        scrollRightToRowEnd(prevRow)
        // we fix the cursor if the yLength does not fill the visible space
        putCursorAt(getCursorPosition()
                .withX(Math.min(prevRow.length, getCursorPosition().x)))
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
            putCursorAt(getCursorPosition().withRelativeY(-1))
        }
    }

    private fun scrollLeftToRowEnd(row: StringBuilder) {
        val visibleCharCount = row.length - getVisibleOffset().x
        if (row.length > getVisibleOffset().x) {
            if (visibleCharCount < getCursorPosition().x) {
                putCursorAt(getCursorPosition().withX(row.length - getVisibleOffset().x))
            }
        } else {
            scrollLeftBy(row.length)
            putCursorAt(getCursorPosition().withX(0))
        }
        refreshDrawSurface()
    }

    private fun scrollRightToRowEnd(row: StringBuilder) {
        scrollRightBy(Math.max(0, row.length - getBoundableSize().xLength))
        putCursorAt(getCursorPosition().withX(getBoundableSize().xLength - 1))
        refreshDrawSurface()
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

    private fun refreshDrawSurface() {
        getBoundableSize().fetchPositions().forEach { pos ->
            val fixedPos = pos + getVisibleOffset()
            getDrawSurface().setTileAt(pos, TileBuilder.newBuilder()
                    .character(textBuffer.getCharAt(fixedPos).orElse(' '))
                    .build())
        }
    }

    private fun refreshVirtualSpaceSize() {
        val (visibleCols, visibleRows) = getBoundableSize()
        val (textCols, textRows) = textBuffer.getBoundingBoxSize()
        if (textCols >= visibleCols && textRows >= visibleRows) {
            setVirtualSpaceSize(textBuffer.getBoundingBoxSize())
        }
    }
}
